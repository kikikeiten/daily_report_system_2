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

@WebServlet("/manager/remand/create")
public class ManagerAdviceCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagerAdviceCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("report_id")));

        r.setApproval(Integer.parseInt(request.getParameter("submit")));

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "日報「" + r.getTitle() + "」を課長に再提出しました。");

        response.sendRedirect(request.getContextPath() + "/remand/manager");
    }

}