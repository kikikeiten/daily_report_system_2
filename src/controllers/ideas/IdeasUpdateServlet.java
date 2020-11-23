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

            // 更新するアイデアのIDを取得
            Idea idea = em.find(Idea.class, (Integer) (request.getSession().getAttribute("ideaId")));

            // 更新後のパラメータを取得
            Integer reviewStatus = Integer.parseInt(request.getParameter("reviewStatus"));

            // ログイン中メンバーのIDを取得
            Member loginMember = (Member) request.getSession().getAttribute("loginMember");

            // Ideaテーブルに値をセット
            idea.setTitle(request.getParameter("title"));
            idea.setContent(request.getParameter("content"));
            idea.setReviewStatus(reviewStatus);
            idea.setCrearedDate(Date.valueOf(request.getParameter("createdDate")));
            idea.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            List<String> errors = IdeaValidator.validate(idea);

            // エラーがある場合
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("idea", idea);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/edit.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                // アソシエイトと管理者の場合
                if (loginMember.getRole() == 0 || loginMember.getRole() == 1) {

                    switch (reviewStatus) {
                        case 0: // ドラフトを再保存
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                            break;
                        case 2: // ドラフトまたはマネージャーからのアドバイス付きアイデアを提出
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を課長に提出しました。");
                            break;
                        case 4: // ディレクターからのアドバイス付きアイデアを提出
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                            break;
                    }
                }

                // マネージャーの場合
                if (loginMember.getRole() == 2) {

                    switch (reviewStatus) {
                        case 0: // ドラフトを再保存
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                            break;
                        case 2: // ドラフトを提出
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を他課長に提出しました。");
                            break;
                        case 4: // ディレクターからのアドバイス付きアイデアを提出
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を部長に再提出しました。");
                            break;
                    }
                }

                // ディレクターの場合
                if (loginMember.getRole() == 3) {

                    switch (reviewStatus) {
                        case 0: // ドラフトを再保存
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を下書きとして保存しました。");
                            break;
                        case 4: // ドラフトを提出
                            request.getSession().setAttribute("toast",
                                    "日報「" + request.getParameter("title") + "」を他部長に提出しました。");
                            break;
                    }
                }

                request.getSession().removeAttribute("ideaId");

                response.sendRedirect(request.getContextPath() + "/ideas");
            }
        }
    }

}