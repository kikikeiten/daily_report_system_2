package controllers.follow;

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

@WebServlet("/following")
public class FollowingIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ログイン中メンバーのフォロー一覧を取得
        List<Follow> getMyFollowing = em.createNamedQuery("getMyFollowing", Follow.class)
                .setParameter("login_member", login_member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        em.close();

        request.setAttribute("getMyFollowing", getMyFollowing);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/following.jsp");
        rd.forward(request, response);
    }

}