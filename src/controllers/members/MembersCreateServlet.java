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

            Member m = new Member();
            // 現在の詳細な時刻を追加
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            m.setCode(request.getParameter("code"));
            m.setName(request.getParameter("name"));
            m.setPassword( // 暗号化してセットする
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String) this.getServletContext().getAttribute("pepper")));
            m.setRole_flag(Integer.parseInt(request.getParameter("role_flag")));
            m.setDelete_flag(0); // 初期値
            m.setCreated_at(currentTime);
            m.setUpdated_at(currentTime);

            List<String> errors = MemberValidator.validate(m, true, true);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("member", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/new.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("toast", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/members");
            }
        }
    }

}