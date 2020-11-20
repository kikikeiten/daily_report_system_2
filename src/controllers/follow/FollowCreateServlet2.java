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

@WebServlet("/follow/create/2")
public class FollowCreateServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowCreateServlet2() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();

        // 詳細な現在時刻を取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // フォローされたメンバーIDを取得
        Member m = em.find(Member.class, Integer.parseInt(request.getParameter("following_id")));

        // Followテーブルに値をセット
        f.setFollowing_id((Member) request.getSession().getAttribute("login_member"));
        f.setFollowed_id(m);
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        // フォローされたメンバーの氏名を取得
        String unfollow_name_str = m.getName();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージ
        request.getSession().setAttribute("toast", unfollow_name_str + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }

}