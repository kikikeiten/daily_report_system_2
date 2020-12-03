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

import models.Member;
import utils.DBUtil;

@WebFilter(urlPatterns = { "/joins/*" })
public class JoinCntFilter implements Filter {

    public JoinCntFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) ((HttpServletRequest) request).getSession().getAttribute("loginMember");

        // ログイン中メンバーの全ジョイン履歴数を取得
        long getMyJoinCnt = (long) em.createNamedQuery("getMyJoinCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // 全てのジョイン履歴数を取得
        long getJoinsCnt = (long) em.createNamedQuery("getJoinsCnt", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("getMyJoinCnt", getMyJoinCnt);
        request.setAttribute("getJoinsCnt", getJoinsCnt);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}