<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report_id != null}">
                <h2>いいねした人</h2>
                <table id="likes_list">
                    <tbody>
                        <tr>
                            <th class="likes_name">氏名</th>
                            <th class="likes_date">いいねした日時</th>
                        </tr>
                        <c:forEach var="like" items="${likes}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="likes_name"><c:out value="${like.employee.name}" /></td>
                                <td class="likes_date"><fmt:formatDate
                                        value="${like.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${likes_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((likes_count - 1) / 10) + 1}"
                        step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a
                                    href="<c:url value='/likes?report_id=${reportUrl}&page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value="/reports" />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>