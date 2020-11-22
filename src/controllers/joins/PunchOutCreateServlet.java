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

        // ログイン中のメンバーIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        // ログイン中メンバーの最新join履歴idを取得
        Integer getMyLatestJoinId = em.createNamedQuery("getMyLatestJoinId", Integer.class)
                .setParameter("login_member", login_member)
                .setMaxResults(1)
                .getSingleResult();

        Join join = em.find(Join.class, getMyLatestJoinId);

        // それぞれの時刻を取得
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());

        // Joinテーブルに値をセット
        join.setPunch_out(time);
        join.setJoin_flag(0); // 0 == 退席済み
        join.setCreated_at(timestamp);
        join.setUpdated_at(timestamp);

        // LocalTime型の現在時刻を取得
        LocalTime localTime = LocalTime.now();
        // 参加時刻をLocalDate型に変換
        LocalTime punch_in_local = join.getPunch_in().toLocalTime();

        // 現在時刻と参加時刻の差分を求める（working time）
        long minutes = ChronoUnit.MINUTES.between(punch_in_local, localTime);

        // 時間と分を求める
        long diff_hours = minutes / 60;
        long diff_minutes = minutes % 60;

        // HH:mmのString型に変換
        String diff_time_str = diff_hours + ":" + diff_minutes + ":00";
        // Time型に変換
        Time working_time = Time.valueOf(diff_time_str);

        // Joinテーブルに値をセット
        join.setWorking(working_time);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "退勤時間を打刻しました。");

        response.sendRedirect(request.getContextPath() + "/");
    }

}