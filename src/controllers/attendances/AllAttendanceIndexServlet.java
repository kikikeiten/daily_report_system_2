package controllers.attendances;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import utils.DBUtil;

/**
 * Servlet implementation class AllAttendanceIndexServlet
 */
@WebServlet("/attendance/all")
public class AllAttendanceIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllAttendanceIndexServlet() {
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

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Join> getAllAttendances = em.createNamedQuery("getAllAttendances", Join.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long getAllAttendancesCount = (long) em.createNamedQuery("getAllAttendancesCount", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("getAllAttendances", getAllAttendances);
        request.setAttribute("getAllAttendancesCount", getAllAttendancesCount);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendances/all_attendance.jsp");
        rd.forward(request, response);

    }

}