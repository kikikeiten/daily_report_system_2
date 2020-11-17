package controllers.ideas;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/ideas")
public class IdeasIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member login_member = (Member) request.getSession().getAttribute("login_member");

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("login_member", login_member)
                .getResultList();

        List<Integer> follow_idea_id = new ArrayList<Integer>();

        for (Member idea_id : checkMyFollow) {
            Integer int_idea_id = idea_id.getId();
            follow_idea_id.add(int_idea_id);
            request.setAttribute("follow_idea_id", follow_idea_id);
        }

        //フォロー判定ここまで

        List<Idea> getIdeasButDrafts = em.createNamedQuery("getIdeasButDrafts", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
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

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        em.close();

        request.setAttribute("getIdeasButDrafts", getIdeasButDrafts);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getDirectorAdviceCnt", getDirectorAdviceCnt);
        request.setAttribute("page", page);

        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/index.jsp");
        rd.forward(request, response);
    }

}