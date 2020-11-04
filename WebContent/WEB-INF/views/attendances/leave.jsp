<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <body>
            <br />
            <br />
            <div class="ui raised very padded text container segment">
                <h2>退勤時刻の修正</h2>
                <form class="ui fluid form" method="POST"
                    action="<c:url value='/leave/update' />">
                    <div class="fields">
                    <label for="leave_hour"></label><br />
                    <div class="two wide field">
                    <input type="text"
                        name="punch_hour" placeholder="時" /></div>
                    <label for="leave_time"></label><br />
                    <div class="two wide field">
                    <input type="text"
                        name="punch_minute" placeholder="分" /></div>

                        </div>
                        <br /> <input
                        type="hidden" name="id" value="${attendance_id}" />
                    <button class="ui primary button" type="submit">修正</button>
                </form>
            </div>
        </body>
    </c:param>
</c:import>