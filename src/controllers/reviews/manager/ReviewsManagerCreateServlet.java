package controllers.reviews.manager;

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

@WebServlet("/reviews/manager/create")
public class ReviewsManagerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewsManagerCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // レビューするアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // Ideaテーブルのレビュー状態を取得しInteger型に変換
        Integer reviewStatus = Integer.parseInt(request.getParameter("reviewStatus"));

        // Ideaテーブルのレビュー状態を更新
        idea.setReviewStatus(reviewStatus);

        Review review = new Review();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Reviewテーブルに値を新規登録
        review.setIdea(idea);
        review.setMember(loginMember);
        review.setReviewStatus(reviewStatus);
        review.setAdvice(request.getParameter("advice"));
        review.setCreatedAt(timestamp);
        review.setUpdatedAt(timestamp);

        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        if (reviewStatus == 1) { // アイデアにアドバイスを付けて送り返す場合
            request.getSession().setAttribute("toast", "You sent \"" + idea.getTitle() + "\" back to " + idea.getMember().getName() + ".");
        } else { // アイデアを承認する場合
            request.getSession().setAttribute("toast", "You approved " + idea.getMember().getName() + "'s idea \"" + idea.getTitle() + "\".");
        }

        response.sendRedirect(request.getContextPath() + "/reviews/manager");
    }
}