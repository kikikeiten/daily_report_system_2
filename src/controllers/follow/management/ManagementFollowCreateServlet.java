package controllers.follow.management;

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

@WebServlet("/management/follow/create")
public class ManagementFollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementFollowCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Member e = em.find(Member.class, Integer.parseInt(request.getParameter("follow_id")));
        Member ee = em.find(Member.class, Integer.parseInt(request.getParameter("employee_operated")));

        f.setEmployee(ee);
        f.setFollow(e);
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        String employee_name = ee.getName();
        System.out.println("フォローする側の従業員氏名は" + employee_name + "です。");

        String unfollow_name = e.getName();
        System.out.println("フォローされる側の従業員氏名は" + unfollow_name + "です。");

        Integer employee_id = ee.getId();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", employee_name + "さんが" + unfollow_name + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/management/follow?id=" + employee_id);
    }

}