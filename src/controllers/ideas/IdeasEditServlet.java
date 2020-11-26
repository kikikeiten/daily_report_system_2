package controllers.ideas;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;
import models.Member;
import utils.DBUtil;

@WebServlet("/ideas/edit")
public class IdeasEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasEditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 編集するアイデアのIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        Integer reviewFlag = idea.getReviewStatus();

        em.close();

        // ログイン中メンバーのIDを取得
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        // 編集するアイデアが存在かつログイン中メンバーとアイデアの作者が同じ場合
        if (idea != null && loginMember.getId() == idea.getMember().getId()) {
            request.setAttribute("idea", idea);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("ideaId", idea.getId());
            request.setAttribute("reviewFlag", reviewFlag);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/edit.jsp");
        rd.forward(request, response);
    }

}