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
import models.Idea;
import utils.DBUtil;

@WebServlet("/follow/create")
public class FollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();

        // 詳細な現在時刻を追加
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // フォローボタンを押されたideaを書いたメンバーidを取得
        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("following_id")));

        // Followテーブルに値をセット
        f.setFollowing_id((Member) request.getSession().getAttribute("login_member"));
        f.setFollowed_id(i.getMember());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        // フォローされた人の氏名を取得
        Member unfollow = i.getMember();
        String unfollow_name_str = unfollow.getName();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージ
        request.getSession().setAttribute("toast", unfollow_name_str + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}