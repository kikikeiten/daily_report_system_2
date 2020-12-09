package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import utils.DBUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    // ログイン画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", false);

        // フラッシュメッセージがセッションにあるか確認
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/index.jsp");
        rd.forward(request, response);
    }

    // ログイン処理を実行
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 認証結果を格納する変数
        Boolean checkResult = false;

        String code = request.getParameter("code");
        String plainPass = request.getParameter("password");

        Member member = null;

        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("")) {

            EntityManager em = DBUtil.createEntityManager();

            String password = "1";

            /*
            String password = EncryptUtil.getPasswordEncrypt(
                    plainPass,
                    (String) this.getServletContext().getAttribute("pepper"));
                    */

            // 社員番号とパスワードが正しいかチェックする
            try {
                member = em.createNamedQuery("checkLoginCodeAndPassword", Member.class)
                        .setParameter("code", code)
                        .setParameter("pass", password)
                        .getSingleResult();
            } catch (NoResultException ex) {
            }

            em.close();

            if (member != null) {
                checkResult = true;
            }
        }

        if (!checkResult) {
            // 認証できなかったらログイン画面に戻る
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("hasError", true);
            request.setAttribute("code", code);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/index.jsp");
            rd.forward(request, response);
        } else {
            // 認証できたらログイン状態にしてトップページへリダイレクト
            request.getSession().setAttribute("loginMember", member);

            // トーストメッセージをセッションにセット
            request.getSession().setAttribute("toast", "You're now logged.");

            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}