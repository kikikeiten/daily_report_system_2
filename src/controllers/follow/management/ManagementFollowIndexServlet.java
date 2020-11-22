package controllers.follow.management;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Member;
import utils.DBUtil;

@WebServlet("/management/follow")
public class ManagementFollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagementFollowIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member member = em.find(Member.class, Integer.parseInt(request.getParameter("member_id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Member> getMemberNotFollowing = em.createNamedQuery("getMemberNotFollowing", Member.class)
                .setParameter("member", member)
                .setFirstResult(12 * (page - 1))
                .setMaxResults(12)
                .getResultList();

        long getMemberNotFollowingCnt = (long) em.createNamedQuery("getMemberNotFollowingCnt", Long.class)
                .setParameter("member", member)
                .getSingleResult();

        try {
            String member_name_str = member.getName();
            Integer member_operated_int = member.getId();

            request.setAttribute("member_name_str", member_name_str);
            request.setAttribute("member_operated_int", member_operated_int);

        } catch (Exception e) {
        }

        Integer member_id_int = Integer.parseInt(request.getParameter("member_id"));

        em.close();

        request.setAttribute("page", page);
        request.setAttribute("member", member);
        request.setAttribute("getMemberNotFollowing", getMemberNotFollowing);
        request.setAttribute("getMemberNotFollowingCnt", getMemberNotFollowingCnt);
        request.setAttribute("member_id_int", member_id_int);

        if (request.getSession().getAttribute("toast") != null) {
            request.setAttribute("toast", request.getSession().getAttribute("toast"));
            request.getSession().removeAttribute("toast");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/management/follow.jsp");
        rd.forward(request, response);
    }

}