package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follower/destroy")
public class FollowerDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowerDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        // フォロー解除ボタンを押した際にフォローしているフォロワーのIDを取得
        Follow following = em.find(Follow.class, Integer.parseInt(request.getParameter("following_id")));

        // フォロー解除するフォロワーのIDを取得
        Integer ei = 0;
        ei = em.createNamedQuery("getDestroyFollower", Integer.class)
                .setParameter("login_member", login_member)
                .setParameter("following_id", following.getMember())
                .getSingleResult();

        Follow follow = em.find(Follow.class, ei);

        // フォロー解除したフォロワーの氏名を取得
        Member unfollow = following.getMember();
        String unfollow_name_str = unfollow.getName();

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージ
        request.getSession().setAttribute("toast", unfollow_name_str + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}