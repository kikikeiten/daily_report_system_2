package controllers.reviews;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Review;
import models.Member;
import models.Idea;
import utils.DBUtil;

@WebServlet("/manager/approval/create")
public class ManagerReviewsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManagerReviewsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Member e = (Member) request.getSession().getAttribute("login_employee");
        Idea r = em.find(Idea.class, Integer.parseInt(request.getParameter("report_id")));
        Integer submit = Integer.parseInt(request.getParameter("submit"));

        r.setApproval(Integer.parseInt(request.getParameter("submit")));

        Review a = new Review();

        a.setReport(r);
        a.setEmployee(e);
        a.setApproval(submit);
        a.setComment(request.getParameter("comment"));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();

        Member report_employee = r.getEmployee();
        Integer admin_flag = report_employee.getAdmin_flag();
        String report_name = report_employee.getName();

        if (submit == 1) {
            switch (admin_flag) {
            case 0:
                request.getSession().setAttribute("flush",
                        "日報「" + r.getTitle() + "」を" + report_name + "社員に差し戻しました。");
                break;
            case 1:
                request.getSession().setAttribute("flush",
                        "日報「" + r.getTitle() + "」を" + report_name + "管理者に差し戻しました。");
                break;
            }
        } else {
            switch (admin_flag) {
            case 0:
                request.getSession().setAttribute("flush",
                        report_name + "社員の日報「" + r.getTitle() + "」を承認しました。");
                break;
            case 1:
                request.getSession().setAttribute("flush",
                        report_name + "管理者の日報「" + r.getTitle() + "」を承認しました。");
                break;
            case 2:
                request.getSession().setAttribute("flush",
                        report_name + "課長の日報「" + r.getTitle() + "」を承認しました。");
                break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/approval/manager");
    }

}