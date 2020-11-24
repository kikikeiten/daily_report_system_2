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
                <div class="ui image label">
                        従業員名
                        <div class="detail"><c:out value="${attendance.employee.name}"></c:out></div>
                    </div>
                    <div class="ui image label">
                        出勤日
                        <div class="detail"><c:out value="${attendance.attendance_date}"></c:out></div>
                    </div>
                    <div class="ui image label">
                        出勤時刻
                        <div class="detail"><c:out value="${attendance.punch_in}"></c:out></div>
                    </div>
                    <br>
                    <br>
                <form class="ui fluid form" method="POST"
                    action="<c:url value='/leave/update' />">
                    <div class="fields">
                        <div class="four wide field">
                            <label for="leave_hour">時</label> <input type="text"
                                name="leave_hour" placeholder="00" />
                        </div>
                        <div class="four wide field">
                            <label for="leave_time">分</label><input type="text"
                                name="leave_minute" placeholder="00" />
                        </div>
                    </div>
                    <br /> <input type="hidden" name="id" value="${attendance.id}" />
                    <button class="ui positive button" type="submit">修正</button>
                </form>
            </div>
        </body>
    </c:param>
</c:import>