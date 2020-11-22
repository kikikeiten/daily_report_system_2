package controllers.reviews;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;
import utils.DBUtil;

@WebServlet("/advice/manager/create")
public class ManagerAdviceCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagerAdviceCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        idea.setReview_flag(Integer.parseInt(request.getParameter("reviewFlag")));

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("toast", "日報「" + idea.getTitle() + "」を課長に再提出しました。");

        response.sendRedirect(request.getContextPath() + "/advice/manager");
    }

}