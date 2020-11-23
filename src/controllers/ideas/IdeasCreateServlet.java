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

        String _token = (String) request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            // ログイン中メンバーのIDを取得
            Member loginMember = (Member) request.getSession().getAttribute("loginMember");

            // アイデアのレビュー状態を取得
            Integer reviewStatus = Integer.parseInt(request.getParameter("reviewStatus"));

            Date date = new Date(System.currentTimeMillis());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // アイデアの作成日を取得
            String createdDate = request.getParameter("createdDate");

            // 作成日のインプットスペースに作成日を予め入れておく
            if (createdDate != null && !createdDate.equals("")) {
                date = Date.valueOf(request.getParameter("createdDate"));
            }

            Idea idea = new Idea();

            // Ideaテーブルに値をセット
            idea.setMember(loginMember);
            idea.setTitle(request.getParameter("title"));
            idea.setContent(request.getParameter("content"));
            idea.setFavors(0); // 初期化
            idea.setReviewStatus(reviewStatus);
            idea.setCreatedDate(date);
            idea.setCreatedAt(timestamp);
            idea.setUpdatedAt(timestamp);

            List<String> errors = IdeaValidator.validate(idea);

            // エラーがある場合
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("idea", idea);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/new.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.persist(idea);
                em.getTransaction().commit();
                em.close();

                if (reviewStatus == 0) { // ドラフトとして保存した場合
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");

                } else if (reviewStatus == 2 && (loginMember.getRole() == 0 || loginMember.getRole() == 1)) { // アソシエイトまたは管理者
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を課長へ提出しました。");

                } else if (reviewStatus == 2 && loginMember.getRole() == 2) { // マネージャー
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を他課長へ提出しました。");

                } else if (reviewStatus == 4 && loginMember.getRole() == 3) { // ディレクター
                    request.getSession().setAttribute("toast", "日報「" + request.getParameter("title") + "」を他部長へ提出しました。");
                }

                response.sendRedirect(request.getContextPath() + "/ideas");
            }
        }
    }

}