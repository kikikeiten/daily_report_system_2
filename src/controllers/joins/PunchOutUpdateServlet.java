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
import utils.DBUtil;

@WebServlet("/leave/update")
public class PunchOutUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PunchOutUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        Join a = em.find(Join.class, Integer.parseInt(request.getParameter("id")));

        String leave_time = Integer.parseInt(request.getParameter("leave_hour")) + ":"
                + Integer.parseInt(request.getParameter("leave_minute")) + ":00";
        Time leave_time2 = Time.valueOf(leave_time);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setPunch_out(leave_time2);
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);
        a.setAttendance_flag(3); // 3 == Fixed flag

        LocalTime punch_in = a.getPunch_in().toLocalTime();
        LocalTime punch_out = leave_time2.toLocalTime();
        long minutes = ChronoUnit.MINUTES.between(punch_in, punch_out);

        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        String diff_time = diff_hours + ":" + diff_minutes + ":00";
        Time working_time = Time.valueOf(diff_time);

        a.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "退勤時間を修正しました。");

        response.sendRedirect(request.getContextPath() + "/attendance/all");
    }

}