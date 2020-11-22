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
import models.Idea;
import utils.DBUtil;

@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("member_id")));

        // ログイン中のメンバーIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        // フォロー解除するメンバーのIDを取得
        Integer ei = 0;
        ei = em.createNamedQuery("getDestroyFollow", Integer.class)
                .setParameter("followed_id", idea.getMember())
                .setParameter("login_member", login_member)
                .getSingleResult();

        Follow f = em.find(Follow.class, ei);

        // フォロー解除するメンバーの氏名を取得
        Member unfollow = idea.getMember();
        String unfollow_name_str = unfollow.getName();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージ
        request.getSession().setAttribute("toast", unfollow_name_str + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}