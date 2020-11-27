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

            request.setAttribute("advice", getReviews.getAdvice());
            request.setAttribute("name", getReviews.getMember().getName());

            switch (getReviews.getMember().getRole()) {
                case 2: // マネージャー
                    request.setAttribute("role", "Manager");
                    break;
                case 3: // ディレクター
                    request.setAttribute("role", "Director");
                    break;
            }

            // レビューの経過を取得しInteger型に変換
            Integer reviewFlag = getReviews.getReviewStatus();

            if (reviewFlag == 1 || reviewFlag == 3) {
                request.setAttribute("reviewFlag", "差し戻し");

            } else if (reviewFlag == 4 || reviewFlag == 6) {
                request.setAttribute("reviewFlag", "承認");
            }
            request.setAttribute("reviewFlag", reviewFlag);
        } catch (Exception e) {
        }

        em.close();

        request.setAttribute("idea", idea);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/show.jsp");
        rd.forward(request, response);
    }

}