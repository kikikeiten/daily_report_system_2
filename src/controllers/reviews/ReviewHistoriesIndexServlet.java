package controllers.reviews;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Idea;
import utils.DBUtil;

@WebServlet("/advice/history")
public class ReviewHistoriesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewHistoriesIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // アドバイス履歴を確認するアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

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

        // アドバイス履歴を確認するアイデアのIDをInteger型で取得
        Integer ideaId = idea.getId();
        // アドバイス履歴を確認するアイデアのタイトルをString型で取得
        String ideaTitle = idea.getTitle();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getReviews", getReviews);
        request.setAttribute("getReviewsCnt", getReviewsCnt);
        request.setAttribute("ideaId", ideaId);
        request.setAttribute("ideaTitle", ideaTitle);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/advice/history/index.jsp");
        rd.forward(request, response);
    }

}