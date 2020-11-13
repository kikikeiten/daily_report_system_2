package controllers.employees;

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

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
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

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        }

        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        long employees_count = (long) em.createNamedQuery("getEmployeesCount", Long.class)
                .getSingleResult();

      //フォロー判定
        List<Employee> checkMyFollow = em.createNamedQuery("checkMyFollow", Employee.class)
                .setParameter("employee", login_employee)
                .getResultList();

        List<Integer> list_report_id = new ArrayList<Integer>();

        for (Employee report_id : checkMyFollow) {
            Integer int_report_id = report_id.getId();
            list_report_id.add(int_report_id);
            System.out.println("ログイン中の従業員がフォローしている従業員id一覧は" + list_report_id + "です。");
            request.setAttribute("list_report_id", list_report_id);
        }

        //フォロー判定ここまで

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}