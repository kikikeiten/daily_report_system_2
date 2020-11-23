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

import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/ideas")
public class IdeasIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasIndexServlet() {
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

        // ドラフトを除いたアイデアを取得
        List<Idea> getIdeasButDrafts = em.createNamedQuery("getIdeasButDrafts", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記カウントを取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
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

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("getIdeasButDrafts", getIdeasButDrafts);
        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getDirectorAdviceCnt", getDirectorAdviceCnt);

        // トーストメッセージがある場合はセッションとして保存
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/index.jsp");
        rd.forward(request, response);
    }

}