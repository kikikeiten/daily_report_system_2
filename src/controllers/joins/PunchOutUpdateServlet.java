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

        // 退席したメンバーのIDを取得
        Join join = em.find(Join.class, Integer.parseInt(request.getParameter("join_id")));

        // 入力された時刻をString型で保存
        String punch_out_time_str = Integer.parseInt(request.getParameter("punch_out_hour")) + ":"
                + Integer.parseInt(request.getParameter("punch_out_minute")) + ":00";

        // Time型に変換
        Time punch_out_time = Time.valueOf(punch_out_time_str);
        // 詳細な現在時刻を取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Joinテーブルに値をセット
        join.setPunch_out(punch_out_time);
        join.setJoin_flag(3); // 3 == 退席時間の修正済み
        join.setCreated_at(currentTime);
        join.setUpdated_at(currentTime);

        // 参加時刻をLocalTime型に変換
        LocalTime punch_in_local = join.getPunch_in().toLocalTime();
        // LocalTime型の現在時刻を取得
        LocalTime punch_out_local = punch_out_time.toLocalTime();
        // 現在時刻と参加時間との差分を求める（working）
        long minutes = ChronoUnit.MINUTES.between(punch_in_local, punch_out_local);

        // 時間と分を割り出す
        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        // HH:mmの形でString型に保存
        String diff_time_str = diff_hours + ":" + diff_minutes + ":00";
        // Time型に変換
        Time working_time = Time.valueOf(diff_time_str);

        // Joinテーブルに値をセット
        join.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "退勤時間を修正しました。");

        response.sendRedirect(request.getContextPath() + "/joins/all");
    }

}