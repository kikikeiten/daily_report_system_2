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

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

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

            int follow_count = checkMyFollow.indexOf(report_employee);
            System.out.println("indexOf(int follow_count)で「report_employee」の検索結果：" + follow_count);

            request.setAttribute("follow_count", follow_count);
        }
        //フォロー判定ここまで

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}