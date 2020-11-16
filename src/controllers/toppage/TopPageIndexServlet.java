package controllers.toppage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import models.Member;
import models.Idea;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
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

        List<Idea> ideas = em.createNamedQuery("getMyAllReports", Idea.class)
                .setParameter("employee", login_employee)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long reports_count = (long) em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
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

        Date now = new Date(System.currentTimeMillis());
        long getYesterdayDraftsCount = (long) em.createNamedQuery("getYesterdayDraftsCount", Long.class)
                .setParameter("now", now)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getYesterdayManagerApprovalsCount = (long) em
                .createNamedQuery("getYesterdayManagerApprovalsCount", Long.class)
                .setParameter("now", now)
                .setParameter("admin_flag", login_employee.getAdmin_flag())
                .getSingleResult();

        long getYesterdayDirectorApprovalsCount = (long) em
                .createNamedQuery("getYesterdayDirectorApprovalsCount", Long.class)
                .setParameter("now", now)
                .setParameter("admin_flag", login_employee.getAdmin_flag())
                .getSingleResult();

        long getReportsCountButDrafts = (long) em.createNamedQuery("getReportsCountButDrafts", Long.class)
                .getSingleResult();

        try {
            Join getMyLatestAttendance = (Join) em
                    .createNamedQuery("getMyLatestAttendance", Join.class)
                    .setParameter("employee", login_employee)
                    .setMaxResults(1)
                    .getSingleResult();

            Integer attendance_flag = getMyLatestAttendance.getAttendance_flag();
            request.setAttribute("attendance_flag", attendance_flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Join> getAllForgetAttendances = em.createNamedQuery("getAllForgetAttendances", Join.class)
                    .setParameter("today", now)
                    .getResultList();

            for (Join set_forget : getAllForgetAttendances) {
                set_forget.setAttendance_flag(2);
                System.out.println("setAttendance_flagの値は" + set_forget.getAttendance_flag() + "です。");

                em.getTransaction().begin();
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();

        request.setAttribute("reports", ideas);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("getMyDraftsCount", getMyDraftsCount);
        request.setAttribute("getManagerRemandReportsCount", getManagerRemandReportsCount);
        request.setAttribute("getDirectorRemandReportsCount", getDirectorRemandReportsCount);
        request.setAttribute("getYesterdayDraftsCount", getYesterdayDraftsCount);
        request.setAttribute("getYesterdayManagerApprovalsCount", getYesterdayManagerApprovalsCount);
        request.setAttribute("getYesterdayDirectorApprovalsCount", getYesterdayDirectorApprovalsCount);
        request.setAttribute("getReportsCountButDrafts", getReportsCountButDrafts);

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}