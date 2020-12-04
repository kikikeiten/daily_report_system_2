<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${sessionScope.loginMember.role != 0}">
                <h2>All stamps</h2>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getJoinsCnt == 0}">
                            <c:import url="_noJoin.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <table class="ui padded celled striped table">
                                <tbody>
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Date</th>
                                    <th>Joined time</th>
                                    <th>Leaved time</th>
                                    <th>Duration</th>
                                </tr>
                                </thead>
                                <c:forEach var="join" items="${getJoins}">
                                    <tr>
                                        <td><c:out value="${join.member.name}"/>&nbsp;&nbsp; <c:if test="${join.joinStatus == 1}">
                                            <div class="ui green label">Joining now</div>
                                        </c:if> <c:if test="${join.joinStatus == 2}">
                                            <a href="<c:url value='/joins/all/punch-out/edit?id=${join.id}'/>" class="ui red label">Forgot</a>
                                        </c:if> <c:if test="${join.joinStatus == 3}">
                                            <div class="ui orange label">Fixed</div>
                                        </c:if></td>
                                        <td><fmt:formatDate value='${join.joinDate}' pattern='yyyy / MM / dd'/></td>

                                        <td><fmt:formatDate value='${join.punchIn}' pattern='HH : mm'/></td>
                                        <c:choose>
                                            <c:when test="${join.joinStatus == 2}">
                                                <td class="negative">Unregistered</td>
                                            </c:when>
                                            <c:when test="${join.joinStatus == 3}">
                                                <td class="warning"><fmt:formatDate value='${join.punchOut}' pattern='HH : mm'/></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><fmt:formatDate value='${join.punchOut}' pattern='HH : mm'/> <c:if test="${join.joinStatus == 2}">Unregistered</c:if></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${join.joinStatus == 2}">
                                                <td class="negative">Unregistered</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><fmt:formatDate value='${join.workingTime}' pattern='HH : mm'/> <c:if test="${join.joinStatus == 2}">Unregistered</c:if></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="ui hidden divider"></div>
                            <c:import url="_pages.jsp"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:import url="_labels.jsp"/>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
                <button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
                    <i class="fas fa-long-arrow-alt-left"></i>
                </button>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>