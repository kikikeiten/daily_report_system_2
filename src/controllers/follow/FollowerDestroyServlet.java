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
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // フォロー解除されるメンバーのIDを取得
        Follow following = em.find(Follow.class, Integer.parseInt(request.getParameter("followingId")));

        // フォローリストの中からフォロー解除されるメンバーのIDを取得
        Integer memberId = 0;
        memberId = em.createNamedQuery("getDestroyFollower", Integer.class)
                .setParameter("loginMember", loginMember)
                .setParameter("followingId", following.getMember())
                .getSingleResult();

        Follow follow = em.find(Follow.class, memberId);

        // フォロー解除されるメンバーの氏名を取得
        Member unfollow = following.getMember();
        String unfollowName = unfollow.getName();

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", unfollowName + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}