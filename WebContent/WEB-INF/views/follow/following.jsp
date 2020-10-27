<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>フォロー中</h2>
        <c:choose>
            <c:when test="${getMyFollowingCount == 0}">
                <h3>
                    <c:out value="${sessionScope.login_employee.name}" />
                    さんはまだ誰もフォローしていません。
                </h3>
                <p>作成されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="following_list" class="ui celled striped table">
                    <tbody>
                        <tr>
                            <th class="following_name">氏名</th>
                            <th class="follow">フォロー</th>
                            <th class="following_date">日付</th>
                        </tr>
                        <c:forEach var="following" items="${getMyAllFollowing}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="following_name"><c:out
                                        value="${following.follow.name}" /></td>
                                <td class="follow">
                                    <form method="POST"
                                        action="<c:url value='/following/destroy' />">
                                        <button class="ui tiny animated button" type="submit"
                                            name="follow_id" value="${following.id}">
                                            <div class="visible content">
                                                <i class="user icon"></i>フォロー中
                                            </div>
                                            <div class="hidden content">
                                                <i class="user icon"></i>フォロー解除
                                            </div>
                                        </button>
                                    </form>
                                </td>
                                <td class="following_date"><fmt:formatDate
                                        value='${following.created_at}' pattern='yyyy-MM-dd HH:mm' /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getMyFollowingCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getMyFollowingCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                    </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/following?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                    </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <br>
        <p>
            <a href="<c:url value='/' />">トップページへ戻る</a>
        </p>
    </c:param>
</c:import>