package controllers.follows.following;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import utils.DBUtil;

@WebServlet("/following/destroy/index")
public class FollowingDestroyViaIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowingDestroyViaIndexServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // フォロー解除するメンバーのIDを取得
        Follow followed = em.find(Follow.class, Integer.parseInt(request.getParameter("followedId")));

        em.getTransaction().begin();
        em.remove(followed);
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", followed.getFollowedId().getName() + "さんのフォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/following");
    }

}