package controllers.follows.following;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import models.Idea;
import models.Member;
import utils.DBUtil;

@WebServlet("/following/destroy/idea")
public class FollowingDestroyViaIdeaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingDestroyViaIdeaServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // フォロー解除するメンバーの作成したアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("memberId")));

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // フォローリストの中からフォロー解除するメンバーのIDを取得
        Integer memberId = 0;
        memberId = em.createNamedQuery("getDestroyFollow", Integer.class)
                .setParameter("followedId", idea.getMember())
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        Follow follow = em.find(Follow.class, memberId);

        em.getTransaction().begin();
        em.remove(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "You unfollowed " + idea.getMember().getName() + ".");

        response.sendRedirect(request.getContextPath() + "/ideas/show?id=" + idea.getId());
    }
}