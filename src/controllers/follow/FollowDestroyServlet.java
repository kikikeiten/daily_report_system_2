package controllers.follow;

import java.io.IOException;

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

@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("employee_id")));
        Member login_employee = (Member) request.getSession().getAttribute("login_employee");

        Integer ei = 0;
        ei = em.createNamedQuery("followDestroy", Integer.class)
                .setParameter("follow", r.getEmployee())
                .setParameter("employee", login_employee)
                .getSingleResult();

        Follow f = em.find(Follow.class, ei);

        Member unfollow = r.getEmployee();
        String unfollow_name = unfollow.getName();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", unfollow_name + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/reports");
    }

}