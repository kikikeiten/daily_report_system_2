<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>フォロワー</h2>
        <c:choose>
            <c:when test="${getMyFollowerCount == 0}">
                <h3>
                    <c:out value="${sessionScope.login_employee.name}" />
                    さんにはまだフォロワーがいません。
                </h3>
                <p>あなたがフォローされるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="follower_list">
                    <tbody>
                        <tr>
                            <th class="follower_name">氏名</th>
                            <th class="follow">フォロー</th>
                            <th class="follower_date">日付</th>
                        </tr>
                        <c:forEach var="follower" items="${getMyAllFollower}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="report_name"><c:out
                                        value="${follower.employee.name}" /></td>
                                <c:choose>
                                    <c:when test="${!follow_count}">
                                        <td class="follow">
                                            <form method="POST"
                                                action="<c:url value='/follower/create' />">
                                                <button type="submit" name="follow_id"
                                                    value="${follower.id}">フォロー</button>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="follow">
                                            <form method="POST"
                                                action="<c:url value='/follower/destroy' />">
                                                <button type="submit" name="employee_id"
                                                    value="${follower.id}"
                                                    onmouseover="this.innerText='フォロー解除'"
                                                    onmouseout="this.innerText='フォロー中'">フォロー中</button>
                                            </form>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="report_date"><fmt:formatDate
                                        value='${follower.created_at}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getMyFollowerCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getMyFollowerCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                    </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/follower/index?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                    </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/index.html' />">トップページへ戻る</a>
        </p>

    </c:param>
</c:import>