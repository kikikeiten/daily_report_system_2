package controllers.members;

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

@WebServlet("/members")
public class MembersIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ページネーション
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        }

        // 全てのメンバー情報を取得
        List<Member> getMembers = em.createNamedQuery("getMembers", Member.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 上記カウントを取得
        long getMembersCnt = (long) em.createNamedQuery("getMembersCnt", Long.class)
                .getSingleResult();

        request.setAttribute("page", page);
        request.setAttribute("getMembers", getMembers);
        request.setAttribute("getMembersCnt", getMembersCnt);

        // トーストメッセージがセッションに保存されているか確認
        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/index.jsp");
        rd.forward(request, response);
    }
}