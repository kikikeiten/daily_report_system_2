package controllers.favors;

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

        // 賛成するアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        Favor favor = new Favor();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Favorテーブルに値をセット
        favor.setMember((Member) request.getSession().getAttribute("loginMember"));
        favor.setIdea(idea);
        favor.setCreatedAt(timestamp);
        favor.setUpdatedAt(timestamp);

        // Ideaテーブルに値をセット
        idea.setFavors(Integer.parseInt(request.getParameter("favors")) + idea.getFavors());

        // 賛成されたアイデアの作者名をString型に変換
        Member ideaMember = idea.getMember();
        String ideaMemberName = ideaMember.getName();

        // 賛成されたアイデアのタイトルをString型に変換
        String ideaTitle = idea.getTitle();

        em.getTransaction().begin();
        em.persist(favor);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", ideaMemberName + "さんの日報「" + ideaTitle + "」にいいねしました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}