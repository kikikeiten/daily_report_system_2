package controllers.follows.followers;

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

@WebServlet("/followers/create")
public class FollowersCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowersCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // フォローされたメンバーのIDを取得
        Follow followed = em.find(Follow.class, Integer.parseInt(request.getParameter("followedId")));

        // Followテーブルに値をセット
        follow.setFollowingId((Member) request.getSession().getAttribute("loginMember"));
        follow.setFollowedId(followed.getMember());
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        // フォローされたメンバーの氏名を取得
        Member followedMember = followed.getMember();
        String followName = followedMember.getName();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", followName + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}