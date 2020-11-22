package controllers.joins;

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

@WebServlet("/punchin/create")
public class PunchInCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PunchInCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member member = (Member) request.getSession().getAttribute("login_member");

        Join join = new Join();

        Date date = new Date(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());

        join.setMember(member);
        join.setJoin_date(date);
        join.setPunch_in(time);
        join.setJoin_flag(1); // 1 == 参加
        join.setCreated_at(timestamp);
        join.setUpdated_at(timestamp);

        em.getTransaction().begin();
        em.persist(join);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", "出勤時間を打刻しました。");

        response.sendRedirect(request.getContextPath() + "/");
    }

}
