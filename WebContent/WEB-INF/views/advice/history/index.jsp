<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>日報「<c:out value="${ideaTitle}"/>」の承認履歴一覧</h2>
        <c:choose>
            <c:when test="${getReviewsCnt == 0}">
                <h3>日報「<c:out value="${ideaTitle}"></c:out>」に承認履歴はありません。</h3>
                <p>日報が承認または差し戻しされるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>承認日時</th>
                        <th>承認状況</th>
                        <th>コメント</th>
                        <th>承認者</th>
                        <th>役職</th>
                    </tr>
                    <c:forEach var="review" items="${getReviews}">
                        <tr>
                            <td>
                                <fmt:formatDate value='${review.createdAt}' pattern='yyyy-MM-dd HH:mm'/>
                            </td>
                            <td>
                                <c:if test="${review.reviewStatus == 1 || review.reviewStatus == 3}">差し戻し</c:if>
                                <c:if test="${review.reviewStatus == 4 || review.reviewStatus == 6}">承認</c:if>
                            </td>
                            <td>
                                <c:out value="${review.advice}"/>
                            </td>
                            <td>
                                <c:out value="${review.member.name}"/>
                            </td>
                            <td>
                                <c:if test="${review.member.role == 2}">課長</c:if>
                                <c:if test="${review.member.role == 3}">部長</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">履歴件数<c:out value="${getReviewsCnt}"/>
                </div>
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getReviewsCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/advice/history?page=${i}' />">
                                    <c:out value="${i}"/>
                                </a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/ideas/show?id=${ideaId}' />">日報詳細ページに戻る</a>
        </p>
    </c:param>
</c:import>