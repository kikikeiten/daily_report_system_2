<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <body>
            <div class="ui raised very padded text container segment">
                <h2>Correction of leaving time</h2>
                <div class="ui image label">
                    Name
                    <div class="detail">
                        <c:out value="${join.member.name}" />
                    </div>
                </div>
                <div class="ui image label">
                    Date
                    <div class="detail">
                        <c:out value="${join.joinDate}" />
                    </div>
                </div>
                <div class="ui image label">
                    Joined
                    <div class="detail">
                        <c:out value="${join.punchIn}" />
                    </div>
                </div>
                <div class="ui hidden divider"></div>
                <form class="ui fluid form" method="POST" action="<c:url value='/joins/all/punch-out/update' />">
                    <div class="fields">
                        <div class="four wide field">
                            <label for="punchOutHour">Hour</label> <input type="text" name="punchOutHour" placeholder="00" />
                        </div>
                        <div class="four wide field">
                            <label for="punchOutMinute">Minute</label> <input type="text" name="punchOutMinute" placeholder="00" />
                        </div>
                    </div>
                    <button type="button" onclick="location.href='<c:url value='/joins/all'/>'" class="circular ui icon button">
                        <i class="fas fa-long-arrow-alt-left"></i>
                    </button>
                    <input type="hidden" name="id" value="${join.id}" />
                    <button class="ui circular positive button" type="submit"><i class="fas fa-wrench"></i></button>
                </form>
            </div>
        </body>
    </c:param>
</c:import>