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
import models.Idea;
import utils.DBUtil;

@WebServlet("/follow/create")
public class FollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("following")));

        f.setEmployee((Member) request.getSession().getAttribute("login_employee"));
        f.setFollow(r.getEmployee());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        Member unfollow = r.getEmployee();
        String unfollow_name = unfollow.getName();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", unfollow_name + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/reports");
    }

}