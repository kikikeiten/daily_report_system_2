package controllers.joins.all.punchOut;

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

@WebServlet("/joins/all/punch-out/update")
public class AllPunchOutUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AllPunchOutUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 退席したメンバーのIDを取得
        Join join = em.find(Join.class, Integer.parseInt(request.getParameter("id")));

        // 入力された時刻をString型で保存
        String punchOutTimeStr = Integer.parseInt(request.getParameter("punchOutHour")) + ":"
                + Integer.parseInt(request.getParameter("punchOutMinute")) + ":00";

        // Time型に変換
        Time punchOutTime = Time.valueOf(punchOutTimeStr);
        // 詳細な現在時刻を取得
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Joinテーブルに値をセット
        join.setPunchOut(punchOutTime);
        join.setJoinStatus(3); // 3 == 退席時間の修正済み
        join.setCreatedAt(timestamp);
        join.setUpdatedAt(timestamp);

        // 参加時刻をLocalTime型に変換
        LocalTime punchInLocal = join.getPunchIn().toLocalTime();
        // LocalTime型の現在時刻を取得
        LocalTime punchOutLocal = punchOutTime.toLocalTime();
        // 現在時刻と参加時間との差分を求める
        long minutes = ChronoUnit.MINUTES.between(punchInLocal, punchOutLocal);

        // 時間と分を割り出す
        long diffHours = minutes / 60;
        long diffMinutes = minutes % 60;

        // HH:mmの形でString型に保存
        String diffTime = diffHours + ":" + diffMinutes + ":00";
        // Time型に変換
        Time workingTime = Time.valueOf(diffTime);

        // Joinテーブルに値をセット
        join.setWorkingTime(workingTime);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "Fixed \"" + join.getMember().getName() + "\"'s leaving time.");

        response.sendRedirect(request.getContextPath() + "/joins/all");
    }
}