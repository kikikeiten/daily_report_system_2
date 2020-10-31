package controllers.attendances;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
 * Servlet implementation class PunchOutCreateServlet
 */
@WebServlet("/punchout/create")
public class PunchOutCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PunchOutCreateServlet() {
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

        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        Integer getMyLatestAttendance = (Integer) em.createNamedQuery("getMyLatestAttendance", Integer.class)
                .setParameter("employee", e)
                .setMaxResults(1)
                .getSingleResult();

        Attendance a = em.find(Attendance.class, getMyLatestAttendance);

        a.setEmployee(e);
        a.setAttendance_date(a.getAttendance_date());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setPunch_in(a.getPunch_in());
        a.setPunch_out(currentTime);
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);

        LocalDateTime nowLocalDate = LocalDateTime.now();
        LocalDateTime punch_in = a.getPunch_in().toLocalDateTime();

        long minutes = ChronoUnit.MINUTES.between(punch_in, nowLocalDate);

        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        String diff_time = diff_hours + ":" + diff_minutes + ":00";
        Time working_time = Time.valueOf(diff_time);

        a.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/");
    }

}