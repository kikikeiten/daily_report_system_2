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

        // フォロー解除される側のメンバーIDを取得
        Follow follow = em.find(Follow.class, Integer.parseInt(request.getParameter("followed_id")));

        // フォロー解除される側の氏名を取得
        Member member_id = follow.getMember();
        String member_name_str = member_id.getName();

        // フォロー解除する側の氏名を取得
        Member unfollow_id = follow.getFollow();
        String unfollow_name_str = unfollow_id.getName();

        // フォロー解除する側のメンバーIDを取得
        Member following_id = follow.getMember();
        Integer member_id_int = following_id.getId();

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", member_name_str + "さんが" + unfollow_name_str + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/management/unfollow?id=" + member_id_int);
    }

}