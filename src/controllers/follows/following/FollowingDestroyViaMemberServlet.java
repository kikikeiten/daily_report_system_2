package controllers.follows.following;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import models.Member;
import utils.DBUtil;

@WebServlet("/following/destroy/member")
public class FollowingDestroyViaMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingDestroyViaMemberServlet() {
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

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", member.getName() + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }
}