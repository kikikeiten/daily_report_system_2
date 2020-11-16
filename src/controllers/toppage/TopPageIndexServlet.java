package controllers.toppage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TopPageIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member login_member = (Member) request.getSession().getAttribute("login_member");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Idea> getMyIdeas = em.createNamedQuery("getMyIdeas", Idea.class)
                .setParameter("login_member", login_member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long getMyIdeasCnt = (long) em.createNamedQuery("getMyIdeasCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long getDirectorAdviceCnt = (long) em.createNamedQuery("getDirectorAdviceCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        Date today = new Date(System.currentTimeMillis());

        long get4getMyDraftsCnt = (long) em.createNamedQuery("get4getMyDraftsCnt", Long.class)
                .setParameter("today", today)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long get4getManagerReviewsCnt = (long) em
                .createNamedQuery("get4getManagerReviewsCnt", Long.class)
                .setParameter("today", today)
                .setParameter("role_flag", login_member.getRole_flag())
                .getSingleResult();

        long get4getDirectorReviewsCnt = (long) em
                .createNamedQuery("get4getDirectorReviewsCnt", Long.class)
                .setParameter("today", today)
                .setParameter("role_flag", login_member.getRole_flag())
                .getSingleResult();

        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        try {
            Join getMyLatestJoin = (Join) em
                    .createNamedQuery("getMyLatestJoin", Join.class)
                    .setParameter("login_member", login_member)
                    .setMaxResults(1)
                    .getSingleResult();

            Integer join_flag = getMyLatestJoin.getJoin_flag();
            request.setAttribute("join_flag", join_flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Join> get4getJoins = em.createNamedQuery("get4getJoins", Join.class)
                    .setParameter("today", today)
                    .getResultList();

            for (Join set_forget : get4getJoins) {
                set_forget.setJoin_flag(2);

                em.getTransaction().begin();
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();

        request.setAttribute("reports", getMyIdeas);
        request.setAttribute("reports_count", getMyIdeasCnt);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerRemandReportsCount", getManagerAdviceCnt);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorAdviceCnt);
        request.setAttribute("get4getMyDraftsCnt", get4getMyDraftsCnt);
        request.setAttribute("getYesterdayManagerApprovalsCount", get4getManagerReviewsCnt);
        request.setAttribute("getYesterdayDirectorApprovalsCount", get4getDirectorReviewsCnt);
        request.setAttribute("getReportsCountButDrafts", getIdeasCntButDrafts);

        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }
}