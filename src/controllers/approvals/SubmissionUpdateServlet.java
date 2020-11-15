package controllers.approvals;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class SubmissionUpdateServlet
 */
@WebServlet("/submission/update")
public class SubmissionUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmissionUpdateServlet() {
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

        Employee e = (Employee) request.getSession().getAttribute("login_employee");
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