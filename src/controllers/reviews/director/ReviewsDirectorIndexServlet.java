package controllers.reviews.director;

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
import utils.DBUtil;

@WebServlet("/reviews/director")
public class ReviewsDirectorIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewsDirectorIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ディレクターのレビュー待ちアイデアを取得
        List<Idea> getDirectorReviews = em.createNamedQuery("getDirectorReviews", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getDirectorReviews", getDirectorReviews);

        // トーストメッセージをセッションにセット
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reviews/director/index.jsp");
        rd.forward(request, response);
    }

}