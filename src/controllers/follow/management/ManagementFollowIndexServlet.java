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
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndexServlet
 */
@WebServlet("/management/follow")
public class ManagementFollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementFollowIndexServlet() {
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

        Member ee = em.find(Member.class, Integer.parseInt(request.getParameter("id")));

        System.out.println("リストの従業員idは" + ee + "です。");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Member> getEmployeeNotFollowing = em.createNamedQuery("getEmployeeNotFollowing", Member.class)
                .setParameter("employee", ee)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        System.out.println("リストの従業員がフォローしていない従業員は" + getEmployeeNotFollowing + "です。");

        long getEmployeeNotFollowingCount = (long) em.createNamedQuery("getEmployeeNotFollowingCount", Long.class)
                .setParameter("employee", ee)
                .getSingleResult();

        try {
            String employee_name = ee.getName();
            Integer employee_operated = ee.getId();

            request.setAttribute("employee_name", employee_name);
            request.setAttribute("employee_operated", employee_operated);
        } catch (Exception e) {

        }
        Integer page_number = Integer.parseInt(request.getParameter("id"));

        em.close();

        request.setAttribute("employee", ee);
        request.setAttribute("getEmployeeNotFollowing", getEmployeeNotFollowing);
        request.setAttribute("page", page);
        request.setAttribute("getEmployeeNotFollowingCount", getEmployeeNotFollowingCount);
        request.setAttribute("page_number", page_number);

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/management/follow.jsp");
        rd.forward(request, response);
    }

}