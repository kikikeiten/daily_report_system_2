package controllers.follows.following;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import models.Member;
import utils.DBUtil;

@WebServlet("/following/create/member")
public class FollowingCreateViaMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingCreateViaMemberServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // フォローされたメンバーのIDを取得
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("followedId")));

        // Followテーブルに値をセット
        follow.setFollowingId((Member) request.getSession().getAttribute("loginMember"));
        follow.setFollowedId(member);
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", member.getName() + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }

}