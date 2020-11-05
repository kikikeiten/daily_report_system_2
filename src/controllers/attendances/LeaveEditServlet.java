package controllers.attendances;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import utils.DBUtil;

/**
 * Servlet implementation class LeaveIndexServlet
 */
@WebServlet(name = "LeaveEditServlet", urlPatterns = { "/leave/edit" })
public class LeaveEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveEditServlet() {
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

        Attendance a = em.find(Attendance.class, Integer.parseInt(request.getParameter("id")));

        request.setAttribute("attendance", a);

        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendances/leave.jsp");
        rd.forward(request, response);
    }

}
