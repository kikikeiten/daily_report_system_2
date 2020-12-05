package controllers.ideas;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;
import models.Review;
import utils.DBUtil;

@WebServlet("/ideas/show")
public class IdeasShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 詳細を見るアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        try {
            // アイデアの全レビューを取得（存在しない場合もあるのでtry-catch）
            Review getReviews = em.createNamedQuery("getReviews", Review.class)
                    .setParameter("idea", idea)
                    .setMaxResults(1) // 一件だけ表示
                    .getSingleResult();

            // 最新のレビューのIDを取得
            Review review = em.find(Review.class, getReviews.getId());

            request.setAttribute("review", review);

        } catch (Exception e) {
        }

        em.close();

        request.setAttribute("idea", idea);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/show.jsp");
        rd.forward(request, response);
    }
}