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

import models.Member;
import utils.DBUtil;

@WebFilter(urlPatterns = { "/index.html", "/idea/*" })
public class IdeasCntFilter implements Filter {

    public IdeasCntFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest) request).getSession();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) session.getAttribute("loginMember");

        // ドラフトを除いたアイデア総数を取得
        long getIdeasCntButDrafts = (long) em.createNamedQuery("getIdeasCntButDrafts", Long.class)
                .getSingleResult();

        // ログイン中メンバーのアイデア総数を取得
        long getMyIdeasCnt = (long) em.createNamedQuery("getMyIdeasCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ログイン中メンバーのドラフト総数を取得
        long getMyDraftsCnt = (long) em.createNamedQuery("getMyDraftsCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのアドバイス有アイデア総数を取得
        long getManagerAdviceCnt = (long) em.createNamedQuery("getManagerAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ディレクターのアドバイス有アイデア総数を取得
        long getDirectorAdviceCnt = (long) em.createNamedQuery("getDirectorAdviceCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        request.setAttribute("getIdeasCntButDrafts", getIdeasCntButDrafts);
        request.setAttribute("getMyIdeasCnt", getMyIdeasCnt);
        request.setAttribute("getMyDraftsCnt", getMyDraftsCnt);
        request.setAttribute("getManagerAdviceCnt", getManagerAdviceCnt);
        request.setAttribute("getDirectorAdviceCnt", getDirectorAdviceCnt);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}