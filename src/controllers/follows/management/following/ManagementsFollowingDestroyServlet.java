package controllers.follows.management.following;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import utils.DBUtil;

@WebServlet("/management/following/destroy")
public class ManagementsFollowingDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementsFollowingDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // フォロー解除されるメンバーのIDを取得
        Follow unfollowed = em.find(Follow.class, Integer.parseInt(request.getParameter("followedId")));

        // フォロー解除されるメンバーの氏名をString型で取得
        String unfollowedMember = unfollowed.getFollowingId().getName();

        // フォロー解除するメンバーの氏名をString型で取得
        String unfollowingMember = unfollowed.getFollowedId().getName();

        // フォロー解除するメンバーのIDをInteger型で取得
        Integer unfollowingMemberId = unfollowed.getFollowingId().getId();

        em.getTransaction().begin();
        em.remove(unfollowed);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", unfollowedMember + "さんが" + unfollowingMember + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/management/following?id=" + unfollowingMemberId);
    }

}