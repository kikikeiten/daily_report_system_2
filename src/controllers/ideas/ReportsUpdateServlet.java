package controllers.ideas;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Idea;
import models.validators.IdeaValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Idea r = em.find(Idea.class, (Integer) (request.getSession().getAttribute("report_id")));
            Integer submit = Integer.parseInt(request.getParameter("submit"));
            Member e = (Member) request.getSession().getAttribute("login_employee");

            r.setReport_date(Date.valueOf(request.getParameter("report_date")));
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            r.setApproval(submit);

            List<String> errors = IdeaValidator.validate(r);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                if (e.getAdmin_flag() == 0 || e.getAdmin_flag() == 1) {
                    switch (submit) {
                    case 0:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 2:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を課長に提出しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                        break;
                    }
                }

                if (e.getAdmin_flag() == 2) {
                    switch (submit) {
                    case 0:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 2:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を他課長に提出しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                        break;
                    }
                }

                if (e.getAdmin_flag() == 3) {
                    switch (submit) {
                    case 0:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("flush",
                                "日報「" + request.getParameter("title") + "」を他部長に提出しました。");
                        break;
                    }
                }

                request.getSession().removeAttribute("report_id");

                response.sendRedirect(request.getContextPath() + "/reports");
            }
        }
    }

}