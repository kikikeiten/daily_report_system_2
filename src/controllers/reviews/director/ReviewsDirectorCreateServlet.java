package controllers.reviews.director;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;
import models.Member;
import models.Review;
import utils.DBUtil;

@WebServlet("/reviews/director/create")
public class ReviewsDirectorCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewsDirectorCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // レビューされるメンバーのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // レビュー状況を取得
        Integer reviewStatus = Integer.parseInt(request.getParameter("reviewStatus"));

        Review review = new Review();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Reviewテーブルに新規のレコードをセット
        review.setIdea(idea);
        review.setMember(loginMember);
        review.setReviewStatus(reviewStatus);
        review.setAdvice(request.getParameter("advice"));
        review.setCreatedAt(timestamp);
        review.setUpdatedAt(timestamp);

        // Ideaテーブルの値を更新
        idea.setReviewStatus(reviewStatus);

        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        // 値に応じてトーストメッセージをセッションにセット
        if (reviewStatus == 3) { // アイデアにアドバイスを付けて差し戻す
            request.getSession().setAttribute("toast", "You sent \"" + idea.getTitle() + "\" back to " + idea.getMember().getName() + ".");
        } else { // アイデアを承認する
            request.getSession().setAttribute("toast", "You approved " + idea.getMember().getName() + "'s idea \"" + idea.getTitle() + "\".");
        }

        response.sendRedirect(request.getContextPath() + "/reviews/director");
    }
}