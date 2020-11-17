package controllers.ideas;

import java.io.IOException;
import java.sql.Date;
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
import models.Idea;
import models.validators.IdeaValidator;
import utils.DBUtil;

@WebServlet("/ideas/update")
public class IdeasUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Idea i = em.find(Idea.class, (Integer) (request.getSession().getAttribute("idea_id")));

            Integer review_flag_int = Integer.parseInt(request.getParameter("review_flag"));

            Member login_member = (Member) request.getSession().getAttribute("login_member");

            i.setTitle(request.getParameter("title"));
            i.setContent(request.getParameter("content"));
            i.setReview_flag(review_flag_int);
            i.setCreared_date(Date.valueOf(request.getParameter("created_date")));
            i.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = IdeaValidator.validate(i);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("idea", i);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/edit.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                if (login_member.getRole_flag() == 0 || login_member.getRole_flag() == 1) {
                    switch (review_flag_int) {
                    case 0:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 2:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を課長に提出しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                        break;
                    }
                }

                if (login_member.getRole_flag() == 2) {
                    switch (review_flag_int) {
                    case 0:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 2:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を他課長に提出しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                        break;
                    }
                }

                if (login_member.getRole_flag() == 3) {
                    switch (review_flag_int) {
                    case 0:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                        break;
                    case 4:
                        request.getSession().setAttribute("toast",
                                "日報「" + request.getParameter("title") + "」を他部長に提出しました。");
                        break;
                    }
                }

                request.getSession().removeAttribute("idea_id");

                response.sendRedirect(request.getContextPath() + "/ideas");
            }
        }
    }

}