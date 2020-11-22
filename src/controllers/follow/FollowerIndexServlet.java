package controllers.follow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follower")
public class FollowerIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowerIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // ログイン中のメンバーIDを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ログイン中メンバーのフォロワー一覧を取得
        List<Follow> getMyFollower = em.createNamedQuery("getMyFollower", Follow.class)
                .setParameter("login_member", login_member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        //フォロー判定
        List<Member> checkMyFollow = em.createNamedQuery("checkMyFollow", Member.class)
                .setParameter("login_member", login_member)
                .getResultList();

        List<Integer> follow_idea_id = new ArrayList<Integer>();

        for (Member idea_id : checkMyFollow) {
            Integer idea_id_int = idea_id.getId();
            follow_idea_id.add(idea_id_int);
            request.setAttribute("follow_idea_id", follow_idea_id);
        }

        em.close();

        request.setAttribute("getMyFollower", getMyFollower);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/follower.jsp");
        rd.forward(request, response);
    }

}