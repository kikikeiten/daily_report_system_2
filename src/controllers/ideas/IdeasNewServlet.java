package controllers.ideas;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Idea;

@WebServlet("/ideas/new")
public class IdeasNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IdeasNewServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("_token", request.getSession().getId());

        Idea i = new Idea();

        i.setCreated_date(new Date(System.currentTimeMillis()));

        request.setAttribute("idea", i);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ideas/new.jsp");
        rd.forward(request, response);
    }

}