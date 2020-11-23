package controllers.follow.management;

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

@WebServlet("/management/follow/destroy")
public class ManagementFollowDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementFollowDestroy() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // フォロー解除されるメンバーのIDを取得
        Follow unfollowed = em.find(Follow.class, Integer.parseInt(request.getParameter("followedId")));

        // フォロー解除されるメンバーの氏名をString型で取得
        Member unfollowedMember = unfollowed.getMember();
        String unfollowedMemberName = unfollowedMember.getName();

        // フォロー解除するメンバーの氏名をString型で取得
        Member unfollowingMember = unfollowed.getFollow();
        String unfollowingMemberName = unfollowingMember.getName();

        // フォロー解除するメンバーのIDをInt型で取得
        Integer unfollowingMemberId = unfollowingMember.getId();

        em.getTransaction().begin();
        em.remove(unfollowed);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", unfollowedMemberName + "さんが" + unfollowingMemberName + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/management/unfollow?id=" + unfollowingMemberId);
    }

}