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

@WebServlet("/submission/update")
public class DraftsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DraftsUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member e = (Member) request.getSession().getAttribute("login_employee");
        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("report_id")));

        r.setApproval(Integer.parseInt(request.getParameter("submit")));

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        String title = r.getTitle();

        switch (e.getAdmin_flag()) {
        case 0:
            request.getSession().setAttribute("flush", "日報「" + title + "」を課長に提出しました。");
            break;
        case 1:
            request.getSession().setAttribute("flush", "日報「" + title + "」を課長に提出しました。");
            break;
        case 2:
            request.getSession().setAttribute("flush", "日報「" + title + "」を部長に提出しました。");
            break;
        case 3:
            request.getSession().setAttribute("flush", "日報「" + title + "」を提出しました。");
            break;
        }

        response.sendRedirect(request.getContextPath() + "/drafts");
    }

}