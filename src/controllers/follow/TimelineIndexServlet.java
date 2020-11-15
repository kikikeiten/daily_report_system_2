package controllers.follow;

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

        Member login_employee = (Member) request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Idea> getMyFollowAllReports = em.createNamedQuery("getMyFollowAllReports", Idea.class)
                .setParameter("employee", login_employee)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
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