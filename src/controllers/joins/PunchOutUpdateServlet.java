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

@WebServlet("/punchout/update")
public class PunchOutUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PunchOutUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Join join = em.find(Join.class, Integer.parseInt(request.getParameter("join_id")));

        String punch_out_time_str = Integer.parseInt(request.getParameter("punch_out_hour")) + ":"
                + Integer.parseInt(request.getParameter("punch_out_minute")) + ":00";

        Time punch_out_time = Time.valueOf(punch_out_time_str);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        join.setPunch_out(punch_out_time);
        join.setJoin_flag(3); // 3 == 退席時間の修正済み
        join.setCreated_at(currentTime);
        join.setUpdated_at(currentTime);

        LocalTime punch_in_local = join.getPunch_in().toLocalTime();
        LocalTime punch_out_local = punch_out_time.toLocalTime();
        long minutes = ChronoUnit.MINUTES.between(punch_in_local, punch_out_local);

        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        String diff_time_str = diff_hours + ":" + diff_minutes + ":00";
        Time working_time = Time.valueOf(diff_time_str);

        join.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", "退勤時間を修正しました。");

        response.sendRedirect(request.getContextPath() + "/joins/all");
    }

}