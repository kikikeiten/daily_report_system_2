package controllers.follows.timeline;

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
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("loginMember", loginMember)
                .getResultList();

        List<Integer> followIdea = new ArrayList<Integer>();

        for (Member ideaId : checkMyFollow) {
            Integer ideaIdInt = ideaId.getId();
            followIdea.add(ideaIdInt);
            request.setAttribute("followIdea", followIdea);
        }

        // ログイン中メンバーがフォローしているメンバーのアイデアを取得
        List<Idea> getMyFollowingIdeas = em.createNamedQuery("getMyFollowingIdeas", Idea.class)
                .setParameter("loginMember", loginMember)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ログイン中メンバーがフォローしているメンバーのアイデア総数を取得
        long getMyFollowingIdeasCnt = (long) em.createNamedQuery("getMyFollowingIdeasCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getMyFollowingIdeas", getMyFollowingIdeas);
        request.setAttribute("getMyFollowingIdeasCnt", getMyFollowingIdeasCnt);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/timeline/index.jsp");
        rd.forward(request, response);

    }
}