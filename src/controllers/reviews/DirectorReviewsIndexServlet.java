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

@WebServlet("/reviews/director")
public class DirectorReviewsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DirectorReviewsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ディレクターのレビュー待ちideaを取得
        List<Idea> getDirectorReviews = em.createNamedQuery("getDirectorReviews", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ログイン中メンバーの下書き総数を取得
        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // 下書きを除いたidea総数を取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getDirectorReviews", getDirectorReviews);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);

        // トーストメッセージをセッションにセット
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reviews/director.jsp");
        rd.forward(request, response);
    }

}