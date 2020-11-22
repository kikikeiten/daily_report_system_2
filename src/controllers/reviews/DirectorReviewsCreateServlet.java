package controllers.reviews;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/reviews/director/create")
public class DirectorReviewsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DirectorReviewsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("ideaId")));

        Integer reviewFlag = Integer.parseInt(request.getParameter("reviewFlag"));

        idea.setReview_flag(reviewFlag);

        Review review = new Review();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        review.setIdea(idea);
        review.setMember(loginMember);
        review.setReview_flag(reviewFlag);
        review.setAdvice(request.getParameter("advice"));
        review.setCreated_at(timestamp);
        review.setUpdated_at(timestamp);

        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        Member ideaMember = idea.getMember();
        Integer roleFlag = ideaMember.getRole_flag();
        String ideaMemberName = ideaMember.getName();

        if (reviewFlag == 3) {
            switch (roleFlag) {
                case 0:
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "社員に差し戻しました。");
                    break;
                case 1:
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "管理者に差し戻しました。");
                    break;
                case 2:
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "課長に差し戻しました。");
                    break;
                case 3:
                    request.getSession().setAttribute("toast",
                            "日報「" + idea.getTitle() + "」を" + ideaMemberName + "部長に差し戻しました。");
                    break;
            }
        } else {
            switch (roleFlag) {
                case 0:
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "社員の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 1:
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "管理者の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 2:
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "課長の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
                case 3:
                    request.getSession().setAttribute("toast",
                            ideaMemberName + "部長の日報「" + idea.getTitle() + "」を承認しました。");
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/reviews/director");
    }

}