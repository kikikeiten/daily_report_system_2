package controllers.attendances;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Member;
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

        Member e = (Member) request.getSession().getAttribute("login_employee");

        Integer getMyLatestAttendanceId = em.createNamedQuery("getMyLatestAttendanceId", Integer.class)
                .setParameter("employee", e)
                .setMaxResults(1)
                .getSingleResult();

        Attendance a = em.find(Attendance.class, getMyLatestAttendanceId);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Time currentTime2 = new Time(System.currentTimeMillis());
        a.setPunch_out(currentTime2);
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);
        a.setAttendance_flag(0);

        LocalTime nowLocalDate = LocalTime.now();
        LocalTime punch_in = a.getPunch_in().toLocalTime();

        long minutes = ChronoUnit.MINUTES.between(punch_in, nowLocalDate);

        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        String diff_time = diff_hours + ":" + diff_minutes + ":00";
        Time working_time = Time.valueOf(diff_time);

        a.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "退勤時間を打刻しました。");

        response.sendRedirect(request.getContextPath() + "/");
    }

}