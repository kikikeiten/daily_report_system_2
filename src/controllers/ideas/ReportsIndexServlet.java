package controllers.ideas;

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

import models.Member;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports")
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

        Member login_employee = (Member) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Idea> getAllReportsButDrafts = em.createNamedQuery("getAllReportsButDrafts", Idea.class)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

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

        long getReportsCountButDrafts = (long) em.createNamedQuery("getReportsCountButDrafts", Long.class)
                .getSingleResult();

        long getMyDraftsCount = (long) em.createNamedQuery("getMyDraftsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getManagerRemandReportsCount = (long) em.createNamedQuery("getManagerRemandReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getDirectorRemandReportsCount = (long) em.createNamedQuery("getDirectorRemandReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("getAllReportsButDrafts", getAllReportsButDrafts);
        request.setAttribute("getReportsCountButDrafts", getReportsCountButDrafts);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCount", getMyDraftsCount);
        request.setAttribute("getManagerRemandReportsCount", getManagerRemandReportsCount);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorRemandReportsCount);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}