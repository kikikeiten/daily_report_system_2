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

@WebServlet("/members/create")
public class MembersCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Member member = new Member();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            member.setCode(request.getParameter("code"));
            member.setName(request.getParameter("name"));
            member.setPassword( // 暗号化してセットする
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String) this.getServletContext().getAttribute("pepper")));
            member.setRole(Integer.parseInt(request.getParameter("role")));
            member.setDeleteFlag(0); // 初期値
            member.setCreatedAt(timestamp);
            member.setUpdatedAt(timestamp);

            List<String> errors = MemberValidator.validate(member, true, true);

            // エラーがあればパラメータを送信
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("member", member);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/new.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.persist(member);
                em.getTransaction().commit();
                em.close();

                // トーストメッセージをセッションにセット
                request.getSession().setAttribute("toast", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/members");
            }
        }
    }

}