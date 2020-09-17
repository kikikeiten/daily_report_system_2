package cotrollers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowDestroyServlet
 */
@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Integer ei = 0;
        ei = em.createNamedQuery("FollowDestroy", Integer.class)
                .setParameter("follow", Integer.parseInt(request.getParameter("employee_id")))
                .getSingleResult();

        Follow f = em.find(Follow.class, ei);

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "フォロー解除しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}
