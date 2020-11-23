package controllers.follow.management;

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

@WebServlet("/management/follow/create")
public class ManagementFollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementFollowCreateServlet() {
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
        follow.setFollowingId(memberOperated);
        follow.setFollowedId(followed);
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        // フォローする側の氏名を取得
        String followingName = following.getName();
        // フォローする側のメンバーIDを取得
        Integer followingId = following.getId();
        // フォローされる側の氏名を取得
        String followedName = followed.getName();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", followingName + "さんが" + followedName + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/management/follow?id=" + followingId);
    }

}