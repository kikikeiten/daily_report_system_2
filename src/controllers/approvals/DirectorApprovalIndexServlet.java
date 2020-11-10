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
 * Servlet implementation class DirectorApprovalIndexServlet
 */
@WebServlet("/approval/director")
public class DirectorApprovalIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DirectorApprovalIndexServlet() {
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

        List<Report> getAllDirectorApprovalReports = em.createNamedQuery("getAllDirectorApprovalReports", Report.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long getMyDraftsCount = (long) em.createNamedQuery("getMyDraftsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getReportsCountButDrafts = (long) em.createNamedQuery("getReportsCountButDrafts", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("getAllDirectorApprovalReports", getAllDirectorApprovalReports);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCount", getMyDraftsCount);
        request.setAttribute("getReportsCountButDrafts", getReportsCountButDrafts);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/director_approval.jsp");
        rd.forward(request, response);
    }

}