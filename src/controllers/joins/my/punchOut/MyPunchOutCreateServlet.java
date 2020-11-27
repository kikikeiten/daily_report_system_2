package controllers.joins.my.punchOut;

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

@WebServlet("/joins/my/punch-out/create")
public class MyPunchOutCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyPunchOutCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // ログイン中メンバーの最新ジョイン履歴IDを1件取得
        Integer getMyLatestJoinId = em.createNamedQuery("getMyLatestJoinId", Integer.class)
                .setParameter("loginMember", loginMember)
                .setMaxResults(1)
                .getSingleResult();

        Join join = em.find(Join.class, getMyLatestJoinId);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());

        // Joinテーブルに値をセット
        join.setPunchOut(time);
        join.setJoinStatus(0); // 0 == 退席済み
        join.setCreatedAt(timestamp);
        join.setUpdatedAt(timestamp);

        // 現在時刻と参加時刻の差分を求める
        long minutes = ChronoUnit.MINUTES.between(join.getPunchIn().toLocalTime(), LocalTime.now());
        // Time型に変換
        Time workingTime = Time.valueOf(minutes / 60 + ":" + minutes % 60 + ":00");
        // Joinテーブルに値をセット
        join.setWorkingTime(workingTime);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "退勤時間を打刻しました。");

        response.sendRedirect(request.getContextPath() + "/");
    }

}