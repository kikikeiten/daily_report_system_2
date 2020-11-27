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
            switch (idea.getMember().getRole()) {
                case 0: // アソシエイト宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + idea.getMember().getName() + "社員に差し戻しました。");
                    break;
                case 1: // 管理者宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + idea.getMember().getName() + "管理者に差し戻しました。");
                    break;
                case 2: // マネージャー宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + idea.getMember().getName() + "課長に差し戻しました。");
                    break;
                case 3: // ディレクター宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + idea.getMember().getName() + "部長に差し戻しました。");
                    break;
            }
        } else { // アイデアを承認する
            switch (idea.getMember().getRole()) {
                case 0: // アソシエイトから
                    request.getSession().setAttribute("toast",
                            idea.getMember().getName() + "社員の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 1: // 管理者から
                    request.getSession().setAttribute("toast",
                            idea.getMember().getName() + "管理者の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 2: // マネージャーから
                    request.getSession().setAttribute("toast",
                            idea.getMember().getName() + "課長の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 3: // ディレクターから
                    request.getSession().setAttribute("toast",
                            idea.getMember().getName() + "部長の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/reviews/director");
    }
}