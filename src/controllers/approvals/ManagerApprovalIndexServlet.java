package controllers.approvals;

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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ManagerIndexServlet
 */
@WebServlet("/approval/manager")
public class ManagerApprovalIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerApprovalIndexServlet() {
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

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Report> getAllManagerApprovalReports = em.createNamedQuery("getAllManagerApprovalReports", Report.class)
                .setParameter("employee", login_employee)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long getManagerApprovalReportsCount = (long) em.createNamedQuery("getManagerApprovalReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getMyDraftsCount = (long) em.createNamedQuery("getMyDraftsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getDirectorRemandReportsCount = (long) em.createNamedQuery("getDirectorRemandReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("getAllManagerApprovalReports", getAllManagerApprovalReports);
        request.setAttribute("getManagerApprovalReportsCount", getManagerApprovalReportsCount);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCount", getMyDraftsCount);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorRemandReportsCount);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/manager_approval.jsp");
        rd.forward(request, response);
    }

}