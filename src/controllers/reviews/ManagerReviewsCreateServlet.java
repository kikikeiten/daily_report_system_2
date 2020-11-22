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

@WebServlet("/manager/approval/create")
public class ManagerReviewsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagerReviewsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // レビューするアイデアIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // Ideaテーブルのレビュー状態を更新
        idea.setReview_flag(Integer.parseInt(request.getParameter("reviewFlag")));

        // Ideaテーブルのレビュー状態を取得しInteger型に変換
        Integer reviewFlag = Integer.parseInt(request.getParameter("reviewFlag"));

        Review review = new Review();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Reviewテーブルに値を新規登録
        review.setIdea(idea);
        review.setMember(loginMember);
        review.setReview_flag(reviewFlag);
        review.setAdvice(request.getParameter("advice"));
        review.setCreated_at(currentTime);
        review.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        // アイデアを書いたメンバーのIDを取得
        Member ideaMember = idea.getMember();
        // アイデアを書いたメンバーの役割を取得
        Integer ideaMemberRoleFlag = ideaMember.getRole_flag();
        // アイデアを書いたメンバー名を取得
        String ideaMemberName = ideaMember.getName();

        if (reviewFlag == 1) { // アイデアにアドバイスを付けて送り返す場合
            switch (ideaMemberRoleFlag) {
                case 0: // associate宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "社員に差し戻しました。");
                    break;
                case 1: // administrator宛て
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "管理者に差し戻しました。");
                    break;
            }
        } else { // アイデアを承認する場合
            switch (ideaMemberRoleFlag) {
                case 0: // associateから
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "社員の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 1: // administratorから
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "管理者の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 2: // managerから
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "課長の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/reviews/manager");
    }

}