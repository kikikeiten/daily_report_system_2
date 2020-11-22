package controllers.reviews;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class HistoryIndexServlet
 */
@WebServlet("/approval/history")
public class HistoryIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryIndexServlet() {
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

        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Review> getReportApprovals = em.createNamedQuery("getReportApprovals", Review.class)
                .setParameter("report", r)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long getReportApprovalsCount = (long) em.createNamedQuery("getReportApprovalsCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        Integer report_id = r.getId();
        String report_title = r.getTitle();

        em.close();

        request.setAttribute("getReportApprovals", getReportApprovals);
        request.setAttribute("page", page);
        request.setAttribute("getReportApprovalsCount", getReportApprovalsCount);
        request.setAttribute("report_id", report_id);
        request.setAttribute("report_title", report_title);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/history.jsp");
        rd.forward(request, response);
    }

}