package controllers.follow.management;

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
 * Servlet implementation class ManagementFollowDestroy
 */
@WebServlet("/management/follow/destroy")
public class ManagementFollowDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementFollowDestroy() {
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
        Follow f = em.find(Follow.class, Integer.parseInt(request.getParameter("follow_id")));

        Employee employee = f.getEmployee();
        String employee_name = employee.getName();

        Employee unfollow = f.getFollow();
        String unfollow_name = unfollow.getName();

        Employee following_id = f.getEmployee();
        Integer employee_id = following_id.getId();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", employee_name + "さんが" + unfollow_name + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/management/unfollow?id=" + employee_id);
    }

}