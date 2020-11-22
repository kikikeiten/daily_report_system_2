package controllers.follow.management;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/management/follow/destroy")
public class ManagementFollowDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementFollowDestroy() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        Follow f = em.find(Follow.class, Integer.parseInt(request.getParameter("follow_id")));

        Member member = f.getEmployee();
        String employee_name = member.getName();

        Member unfollow = f.getFollow();
        String unfollow_name = unfollow.getName();

        Member following_id = f.getEmployee();
        Integer employee_id = following_id.getId();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", employee_name + "さんが" + unfollow_name + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/management/unfollow?id=" + employee_id);
    }

}