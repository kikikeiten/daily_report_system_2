package controllers.follows.management.unfollowing;

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

@WebServlet("/management/unfollowing/create")
public class ManagementsUnfollowingCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementsUnfollowingCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // フォローする側のメンバーIDを取得
        Member following = em.find(Member.class, Integer.parseInt(request.getParameter("followingId")));
        // フォローされる側のメンバーIDを取得
        Member followed = em.find(Member.class, Integer.parseInt(request.getParameter("followedId")));

        // Followテーブルにセット
        follow.setFollowingId(following);
        follow.setFollowedId(followed);
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", following.getName() + "さんが" + followed.getName() + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/management/unfollowing?id=" + following.getId());
    }

}