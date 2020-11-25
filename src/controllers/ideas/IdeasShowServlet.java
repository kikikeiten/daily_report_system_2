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

import models.Idea;
import models.Member;
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
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("loginMember", loginMember)
                .getResultList();

        List<Integer> followIdea = new ArrayList<Integer>();

        for (Member ideaId : checkMyFollow) {
            Integer ideaIdInt = ideaId.getId();
            followIdea.add(ideaIdInt);
            request.setAttribute("followIdea", followIdea);
        }

        try {
            // アイデアの全レビューを取得（存在しない場合もあるのでtry-catch）
            Review getReviews = em.createNamedQuery("getReviews", Review.class)
                    .setParameter("idea", idea)
                    .setMaxResults(1) // 一件だけ表示
                    .getSingleResult();

            // アドバイスを取得しString型に変換
            String advice = getReviews.getAdvice();
            // レビューした人のメンバーIDを取得
            Member reviewer = getReviews.getMember();
            // レビューした人の氏名を取得しString型に変換
            String name = reviewer.getName();
            // レビューした人の役割を取得しInteger型に変換
            Integer role = reviewer.getRole();

            request.setAttribute("advice", advice);
            request.setAttribute("name", name);

            switch (role) {
                case 2: // マネージャー
                    request.setAttribute("role", "Manager");
                    break;
                case 3: // ディレクター
                    request.setAttribute("role", "Director");
                    break;
            }

        } catch (Exception e) {
        } finally {

            Review getReviews = em.createNamedQuery("getReviews", Review.class)
                    .setParameter("idea", idea)
                    .setMaxResults(1) // 一件だけ表示
                    .getSingleResult();

            // レビューの経過を取得しInteger型に変換
            Integer reviewFlag = getReviews.getReviewStatus();

            if (reviewFlag == 1 || reviewFlag == 3) {
                request.setAttribute("reviewFlag", "差し戻し");

            } else if (reviewFlag == 4 || reviewFlag == 6) {
                request.setAttribute("reviewFlag", "承認");
            }

            request.setAttribute("reviewFlag", reviewFlag);
        }

        em.close();

        request.setAttribute("idea", idea);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/show.jsp");
        rd.forward(request, response);
    }

}