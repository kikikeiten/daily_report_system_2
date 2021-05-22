package controllers.follows.management.unfollowing;

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
import utils.DBUtil;

@WebServlet("/management/unfollowing")
public class ManagementsUnfollowingIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementsUnfollowingIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 対象のメンバーIDを取得
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("id")));

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // 対象のメンバーがフォローしていないメンバー一覧を取得
        List<Member> getMemberNotFollowing = em.createNamedQuery("getMemberNotFollowing", Member.class)
                .setParameter("member", member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記カウントを取得
        long getMemberNotFollowingCnt = (long) em.createNamedQuery("getMemberNotFollowingCnt", Long.class)
                .setParameter("member", member)
                .getSingleResult();

        // 対象メンバーのフォロー総数を取得
        long getMemberFollowingCnt = (long) em.createNamedQuery("getMemberFollowingCnt", Long.class)
                .setParameter("member", member)
                .getSingleResult();

        // フォローする側のメンバーIDを取得
        Integer followingId = member.getId();

        em.close();

        request.setAttribute("member", member);
        request.setAttribute("page", page);
        request.setAttribute("getMemberNotFollowing", getMemberNotFollowing);
        request.setAttribute("getMemberNotFollowingCnt", getMemberNotFollowingCnt);
        request.setAttribute("getMemberFollowingCnt", getMemberFollowingCnt);
        request.setAttribute("followingId", followingId);

        // トーストメッセージのセッションがあるか確認
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/management/unfollowing/index.jsp");
        rd.forward(request, response);
    }

}