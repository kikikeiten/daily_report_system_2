package controllers.ideas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Member;
import models.Idea;
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

        // ideas/show.jspからideaのidを取得
        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("idea_id")));

        // ログイン中メンバーのidを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("login_member", login_member)
                .getResultList();

        List<Integer> follow_idea_id = new ArrayList<Integer>();

        for (Member idea_id : checkMyFollow) {
            Integer idea_id_int = idea_id.getId();
            follow_idea_id.add(idea_id_int);
            request.setAttribute("follow_idea_id", follow_idea_id);
        }

        try {
            // ideaの全レビューを取得（存在しない場合もあるのでtry-catch）
            Review getReviews = em.createNamedQuery("getReviews", Review.class)
                    .setParameter("idea", i)
                    .setMaxResults(1) // 一件だけ表示
                    .getSingleResult();

            // アドバイスを取得しString型に変換
            String advice_str = getReviews.getAdvice();

            // レビューした人のメンバーidを取得
            Member reviewer = getReviews.getMember();
            // レビューした人の氏名を取得しString型に変換
            String name_str = reviewer.getName();
            // レビューした人の役割を取得しInteger型に変換
            Integer role_flag_int = reviewer.getRole_flag();

            request.setAttribute("advice_str", advice_str);
            request.setAttribute("name_str", name_str);

            switch (role_flag_int) {
                case 2:
                    request.setAttribute("role", "Manager");
                    break;
                case 3:
                    request.setAttribute("role", "Director");
                    break;
            }

        } catch (Exception e) {

        } finally {

            // レビューの経過を取得しInteger型に変換
            Integer review_flag_int = getReviews.getReview_flag();

            if (review_flag_int == 1 || review_flag_int == 3) {
                request.setAttribute("review_flag", "差し戻し");

            } else if (review_flag_int == 4 || review_flag_int == 6) {
                request.setAttribute("review_flag", "承認");
            }
        }

        em.close();

        request.setAttribute("idea", i);
        request.setAttribute("review_flag_int", review_flag_int);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/show.jsp");
        rd.forward(request, response);
    }

}