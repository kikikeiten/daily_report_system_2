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

import models.Member;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class DirectorRemandIndexServlet
 */
@WebServlet("/remand/director")
public class DirectorRemandIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DirectorRemandIndexServlet() {
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

        Member login_employee = (Member) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Idea> getAllDirectorRemandReports = em.createNamedQuery("getAllDirectorRemandReports", Idea.class)
                .setParameter("employee", login_employee)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long getDirectorRemandReportsCount = (long) em.createNamedQuery("getDirectorRemandReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getMyDraftsCount = (long) em.createNamedQuery("getMyDraftsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getManagerRemandReportsCount = (long) em.createNamedQuery("getManagerRemandReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getReportsCountButDrafts = (long) em.createNamedQuery("getReportsCountButDrafts", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("getAllDirectorRemandReports", getAllDirectorRemandReports);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorRemandReportsCount);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCount", getMyDraftsCount);
        request.setAttribute("getManagerRemandReportsCount", getManagerRemandReportsCount);
        request.setAttribute("getReportsCountButDrafts", getReportsCountButDrafts);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/director_remand.jsp");
        rd.forward(request, response);
    }

}