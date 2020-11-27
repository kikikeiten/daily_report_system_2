package filters;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

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

@WebFilter("/index.html")
public class ForgetCntFilter implements Filter {

    public ForgetCntFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) ((HttpServletRequest) request).getSession().getAttribute("loginMember");

        Date date = new Date(System.currentTimeMillis());

        // ドラフトのポスト忘れ総数を取得（前日以前）
        long get4getMyDraftsCnt = (long) em.createNamedQuery("get4getMyDraftsCnt", Long.class)
                .setParameter("date", date)
                .setParameter("loginMember", loginMember)
                .getSingleResult();

        try {
            // マネージャーのレビュー忘れアイデアを取得（前日以前）
            long get4getManagerReviewsCnt = (long) em
                    .createNamedQuery("get4getManagerReviewsCnt", Long.class)
                    .setParameter("date", date)
                    .setParameter("role", loginMember.getRole())
                    .getSingleResult();

            // ディレクターのレビュー忘れアイデアを取得（前日以前）
            long get4getDirectorReviewsCnt = (long) em
                    .createNamedQuery("get4getDirectorReviewsCnt", Long.class)
                    .setParameter("date", date)
                    .setParameter("role", loginMember.getRole())
                    .getSingleResult();

            request.setAttribute("get4getManagerReviewsCnt", get4getManagerReviewsCnt);
            request.setAttribute("get4getDirectorReviewsCnt", get4getDirectorReviewsCnt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 退席忘れを取得（履歴がない場合もあるのでtry-catch）
            List<Join> get4getJoins = em.createNamedQuery("get4getJoins", Join.class)
                    .setParameter("date", date)
                    .getResultList();

            // 退席忘れのjoinStatusにそれぞれ2を付与
            for (Join setForget : get4getJoins) {
                setForget.setJoinStatus(2);

                em.getTransaction().begin();
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();

        request.setAttribute("get4getMyDraftsCnt", get4getMyDraftsCnt);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}