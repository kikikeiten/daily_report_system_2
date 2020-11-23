package controllers.joins;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Join;
import utils.DBUtil;

@WebServlet("/punchout/edit")
public class PunchOutEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PunchOutEditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        // 変更対象のジョインIDを取得
        Join join = em.find(Join.class, Integer.parseInt(request.getParameter("joinId")));

        request.setAttribute("join", join);

        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/joins/punchout.jsp");
        rd.forward(request, response);
    }

}
