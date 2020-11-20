package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follower/create")
public class FollowerCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowerCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Follow ff = em.find(Follow.class, Integer.parseInt(request.getParameter("follow_id")));

        f.setEmployee((Member) request.getSession().getAttribute("login_employee"));
        f.setFollow(ff.getEmployee());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        Member follow = ff.getEmployee();
        String follow_name = follow.getName();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", follow_name + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/reports");
    }

}