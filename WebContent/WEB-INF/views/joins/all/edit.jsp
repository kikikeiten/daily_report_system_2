<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <body>
        <div class="ui raised very padded text container segment">
            <h2>退勤時刻の修正</h2>
            <div class="ui image label">従業員名
                <div class="detail">
                    <c:out value="${join.member.name}"/>
                </div>
            </div>
            <div class="ui image label">出勤日
                <div class="detail">
                    <c:out value="${join.join_date}"/>
                </div>
            </div>
            <div class="ui image label">出勤時刻
                <div class="detail">
                    <c:out value="${join.punch_in}"/>
                </div>
            </div>
            <form class="ui fluid form" method="POST" action="<c:url value='joins/all/punch-out/update' />">
                <div class="fields">
                    <div class="four wide field">
                        <label for="punchOutHour">時</label>
                        <input type="text" name="punchOutHour" placeholder="00"/>
                    </div>
                    <div class="four wide field">
                        <label for="punchOutMinute">分</label>
                        <input type="text" name="punchOutMinute" placeholder="00"/>
                    </div>
                </div>
                <input type="hidden" name="id" value="${join.id}"/>
                <button class="ui positive button" type="submit">修正</button>
            </form>
        </div>
        </body>
    </c:param>
</c:import>