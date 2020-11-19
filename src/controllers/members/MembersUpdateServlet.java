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

            Member m = em.find(Member.class, (Integer) (request.getSession().getAttribute("member_id")));

            // 現在の値と異なるmember_idが入力されていたら
            // 重複チェックを行う指定をする
            Boolean code_duplicate_check = true;

            if (m.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;
            } else {
                m.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;

            String password = request.getParameter("password");

            if (password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                m.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String) this.getServletContext().getAttribute("pepper")));
            }

            m.setName(request.getParameter("name"));
            m.setRole_flag(Integer.parseInt(request.getParameter("role_flag")));
            m.setDelete_flag(0);
            m.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = MemberValidator.validate(m, code_duplicate_check, password_check_flag);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("member", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/edit.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("toast", "Update completed!");

                request.getSession().removeAttribute("member_id");

                response.sendRedirect(request.getContextPath() + "/members");
            }
        }
    }

}