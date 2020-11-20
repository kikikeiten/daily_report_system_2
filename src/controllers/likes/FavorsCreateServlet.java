package controllers.likes;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Favor;
import models.Idea;
import utils.DBUtil;

@WebServlet("/favors/create")
public class FavorsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FavorsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("idea_id")));

        Favor f = new Favor();

        // 詳細な現在時刻を取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Favorテーブルにセット
        f.setMember((Member) request.getSession().getAttribute("login_member"));
        f.setIdea(i);
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        // Ideaテーブルにセット
        i.setFavors(Integer.parseInt(request.getParameter("favors")) + i.getFavors());

        // favorされたメンバーの氏名を取得
        Member member = i.getMember();
        String member_name_str = member.getName();

        // favorされたideaのタイトルを取得
        String idea_title_str = i.getTitle();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージ
        request.getSession().setAttribute("toast", member_name_str + "さんの日報「" + idea_title_str + "」にいいねしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");

    }
}