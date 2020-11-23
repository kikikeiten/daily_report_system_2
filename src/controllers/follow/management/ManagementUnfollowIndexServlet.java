package controllers.follow.management;

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
import models.Follow;
import utils.DBUtil;

@WebServlet("/management/unfollow")
public class ManagementUnfollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementUnfollowIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 対象メンバーのIDを取得
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("memberId")));

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // 対象メンバーのフォロー一覧を取得
        List<Follow> getMemberFollowing = em.createNamedQuery("getMemberFollowing", Follow.class)
                .setParameter("member", member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記のカウントを取得
        long getMemberFollowingCnt = (long) em.createNamedQuery("getMemberFollowingCnt", Long.class)
                .setParameter("member", member)
                .getSingleResult();

        try {
            // 対象メンバーの氏名を取得
            String memberName = member.getName();
            request.setAttribute("memberName", memberName);
        } catch (Exception e) {
        } finally {
            // 対象メンバーのIDをInteger型で取得
            Integer memberId = Integer.parseInt(request.getParameter("memberId"));
            request.setAttribute("memberId", memberId);
        }

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("member", member);
        request.setAttribute("getMemberFollowing", getMemberFollowing);
        request.setAttribute("getMemberFollowingCnt", getMemberFollowingCnt);

        // トーストメッセージがセッションにあるか確認
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/management/unfollow.jsp");
        rd.forward(request, response);
    }

}