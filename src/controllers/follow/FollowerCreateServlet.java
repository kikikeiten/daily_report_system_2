package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follower/create")
public class FollowerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowerCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow follow = new Follow();

        // 詳細な時刻を取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // フォローボタンを押した際にフォローされるメンバーのIDを取得
        Follow followed = em.find(Follow.class, Integer.parseInt(request.getParameter("followed_id")));

        // Followテーブルに値をセット
        follow.setFollowing_id((Member) request.getSession().getAttribute("login_member"));
        follow.setFollowed_id(followed.getMember());
        follow.setCreated_at(currentTime);
        follow.setUpdated_at(currentTime);

        // フォローされたメンバーの氏名を取得
        Member followedMember = followed.getMember();
        String follow_name_str = followedMember.getName();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", follow_name_str + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}