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
import utils.DBUtil;

@WebServlet("/follow/destroy/2")
public class FollowDestroyServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowDestroyServlet2() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member login_member = (Member) request.getSession().getAttribute("login_member");
        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("member_id")));

        Integer ei = 0;
        ei = em.createNamedQuery("getDestroyFollow", Integer.class)
                .setParameter("followed_id", member)
                .setParameter("login_member", login_member)
                .getSingleResult();

        Follow f = em.find(Follow.class, ei);

        String unfollow_name_str = member.getName();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", unfollow_name_str + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/members");
    }
}