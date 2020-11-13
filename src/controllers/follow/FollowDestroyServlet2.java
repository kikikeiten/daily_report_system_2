package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowDestroyServlet2
 */
@WebServlet("/follow/destroy/2")
public class FollowDestroyServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet2() {
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

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("employee_id")));

        Integer ei = 0;
        ei = em.createNamedQuery("followDestroy", Integer.class)
                .setParameter("follow", e)
                .setParameter("employee", login_employee)
                .getSingleResult();

        Follow f = em.find(Follow.class, ei);

        String unfollow_name = e.getName();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", unfollow_name + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/employees");
    }
}