package controllers.likes;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favor;
import models.Idea;
import utils.DBUtil;

@WebServlet("/likes")
public class FavorsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FavorsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("report_id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Favor> favors = em.createNamedQuery("getMyAllLikes", Favor.class)
                .setParameter("report", r)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long likes_count = (long) em.createNamedQuery("getMyLikesCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        Integer reportUrl = Integer.parseInt(request.getParameter("report_id"));

        em.close();

        request.setAttribute("likes", favors);
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("page", page);
        request.setAttribute("report_id", r);
        request.setAttribute("reportUrl", reportUrl);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/likes/index.jsp");
        rd.forward(request, response);
    }

}
