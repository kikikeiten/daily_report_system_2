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

import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/approval/manager")
public class ManagerReviewsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagerReviewsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // マネージャーのレビュー待ちアイデアを取得
        List<Idea> getManagerReviews = em.createNamedQuery("getManagerReviews", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ログイン中メンバーのドラフト総数を取得
        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのアドバイス有アイデア総数を取得
        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ドラフトを除いたアイデア総数を取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getManagerReviews", getManagerReviews);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);

        // トーストメッセージがセッションに保存されているか確認
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reviews/manager/index.jsp");
        rd.forward(request, response);
    }

}