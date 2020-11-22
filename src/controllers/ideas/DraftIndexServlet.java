package controllers.ideas;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/drafts")
public class DraftIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DraftIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Idea> getMyIdeas = em.createNamedQuery("getMyIdeas", Idea.class)
                .setParameter("loginMember", loginMember)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        long getDirectorAdviceCnt = (long) em.createNamedQuery("getDirectorAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getMyAllDrafts", getMyIdeas);
        request.setAttribute("getMyDraftsCount", getMyDraftsCnt);
        request.setAttribute("getReportsCountButDrafts", getIdeasCntButDrafts);
        request.setAttribute("getManagerRemandReportsCount", getManagerAdviceCnt);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorAdviceCnt);

        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/draft.jsp");
        rd.forward(request, response);
    }
}