package controllers.follow.management;

import java.io.IOException;
import java.sql.Timestamp;

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
 * Servlet implementation class ManagementFollowCreateServlet
 */
@WebServlet("/management/follow/create")
public class ManagementFollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementFollowCreateServlet() {
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
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("follow_id")));
        Employee ee = em.find(Employee.class, Integer.parseInt(request.getParameter("employee_operated")));

        f.setEmployee(ee);
        f.setFollow(e);
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        String employee_name = ee.getName();
        System.out.println("フォローする側の従業員氏名は" + employee_name + "です。");

        String unfollow_name = e.getName();
        System.out.println("フォローされる側の従業員氏名は" + unfollow_name + "です。");

        Integer employee_id = ee.getId();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", employee_name + "さんが" + unfollow_name + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/management/follow?id=" + employee_id);
    }

}