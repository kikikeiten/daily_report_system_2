<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>My stamps</h2>
        <div class="ui raised very padded container segment">
        <c:choose>
            <c:when test="${getMyJoinCnt == 0}">
                <h3>打刻はまだありません。</h3>
                <p>打刻するとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table class="ui padded celled striped table">
                    <tbody>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Joined time</th>
                        <th>Leaved time</th>
                        <th>Duration</th>
                    </tr>
                    </thead>
                    <c:forEach var="join" items="${getMyJoins}">
                        <tr>
                            <td>
                                <fmt:formatDate value='${join.joinDate}' pattern='yyyy / MM / dd'/>&nbsp;&nbsp;
                                <c:if test="${join.joinStatus == 1}">
                                    <div class="ui green label">Joining now</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 2}">
                                    <div class="ui red label">Forgot</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 3}">
                                    <div class="ui orange label">Fixed</div>
                                </c:if>
                            </td>
                            <td><fmt:formatDate value='${join.punchIn}' pattern='HH : mm'/></td>
                            <c:choose>
                                    <c:when test="${join.joinStatus == 2}">
                                        <td class="negative">Unregistered</td>
                                    </c:when>
                                    <c:when test="${join.joinStatus == 3}">
                                        <td class="warning"><fmt:formatDate value='${join.punchOut}' pattern='HH : mm' /></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><fmt:formatDate value='${join.punchOut}' pattern='HH : mm' /> <c:if test="${join.joinStatus == 2}">Unregistered</c:if></td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${join.joinStatus == 2}">
                                        <td class="negative">Unregistered</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><fmt:formatDate value='${join.workingTime}' pattern='HH : mm' /> <c:if test="${join.joinStatus == 2}">Unregistered</c:if></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui hidden divider"></div>
                <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getJoinsCnt - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/joins/all?page=${i}' />"> <c:out value="${i}" />
                                    </a>&nbsp;
                            </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
            </c:otherwise>
        </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>