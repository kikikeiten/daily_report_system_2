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

        // フォロー解除するメンバーの作成したアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("memberId")));

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // フォロー解除するメンバーのIDを取得
        Integer ei = 0;
        ei = em.createNamedQuery("getDestroyFollow", Integer.class)
                .setParameter("followedId", idea.getMember())
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        Follow follow = em.find(Follow.class, ei);

        // フォロー解除するメンバーの氏名を取得
        Member unfollow = idea.getMember();
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