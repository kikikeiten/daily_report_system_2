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

        Follow follow = new Follow();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("followed_id")));
        Member member1 = em.find(Member.class, Integer.parseInt(request.getParameter("member_operated")));

        follow.setFollowing_id(member1);
        follow.setFollowed_id(member);
        follow.setCreated_at(currentTime);
        follow.setUpdated_at(currentTime);

        String member_name_str = follow.getName();
        String unfollow_name_str = member.getName();

        Integer member_id_int = member1.getId();

        em.getTransaction().begin();
        em.persist(follow);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", member_name_str + "さんが" + unfollow_name_str + "さんをフォローしました。");

        response.sendRedirect(request.getContextPath() + "/management/follow?id=" + member_id_int);
    }

}