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
                <table class="ui celled striped table">
                    <tbody>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Joining time</th>
                        <th>Leaving time</th>
                        <th>Duration</th>
                    </tr>
                    </thead>
                    <c:forEach var="join" items="${getMyJoins}">
                        <tr>
                            <td>
                                <fmt:formatDate value='${join.joinDate}' pattern='yyyy / MM / dd'/>&nbsp;&nbsp;
                                <c:if test="${join.joinStatus == 1}">
                                    <div class="ui green label">joining now</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 2}">
                                    <div class="ui red label">forget</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 3}">
                                    <div class="ui orange label">fixed</div>
                                </c:if>
                            </td>
                            <td><fmt:formatDate value='${join.punchIn}' pattern='HH : mm'/></td>
                            <td><fmt:formatDate value='${join.punchOut}' pattern='HH : mm'/>
                                <c:if test="${join.joinStatus == 2}">未登録</c:if>
                            </td>
                            <td><fmt:formatDate value='${join.workingTime}' pattern='HH : mm'/>
                                <c:if test="${join.joinStatus == 2}">未登録</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">履歴数${getMyJoinCnt}</div>
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getMyJoinCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/joins/my?page=${i}' />">
                                    <c:out value="${i}"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        </div>
    </c:param>
</c:import>