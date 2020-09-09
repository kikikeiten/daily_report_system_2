package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class LikesCreateServlet
 */
@WebServlet("/likes/create")
public class LikesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        // CSRF対策のチェック
        //String _token = (String) request.getParameter("_token");
        //if (_token != null && _token.equals(request.getSession().getId())) {

        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

        // 確認
        //System.out.println("セッション中のreport_id : " + request.getSession().getAttribute("report_id"));

        // リクエストパラメータからレポートのIDを取得して該当のIDのレポート1件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));

        // いいねをプロパティにvalue(1)を上書き
        r.setLikes(Integer.parseInt(request.getParameter("likes")) + r.getLikes());

        // データベースを更新
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "いいねしました。");

        // セッションスコープ上の不要になったデータを削除
        //request.getSession().removeAttribute("report_id");

        // indexページへリダイレクト
        response.sendRedirect(request.getContextPath() + "/reports/index");

    }
}
