package controllers.ideas;

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

import models.Review;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/ideas/show")
public class IdeasShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("idea_id")));

        Member login_member = (Member) request.getSession().getAttribute("login_member");

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

        try {
            Review getReviews = em.createNamedQuery("getReviews", Review.class)
                    .setParameter("idea", i)
                    .setMaxResults(1)
                    .getSingleResult();

            String advice_str = getReviews.getAdvice();

            Member m = getReviews.getMember();

            String name_str = m.getName();
            Integer role_flag_int = m.getRole_flag();

            request.setAttribute("advice_str", advice_str);
            request.setAttribute("name_str", name_str);

            switch (role_flag_int) {
                case 2:
                    request.setAttribute("role", "Manager");
                    break;
                case 3:
                    request.setAttribute("role", "Director");
                    break;
            }

            Integer review_flag_int = getReviews.getReview_flag();

            if (review_flag_int == 1 || review_flag_int == 3) {
                request.setAttribute("approval_status", "差し戻し");

            } else if (review_flag_int == 4 || review_flag_int == 6) {
                request.setAttribute("approval_status", "承認");

            }
        } catch (Exception e) {
        }

        Integer review_flag_int = getReviews.getReview_flag();

        em.close();

        request.setAttribute("idea", i);
        request.setAttribute("review_flag_int", review_flag_int);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/show.jsp");
        rd.forward(request, response);
    }

}