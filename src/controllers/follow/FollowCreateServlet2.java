package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follow/create/2")
public class FollowCreateServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowCreateServlet2() {
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

        // フォローされたメンバーの氏名を取得
        String followedName = member.getName();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", followedName + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }

}