package controllers.attendances;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class PunchInCreateServlet
 */
@WebServlet("/punchin/create")
public class PunchInCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PunchInCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();
        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        Attendance a = new Attendance();

        a.setEmployee(e);

        Date attendance_date = new Date(System.currentTimeMillis());
        a.setAttendance_date(attendance_date);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setPunch_in(currentTime);
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/");
    }

}
