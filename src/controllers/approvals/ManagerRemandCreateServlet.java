package controllers.approvals;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ManagerRemandCreateServlet
 */
@WebServlet("/manager/remand/reate")
public class ManagerRemandCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerRemandCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee e = (Employee) request.getSession().getAttribute("login_employee");
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
        Integer submit = Integer.parseInt(request.getParameter("submit"));

        r.setApproval(Integer.parseInt(request.getParameter("submit")));

        Approval a = new Approval();

        a.setReport(r);
        a.setEmployee(e);
        a.setApproval(submit);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/remand/manager");
    }

}