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
import models.Idea;
import utils.DBUtil;

@WebServlet("/follow/create")
public class FollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // フォローされたメンバーが作成したアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("followedId")));

        // Followテーブルに値をセット
        follow.setFollowingId((Member) request.getSession().getAttribute("loginMember"));
        follow.setFollowedId(idea.getMember());
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        // フォローされたメンバーの氏名を取得
        Member followed = idea.getMember();
        String followedName = followed.getName();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", followedName + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}