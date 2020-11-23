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

@WebServlet("/follow/destroy/2")
public class FollowDestroyServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowDestroyServlet2() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // フォロー解除するメンバーのIDを取得
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("memberId")));

        // フォローリストの中からフォロー解除するメンバーのIDを取得
        Integer memberId = 0;
        memberId = em.createNamedQuery("getDestroyFollow", Integer.class)
                .setParameter("followedId", member)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        Follow follow = em.find(Follow.class, memberId);

        // フォロー解除するメンバーの氏名を取得
        String unfollowName = member.getName();

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", unfollowName + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }
}