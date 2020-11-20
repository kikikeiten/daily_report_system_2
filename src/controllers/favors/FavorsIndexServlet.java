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

        Idea i = em.find(Idea.class, Integer.parseInt(request.getParameter("idea_id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // 全てのideaに付いたfavorを取得
        List<Favor> getFavors = em.createNamedQuery("getFavors", Favor.class)
                .setParameter("idea", i)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        // 全てのideaに付いたfavor総数を取得
        long getFavorsCnt = (long) em.createNamedQuery("getFavorsCnt", Long.class)
                .setParameter("idea", i)
                .getSingleResult();

        // URLに付与するパラメータを送信
        Integer url_int = Integer.parseInt(request.getParameter("idea_id"));

        em.close();

        request.setAttribute("idea_id", i);
        request.setAttribute("page", page);
        request.setAttribute("getFavors", getFavors);
        request.setAttribute("getFavorsCnt", getFavorsCnt);
        request.setAttribute("url_int", url_int);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/favors/index.jsp");
        rd.forward(request, response);
    }

}
