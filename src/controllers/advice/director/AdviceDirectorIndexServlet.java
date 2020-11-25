package controllers.advice.director;

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

@WebServlet("/advice/director")
public class AdviceDirectorIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdviceDirectorIndexServlet() {
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

        // ディレクターのアドバイス有アイデアを取得
        List<Idea> getDirectorAdvice = em.createNamedQuery("getDirectorAdvice", Idea.class)
                .setParameter("loginMember", loginMember)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ディレクターのアドバイス有アイデア総数を取得
        long getDirectorAdviceCnt = (long) em.createNamedQuery("getDirectorAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ログイン中メンバーのドラフト総数を取得
        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのアドバイス有アイデアを取得
        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ドラフトを除いたアイデア総数を取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getDirectorAdvice", getDirectorAdvice);
        request.setAttribute("getDirectorAdviceCnt", getDirectorAdviceCnt);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);

        // トーストメッセージがセッションに保存されているか確認
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/advice/director/index.jsp");
        rd.forward(request, response);
    }

}