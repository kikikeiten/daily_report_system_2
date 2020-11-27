package controllers.advice.history;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/advice/history")
public class HistoryIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HistoryIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // アドバイス履歴を確認するアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // アイデアの全アドバイス履歴を取得
        List<Review> getReviews = em.createNamedQuery("getReviews", Review.class)
                .setParameter("idea", idea)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記のカウントを取得
        long getReviewsCnt = (long) em.createNamedQuery("getReviewsCnt", Long.class)
                .setParameter("idea", idea)
                .getSingleResult();

        em.close();

        request.setAttribute("idea", idea);
        request.setAttribute("page", page);
        request.setAttribute("getReviews", getReviews);
        request.setAttribute("getReviewsCnt", getReviewsCnt);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/advice/history/index.jsp");
        rd.forward(request, response);
    }

}