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

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class UnfollowIndexServlet
 */
@WebServlet("/management/unfollow")
public class ManagementUnfollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementUnfollowIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Employee ee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Follow> getEmployeeFollowing = em.createNamedQuery("getEmployeeFollowing", Follow.class)
                .setParameter("employee", ee)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long getEmployeeFollowingCount = (long) em.createNamedQuery("getEmployeeFollowingCount", Long.class)
                .setParameter("employee", ee)
                .getSingleResult();

        String employee_name = ee.getName();
        Integer page_number = Integer.parseInt(request.getParameter("id"));

        em.close();

        request.setAttribute("getEmployeeFollowing", getEmployeeFollowing);
        request.setAttribute("page", page);
        request.setAttribute("getEmployeeFollowingCount", getEmployeeFollowingCount);
        request.setAttribute("employee_name", employee_name);
        request.setAttribute("page_number", page_number);

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/management/unfollow.jsp");
        rd.forward(request, response);
    }

}