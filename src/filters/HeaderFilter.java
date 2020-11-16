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

import models.Join;
import models.Member;
import utils.DBUtil;

@WebFilter("/*")
public class HeaderFilter implements Filter {

    public HeaderFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest) request).getSession();

        Member login_member = (Member) session.getAttribute("login_member");

        long getMyFollowingCnt = (long) em.createNamedQuery("getMyFollowingCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long getMyFollowerCnt = (long) em.createNamedQuery("getMyFollowerCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        long getManagerReviewsCnt = (long) em.createNamedQuery("getManagerReviewsCnt", Long.class)
                .getSingleResult();

        long getDirectorReviewsCnt = (long) em.createNamedQuery("getDirectorReviewsCnt", Long.class)
                .getSingleResult();

        try {
            Join getMyLatestJoin = (Join) em.createNamedQuery("getMyLatestJoin", Join.class)
                    .setParameter("login_member", login_member)
                    .setMaxResults(1)
                    .getSingleResult();

            Integer latest_join = getMyLatestJoin.getAttendance_flag();
            request.setAttribute("latest_join", latest_join);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long getMyIdeasCnt = (long) em.createNamedQuery("getMyIdeasCnt", Long.class)
                .setParameter("login_member", login_member)
                .getSingleResult();

        em.close();

        request.setAttribute("getMyFollowingCnt", getMyFollowingCnt);
        request.setAttribute("getMyFollowerCnt", getMyFollowerCnt);
        request.setAttribute("getManagerReviewsCnt", getManagerReviewsCnt);
        request.setAttribute("getDirectorReviewsCnt", getDirectorReviewsCnt);
        request.setAttribute("getMyIdeasCnt", getMyIdeasCnt);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}