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
                    action="<c:url value='/leave/create' />">
                    <label for="leave_time">退勤時刻</label><br /> <input type="text"
                        name="leave_time" placeholder="HHmm" /> <br /> <br /> <input
                        type="hidden" name="_token" value="${_token}" />
                    <button class="ui primary button" type="submit">修正</button>
                </form>
            </div>
        </body>
    </c:param>
</c:import>