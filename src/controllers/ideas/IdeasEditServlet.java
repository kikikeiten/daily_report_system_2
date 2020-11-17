package controllers.ideas;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import models.Idea;
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

        // ideas/edit.jspからideaのidを取得
        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("idea_id")));

        Integer review_flag_int = i.getReview_flag();

        em.close();

        // ログイン中メンバーのidを取得
        Member login_member = (Member) request.getSession().getAttribute("login_member");

        if (i != null && login_member.getId() == i.getMember().getId()) {
            request.setAttribute("idea", i);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("idea_id", i.getId());
            request.setAttribute("review_flag_int", review_flag_int);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/edit.jsp");
        rd.forward(request, response);
    }

}