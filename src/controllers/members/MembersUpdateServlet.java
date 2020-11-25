package controllers.members;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.validators.MemberValidator;
import utils.DBUtil;
import utils.EncryptUtil;

@WebServlet("/members/update")
public class MembersUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Member member = em.find(Member.class, (Integer) (request.getSession().getAttribute("memberId")));

            // 現在の値と異なるメンバーIDが入力されていたら重複チェック
            Boolean codeDuplicateCheck = true;

            if (member.getCode().equals(request.getParameter("code"))) {
                codeDuplicateCheck = false;
            } else {
                member.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったらパスワードの入力値チェック
            Boolean passwordCheckFlag = true;

            String password = request.getParameter("password");

            if (password == null || password.equals("")) {
                passwordCheckFlag = false;
            } else {
                member.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String) this.getServletContext().getAttribute("pepper")));
            }

            // Memberテーブルに値をセット
            member.setName(request.getParameter("name"));
            member.setRole(Integer.parseInt(request.getParameter("role")));
            member.setDeleteFlag(0);
            member.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            List<String> errors = MemberValidator.validate(member, codeDuplicateCheck, passwordCheckFlag);

            // エラーがあればパラメータとして送信
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("member", member);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/edit.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                // トーストメッセージをセッションにセット
                request.getSession().setAttribute("toast", "Update completed!");

                request.getSession().removeAttribute("memberId");

                response.sendRedirect(request.getContextPath() + "/members");
            }
        }
    }

}