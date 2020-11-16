package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Member;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        Member login_employee = (Member) request.getSession().getAttribute("login_employee");

        Integer approval = r.getApproval();

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("employee", login_employee)
                .getResultList();

        List<Integer> list_report_id = new ArrayList<Integer>();

        for (Member report_id : checkMyFollow) {
            Integer int_report_id = report_id.getId();
            list_report_id.add(int_report_id);
            System.out.println("ログイン中の従業員がフォローしている従業員id一覧は" + list_report_id + "です。");
            request.setAttribute("list_report_id", list_report_id);
        }

        //フォロー判定ここまで

        try {
            Review getReportApprovals = em.createNamedQuery("getReportApprovals", Review.class)
                    .setParameter("report", r)
                    .setMaxResults(1)
                    .getSingleResult();
            String comment = getReportApprovals.getComment();
            Member e = getReportApprovals.getEmployee();
            String name = e.getName();
            Integer int_admin_flag = e.getAdmin_flag();
            request.setAttribute("comment", comment);
            request.setAttribute("name", name);
            switch (int_admin_flag) {
            case 2:
                request.setAttribute("position", "課長");
                break;
            case 3:
                request.setAttribute("position", "部長");
                break;
            }
            Integer int_approval = getReportApprovals.getApproval();
            if (int_approval == 1 || int_approval == 3) {
                request.setAttribute("approval_status", "差し戻し");
            } else if (int_approval == 4 || int_approval == 6) {
                request.setAttribute("approval_status", "承認");
            }
        } catch (Exception e) {
        }

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("approval", approval);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}