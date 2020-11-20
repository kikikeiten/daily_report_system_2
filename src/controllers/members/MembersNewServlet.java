package controllers.members;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;

@WebServlet("/members/new")
public class MembersNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MembersNewServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("_token", request.getSession().getId());

        request.setAttribute("member", new Member());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/new.jsp");
        rd.forward(request, response);
    }

}