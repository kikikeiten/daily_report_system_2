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

            // 削除するメンバーIDをセッションから取得
            Member member = em.find(Member.class, (Integer) (request.getSession().getAttribute("memberId")));

            member.setDeleteFlag(1); // 論理削除
            member.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            // トーストメッセージをセッションにセット
            request.getSession().setAttribute("toast", "削除が完了しました。");

            response.sendRedirect(request.getContextPath() + "/members");
        }
    }

}