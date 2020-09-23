package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getAllReports", Report.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        //フォロー判定
        List<Employee> getMyReportEmployee = em.createNamedQuery("getMyReportEmployee", Employee.class)
                .getResultList();

        System.out.println("レポートを書いた従業員idは" + getMyReportEmployee + "です。");

        for (Employee report_employee : getMyReportEmployee) {
            List<Employee> checkMyFollow = em.createNamedQuery("checkMyFollow", Employee.class)
                    .setParameter("employee", login_employee)
                    .getResultList();

            System.out.println("ログイン中の従業員がフォローしている従業員idは" + checkMyFollow + "です。");
            System.out.println("レポートの従業員idは" + report_employee + "です。");

            request.setAttribute("report_employee", report_employee);
            request.setAttribute("checkMyFollow", checkMyFollow);

            boolean follow_count = checkMyFollow.contains(report_employee);
            System.out.println("contains(boolean follow_count)で「report_employee」の検索結果：" + follow_count);

            request.setAttribute("follow_count", follow_count);
        }
        //フォロー判定ここまで

        long reports_count = (long) em.createNamedQuery("getReportsCount", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}
