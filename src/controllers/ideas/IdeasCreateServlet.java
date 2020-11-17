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

@WebServlet("/ideas/create")
public class IdeasCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // トークンを取得
        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            // ログイン中メンバーのidを取得
            Member login_member = (Member) request.getSession().getAttribute("login_member");

            // ideaのレビュー経過を取得
            Integer review_flag_int = Integer.parseInt(request.getParameter("review_flag"));

            // 本日の日付を取得
            Date created_date = new Date(System.currentTimeMillis());
            // 本日の詳細時刻を取得
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            String cd_str = request.getParameter("created_date");
            if (cd_str != null && !cd_str.equals("")) {
                created_date = Date.valueOf(request.getParameter("created_date"));
            }

            Idea i = new Idea();

            i.setMember(login_member);
            i.setTitle(request.getParameter("title"));
            i.setContent(request.getParameter("content"));
            i.setFavors(0); // 初期化
            i.setReview_flag(review_flag_int);
            i.setCreated_date(created_date);
            i.setCreated_at(currentTime);
            i.setUpdated_at(currentTime);

            List<String> errors = IdeaValidator.validate(i);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("idea", i);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/new.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.persist(i);
                em.getTransaction().commit();
                em.close();

                if (review_flag_int == 0) { // 下書きとして保存した場合
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");

                } else if (review_flag_int == 2 && (login_member.getRole_flag() == 0 || login_member.getRole_flag() == 1)) { // associateまたはadministlatorがideaをpost
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を課長へ提出しました。");

                } else if (review_flag_int == 2 && login_member.getRole_flag() == 2) { // managerがideaを提出したpost
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を他課長へ提出しました。");

                } else if (review_flag_int == 4 && login_member.getRole_flag() == 3) { // directorがideaを提出したpost
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を他部長へ提出しました。");
                }

                response.sendRedirect(request.getContextPath() + "/ideas");
            }
        }
    }

}