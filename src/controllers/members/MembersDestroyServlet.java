package controllers.members;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import utils.DBUtil;

@WebServlet("/members/destroy")
public class MembersDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            // member_idのセッションからメンバーidを取得
            Member m = em.find(Member.class, (Integer) (request.getSession().getAttribute("member_id")));

            m.setDelete_flag(1); // 論理削除
            m.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("toast", "削除が完了しました。");

            response.sendRedirect(request.getContextPath() + "/members");
        }
    }

}