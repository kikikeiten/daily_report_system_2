package controllers.joins;

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

import models.Join;
import models.Member;
import utils.DBUtil;

@WebServlet("/punchout/create")
public class PunchOutCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PunchOutCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member e = (Member) request.getSession().getAttribute("login_employee");

        Integer getMyLatestAttendanceId = em.createNamedQuery("getMyLatestAttendanceId", Integer.class)
                .setParameter("employee", e)
                .setMaxResults(1)
                .getSingleResult();

        Join a = em.find(Join.class, getMyLatestAttendanceId);

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