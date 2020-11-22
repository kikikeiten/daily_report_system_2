package controllers.drafts;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/drafts/update")
public class DraftsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DraftsUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        idea.setReview_flag(Integer.parseInt(request.getParameter("review_flag")));

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        String ideaTitle = idea.getTitle();

        switch (loginMember.getRole_flag()) {
        case 0:
            request.getSession().setAttribute("toast", "日報「" + ideaTitle + "」を課長に提出しました。");
            break;
        case 1:
            request.getSession().setAttribute("toast", "日報「" + ideaTitle + "」を課長に提出しました。");
            break;
        case 2:
            request.getSession().setAttribute("toast", "日報「" + ideaTitle + "」を部長に提出しました。");
            break;
        case 3:
            request.getSession().setAttribute("toast", "日報「" + ideaTitle + "」を提出しました。");
            break;
        }

        response.sendRedirect(request.getContextPath() + "/drafts");
    }

}