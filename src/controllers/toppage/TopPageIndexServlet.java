package controllers.toppage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TopPageIndexServlet() {
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

        // ログイン中メンバーのアイデアを取得
        List<Idea> getMyIdeas = em.createNamedQuery("getMyIdeas", Idea.class)
                .setParameter("loginMember", loginMember)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // ログイン中メンバーのアイデア総数を取得
        long getMyIdeasCnt = (long) em.createNamedQuery("getMyIdeasCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ログイン中メンバーのドラフト総数を取得
        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのアドバイス有アイデア総数を取得
        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ディレクターのアドバイス有アイデア総数を取得
        long getDirectorAdviceCnt = (long) em.createNamedQuery("getDirectorAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        Date date = new Date(System.currentTimeMillis());

        // ドラフトのポスト忘れ総数を取得（前日以前）
        long get4getMyDraftsCnt = (long) em.createNamedQuery("get4getMyDraftsCnt", Long.class)
                .setParameter("date", date)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのレビュー忘れアイデアを取得（前日以前）
        long get4getManagerReviewsCnt = (long) em
                .createNamedQuery("get4getManagerReviewsCnt", Long.class)
                .setParameter("date", date)
                .setParameter("role", loginMember.getRole())
                .getSingleResult();

        // ディレクターのレビュー忘れアイデアを取得（前日以前）
        long get4getDirectorReviewsCnt = (long) em
                .createNamedQuery("get4getDirectorReviewsCnt", Long.class)
                .setParameter("date", date)
                .setParameter("role", loginMember.getRole())
                .getSingleResult();

        // ドラフトを除いたアイデア総数を取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        try {
            // 退席忘れを取得（履歴がない場合もあるのでtry-catch）
            List<Join> get4getJoins = em.createNamedQuery("get4getJoins", Join.class)
                    .setParameter("date", date)
                    .getResultList();

            // 退席忘れのjoinStatusにそれぞれ2を付与
            for (Join setForget : get4getJoins) {
                setForget.setJoinStatus(2);

                em.getTransaction().begin();
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getMyIdeas", getMyIdeas);
        request.setAttribute("getMyIdeasCnt", getMyIdeasCnt);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getDirectorAdviceCnt", getDirectorAdviceCnt);
        request.setAttribute("get4getMyDraftsCnt", get4getMyDraftsCnt);
        request.setAttribute("get4getManagerReviewsCnt", get4getManagerReviewsCnt);
        request.setAttribute("get4getDirectorReviewsCnt", get4getDirectorReviewsCnt);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);

        // トーストメッセージがある場合はセッションとして保存
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        // フラッシュメッセージがある場合はセッションとして保存
        if (request.getSession().getAttribute("flash") != null) {
            request.setAttribute("flash", request.getSession().getAttribute("flash"));
            request.getSession().removeAttribute("flash");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }
}