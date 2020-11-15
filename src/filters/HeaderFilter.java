package filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Attendance;
import models.Member;
import utils.DBUtil;

/**
 * Servlet Filter implementation class HeaderFilter
 */
@WebFilter("/*")
public class HeaderFilter implements Filter {

    /**
     * Default constructor.
     */
    public HeaderFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest) request).getSession();

        Member login_employee = (Member) session.getAttribute("login_employee");

        long getMyFollowingCount = (long) em.createNamedQuery("getMyFollowingCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getMyFollowerCount = (long) em.createNamedQuery("getMyFollowerCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long getManagerApprovalReportsCount = (long) em.createNamedQuery("getManagerApprovalReportsCount", Long.class)
                .getSingleResult();

        long getDirectorApprovalReportsCount = (long) em.createNamedQuery("getDirectorApprovalReportsCount", Long.class)
                .getSingleResult();

        try {
            Attendance getMyLatestAttendance = (Attendance) em.createNamedQuery("getMyLatestAttendance", Attendance.class)
                    .setParameter("employee", login_employee)
                    .setMaxResults(1)
                    .getSingleResult();

            Integer attendance_flag = getMyLatestAttendance.getAttendance_flag();
            request.setAttribute("attendance_flag", attendance_flag);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long reports_count = (long) em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("getMyFollowingCount", getMyFollowingCount);
        request.setAttribute("getMyFollowerCount", getMyFollowerCount);
        request.setAttribute("getManagerApprovalReportsCount", getManagerApprovalReportsCount);
        request.setAttribute("getDirectorApprovalReportsCount", getDirectorApprovalReportsCount);
        request.setAttribute("reports_count", reports_count);

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}