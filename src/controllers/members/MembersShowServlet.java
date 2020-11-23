package controllers.members;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import utils.DBUtil;

@WebServlet("/members/show")
public class MembersShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 詳細を確認するメンバーのIDを取得
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("memberId")));

        em.close();

        request.setAttribute("member", member);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/show.jsp");
        rd.forward(request, response);
    }

}