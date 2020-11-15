package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowerCreateServlet
 */
@WebServlet("/follower/create")
public class FollowerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowerCreateServlet() {
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

        Follow f = new Follow();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Follow ff = em.find(Follow.class, Integer.parseInt(request.getParameter("follow_id")));

        f.setEmployee((Member) request.getSession().getAttribute("login_employee"));
        f.setFollow(ff.getEmployee());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        Member follow = ff.getEmployee();
        String follow_name = follow.getName();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", follow_name + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/reports");
    }

}