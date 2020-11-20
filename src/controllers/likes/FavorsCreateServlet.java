package controllers.likes;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Favor;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class LikesCreateServlet
 */
@WebServlet("/likes/create")
public class FavorsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavorsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("report_id")));

        Favor l = new Favor();

        l.setEmployee((Member) request.getSession().getAttribute("login_employee"));
        l.setReport(r);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        l.setCreated_at(currentTime);
        l.setUpdated_at(currentTime);

        r.setLikes(Integer.parseInt(request.getParameter("likes")) + r.getLikes());

        Member member = r.getEmployee();
        String employee_name = member.getName();

        String report_title = r.getTitle();

        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", employee_name + "さんの日報「" + report_title + "」にいいねしました。");

        response.sendRedirect(request.getContextPath() + "/reports");

    }
}