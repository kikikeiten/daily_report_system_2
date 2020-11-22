package controllers.joins;

import java.io.IOException;
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
import utils.DBUtil;

@WebServlet("/attendance/my")
public class MyJoinIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyJoinIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ログイン中メンバーの全join履歴を取得
        List<Join> getMyJoins = em.createNamedQuery("getMyJoins", Join.class)
                .setParameter("login_member", login_member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記カウント
        long getMyJoinCnt = (long) em.createNamedQuery("getMyJoinCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        em.close();

        request.setAttribute("getMyJoins", getMyJoins);
        request.setAttribute("getMyJoinCnt", getMyJoinCnt);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/joins/my.jsp");
        rd.forward(request, response);

    }
}