package filters;

import java.io.IOException;
import java.util.ArrayList;
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

import models.Member;
import utils.DBUtil;

@WebFilter(urlPatterns = {"/ideas/*", "/members/*", "/timeline/*" })
public class FollowCheckFilter implements Filter {

    public FollowCheckFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) ((HttpServletRequest) request).getSession().getAttribute("loginMember");

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("loginMember", loginMember)
                .getResultList();

        List<Integer> followIdea = new ArrayList<Integer>();

        for (Member ideaId : checkMyFollow) {
            followIdea.add(ideaId.getId());
            request.setAttribute("followIdea", followIdea);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
