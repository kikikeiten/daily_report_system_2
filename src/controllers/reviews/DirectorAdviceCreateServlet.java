package controllers.reviews;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;
import utils.DBUtil;

@WebServlet("/advice/director/create")
public class DirectorAdviceCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DirectorAdviceCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // アドバイスされたアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        // アドバイスされたアイデアのレビュー状態をIdeaテーブルで更新
        idea.setReviewStatus(Integer.parseInt(request.getParameter("reviewStatus")));

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        // トーストメッセージをセッションにセット
        request.getSession().setAttribute("toast", "日報「" + idea.getTitle() + "」を部長に再提出しました。");

        response.sendRedirect(request.getContextPath() + "/advice/director");
    }

}