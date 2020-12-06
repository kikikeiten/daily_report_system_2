package controllers.follows.following;

import java.io.IOException;
import java.sql.Timestamp;

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

@WebServlet("/following/create/idea")
public class FollowingCreateViaIdeaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingCreateViaIdeaServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // フォローされたメンバーが作成したアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("followedId")));

        // Followテーブルに値をセット
        follow.setFollowingId((Member) request.getSession().getAttribute("loginMember"));
        follow.setFollowedId(idea.getMember());
        follow.setCreatedAt(timestamp);
        follow.setUpdatedAt(timestamp);

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "You followed " + idea.getMember().getName() + ".");

        response.sendRedirect(request.getContextPath() + "/ideas/show?id=" + idea.getId());
    }
}