<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>My打刻履歴</h2>
        <div class="ui raised very padded container segment">
        <c:choose>
            <c:when test="${getMyJoinCnt == 0}">
                <h3>打刻はまだありません。</h3>
                <p>打刻するとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>日付</th>
                        <th>出勤時刻</th>
                        <th>退勤時刻</th>
                        <th>勤務時間</th>
                    </tr>
                    <c:forEach var="join" items="${getMyJoins}">
                        <tr>
                            <td>
                                <fmt:formatDate value='${join.joinDate}' pattern='MM / dd'/>
                                <c:if test="${join.joinStatus == 1}">
                                    <div class="ui green label">勤務中</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 2}">
                                    <div class="ui red label">打刻忘れ</div>
                                </c:if>
                                <c:if test="${join.joinStatus == 3}">
                                    <div class="ui orange label">修正済み</div>
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
        <p>
            <a href="<c:url value='/' />">トップページに戻る</a>
        </p>
    </c:param>
</c:import>