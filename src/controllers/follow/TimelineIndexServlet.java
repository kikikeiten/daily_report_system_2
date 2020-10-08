package controllers.follow;

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
 * Servlet implementation class TimelineIndexServlet
 */
@WebServlet("/timeline")
public class TimelineIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimelineIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Report> getMyFollowAllReports = em.createNamedQuery("getMyFollowAllReports", Report.class)
                .setParameter("employee", login_employee)
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

            boolean follow_count = checkMyFollow.contains(report_employee);
            System.out.println("contains(boolean follow_count)で「report_employee」の検索結果：" + follow_count);

            request.setAttribute("follow_count", follow_count);
        }
        //フォロー判定ここまで

        long getMyFollowReportsCount = (long) em.createNamedQuery("getMyFollowReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("getMyFollowAllReports", getMyFollowAllReports);
        request.setAttribute("getMyFollowReportsCount", getMyFollowReportsCount);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/timeline.jsp");
        rd.forward(request, response);

    }

}