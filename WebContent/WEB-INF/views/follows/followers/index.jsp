<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>フォロワー</h2>
        <div class="ui raised very padded container segment">
        <c:choose>
            <c:when test="${getMyFollowerCnt == 0}">
                <h3><c:out value="${sessionScope.loginMember.name}"/>さんにはまだフォロワーがいません。</h3>
                <p>あなたがフォローされるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>氏名</th>
                        <th>フォロー</th>
                        <th>日付</th>
                    </tr>
                    <c:forEach var="member" items="${getMyFollower}">
                        <tr>
                            <td>
                                <c:out value="${member.followingId.name}"/>
                            </td>
                            <c:choose>
                                <c:when test="${!fn:contains(followIdea,member.followingId.id)}">
                                    <td>
                                        <form method="POST" action="<c:url value='/followers/create' />">
                                            <button class="ui tiny active button" type="submit" name="followerId" value="${member.id}">
                                                <i class="user icon"></i>フォロー
                                            </button>
                                        </form>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <form method="POST" action="<c:url value='/followers/destroy' />">
                                            <button class="ui tiny animated button" type="submit" name="followerId" value="${member.id}">
                                                <div class="visible content">
                                                    <i class="user icon"></i>フォロー中
                                                </div>
                                                <div class="hidden content">
                                                    <i class="user icon"></i>フォロー解除
                                                </div>
                                            </button>
                                        </form>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <fmt:formatDate value='${member.createdAt}' pattern='yyyy-MM-dd HH:mm'/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">フォロワー<c:out value="${getMyFollowerCnt}"/>
                </div>&nbsp;
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getMyFollowerCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/followers?page=${i}' />">
                                    <c:out value="${i}"/>
                                </a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        </div>
        <p>
            <a href="<c:url value='/' />">トップページへ戻る</a>
        </p>
    </c:param>
</c:import>