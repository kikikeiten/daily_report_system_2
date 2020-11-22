package controllers.reviews;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/reviews/director/create")
public class DirectorReviewsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DirectorReviewsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // レビューされるメンバーのIDを取得
        Idea ideaId = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // レビュー状況を取得
        Integer reviewFlag = Integer.parseInt(request.getParameter("reviewFlag"));

        Review review = new Review();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Reviewテーブルに新規のレコードをセット
        review.setIdea(ideaId);
        review.setMember(loginMember);
        review.setReview_flag(reviewFlag);
        review.setAdvice(request.getParameter("advice"));
        review.setCreated_at(timestamp);
        review.setUpdated_at(timestamp);

        // Ideaテーブルの値を更新
        ideaId.setReview_flag(reviewFlag);

        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        // レビューされるメンバーIDを取得
        Member ideaMember = ideaId.getMember();
        // メンバーの役割を取得
        Integer roleFlag = ideaMember.getRole_flag();
        // レビューされるメンバー名を取得
        String ideaMemberName = ideaMember.getName();

        // 値に応じてトーストメッセージをセッションにセット
        if (reviewFlag == 3) { // ideaにadviceを付けて差し戻す
            switch (roleFlag) {
                case 0: // associate宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + ideaId.getTitle() + "」を" + ideaMemberName + "社員に差し戻しました。");
                    break;
                case 1: // administrator宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + ideaId.getTitle() + "」を" + ideaMemberName + "管理者に差し戻しました。");
                    break;
                case 2: // manager宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + ideaId.getTitle() + "」を" + ideaMemberName + "課長に差し戻しました。");
                    break;
                case 3: // director宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + ideaId.getTitle() + "」を" + ideaMemberName + "部長に差し戻しました。");
                    break;
            }
        } else { // ideaを承認する
            switch (roleFlag) {
                case 0: // associate宛て
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "社員の日報「" + ideaId.getTitle() + "」を承認しました。");
                    break;
                case 1: // administrator宛て
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "管理者の日報「" + ideaId.getTitle() + "」を承認しました。");
                    break;
                case 2: // manager宛て
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "課長の日報「" + ideaId.getTitle() + "」を承認しました。");
                    break;
                case 3: // director宛て
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "部長の日報「" + ideaId.getTitle() + "」を承認しました。");
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/reviews/director");
    }
}