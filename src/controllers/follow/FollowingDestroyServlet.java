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

@WebServlet("/following/destroy")
public class FollowingDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Follow followed = em.find(Follow.class, Integer.parseInt(request.getParameter("followed_id")));

        Member unfollow = followed.getFollow();
        String unfollow_name_str = unfollow.getName();

        em.getTransaction().begin();
        em.remove(followed);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", unfollow_name_str + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/ideas");
    }

}