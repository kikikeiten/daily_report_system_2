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

import models.Join;
import models.Member;
import utils.DBUtil;

@WebFilter("/*") // 全てに適応
public class HeaderFilter implements Filter {

    public HeaderFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) ((HttpServletRequest) request).getSession().getAttribute("loginMember");

        // ログイン中メンバーのフォロー一覧総数を取得
        long getMyFollowingCnt = (long) em.createNamedQuery("getMyFollowingCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // ログイン中メンバーのフォロワー一覧総数を取得
        long getMyFollowerCnt = (long) em.createNamedQuery("getMyFollowerCnt", Long.class)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        // マネージャーのレビュー待ちアイデア総数を取得
        long getManagerReviewsCnt = (long) em.createNamedQuery("getManagerReviewsCnt", Long.class)
                .getSingleResult();

        // ディレクターのレビュー待ちアイデア総数を取得
        long getDirectorReviewsCnt = (long) em.createNamedQuery("getDirectorReviewsCnt", Long.class)
                .getSingleResult();

        try {
            // ログイン中メンバーの最新ジョイン履歴を取得（履歴がない場合もあるのでtry-catch）
            Join getMyLatestJoin = (Join) em
                    .createNamedQuery("getMyLatestJoin", Join.class)
                    .setParameter("loginMember", loginMember)
                    .setMaxResults(1) // 一件だけ取得
                    .getSingleResult();

            Integer latestJoin = getMyLatestJoin.getJoinStatus(); // Join型からInteger型に変換
            request.setAttribute("latestJoin", latestJoin);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ログイン中メンバーのアイデア総数を取得
        long getMyIdeasCnt = (long) em.createNamedQuery("getMyIdeasCnt", Long.class)
                .setParameter("loginMember", loginMember)
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