package controllers.reports;

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
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
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

            Member e = (Member) request.getSession().getAttribute("login_employee");
            Integer sudmit = Integer.parseInt(request.getParameter("submit"));
            Idea r = new Idea();

            r.setEmployee((Member) request.getSession().getAttribute("login_employee"));

            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            if (rd_str != null && !rd_str.equals("")) {
                report_date = Date.valueOf(request.getParameter("report_date"));
            }

            r.setReport_date(report_date);
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            r.setLikes(0);
            r.setApproval(sudmit);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            List<String> errors = IdeaValidator.validate(r);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();

                if (sudmit == 0) {
                    request.getSession().setAttribute("flush",
                            "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                } else if (sudmit == 2 && (e.getAdmin_flag() == 0 || e.getAdmin_flag() == 1)) {
                    request.getSession().setAttribute("flush", "日報「" + request.getParameter("title") + "」を課長へ提出しました。");
                } else if (sudmit == 2 && e.getAdmin_flag() == 2) {
                    request.getSession().setAttribute("flush", "日報「" + request.getParameter("title") + "」を他課長へ提出しました。");
                } else {
                    request.getSession().setAttribute("flush", "日報「" + request.getParameter("title") + "」を他部長へ提出しました。");
                }

                response.sendRedirect(request.getContextPath() + "/reports");
            }
        }
    }

}