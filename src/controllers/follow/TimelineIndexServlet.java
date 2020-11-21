package controllers.follow;

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

@WebServlet("/timeline")
public class TimelineIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TimelineIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("login_member", login_member)
                .getResultList();

        List<Integer> follow_idea_id = new ArrayList<Integer>();

        for (Member idea_id : checkMyFollow) {
            Integer idea_id_int = idea_id.getId();
            follow_idea_id.add(idea_id_int);
            request.setAttribute("follow_idea_id", follow_idea_id);
        }

        // ログイン中メンバーがフォローしているメンバーのideaを取得
        List<Idea> getMyFollowingIdeas = em.createNamedQuery("getMyFollowingIdeas", Idea.class)
                .setParameter("login_member", login_member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ログイン中メンバーがフォローしているメンバーのidea総数を取得
        long getMyFollowingIdeasCnt = (long) em.createNamedQuery("getMyFollowingIdeasCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getMyFollowingIdeas", getMyFollowingIdeas);
        request.setAttribute("getMyFollowingIdeasCnt", getMyFollowingIdeasCnt);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/timeline.jsp");
        rd.forward(request, response);

    }
}