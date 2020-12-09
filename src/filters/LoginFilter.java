package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Member;

@WebFilter("/*") // 全てに適応
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String contextPath = ((HttpServletRequest) request).getContextPath();
        String servletPath = ((HttpServletRequest) request).getServletPath();

        if (!servletPath.matches("/css.*")) { // CSSフォルダ内は認証処理から除外する
            HttpSession session = ((HttpServletRequest) request).getSession();

            // セッションスコープに保存されたメンバー（ログインメンバー）情報を取得
            Member loginMember = (Member) session.getAttribute("loginMember");

            if (!servletPath.equals("/login")) {
                // ログイン画面以外について
                // ログアウトしている状態であれば
                // ログイン画面にリダイレクト
                if (loginMember == null) {
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/login");
                    return;
                }

            } else {
                // ログイン画面について
                // ログインしているのにログイン画面を表示させようとした場合は
                // システムのトップページにリダイレクト
                if (loginMember != null) {
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}