package controllers.favors;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favor;
import models.Idea;
import utils.DBUtil;

@WebServlet("/favors")
public class FavorsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FavorsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 賛成一覧の対象アイデアIDを取得
        Idea idea = em.find(Idea.class, Integer.parseInt(request.getParameter("id")));

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // アイデアに付いた全ての賛成を取得
        List<Favor> getFavors = em.createNamedQuery("getFavors", Favor.class)
                .setParameter("idea", idea)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // アイデアに付いた賛成総数を取得
        long getFavorsCnt = (long) em.createNamedQuery("getFavorsCnt", Long.class)
                .setParameter("idea", idea)
                .getSingleResult();

        // URLに付与するアイデアIDを取得
        Integer ideaId = Integer.parseInt(request.getParameter("ideaId"));

        em.close();

        request.setAttribute("idea", idea);
        request.setAttribute("page", page);
        request.setAttribute("getFavors", getFavors);
        request.setAttribute("getFavorsCnt", getFavorsCnt);
        request.setAttribute("ideaId", ideaId);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/favors/index.jsp");
        rd.forward(request, response);
    }

}
