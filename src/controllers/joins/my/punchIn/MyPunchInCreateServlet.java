package controllers.joins.my.punchIn;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import models.Member;
import utils.DBUtil;

@WebServlet("/joins/my/punch-in/create")
public class MyPunchInCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyPunchInCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        Join join = new Join();

        Date date = new Date(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());

        // Joinテーブルに値をセット
        join.setMember(loginMember);
        join.setJoinDate(date);
        join.setPunchIn(time);
        join.setJoinStatus(1); // 1 == 参加
        join.setCreatedAt(timestamp);
        join.setUpdatedAt(timestamp);

        em.getTransaction().begin();
        em.persist(join);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "出勤時間を打刻しました。");

        response.sendRedirect(request.getContextPath() + "/");
    }

}
