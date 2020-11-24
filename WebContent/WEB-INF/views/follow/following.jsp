<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>
            フォロー中
        </h2>
        <c:choose>
            <c:when test="${getMyFollowingCnt == 0}">
                <h3>
                    <c:out value="${sessionScope.loginMember.name}"/>
                    さんはまだ誰もフォローしていません。
                </h3>
                <p>
                    作成されるとここに表示されます。
                </p>
            </c:when>
            <c:otherwise>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>
                            氏名
                        </th>
                        <th>
                            フォロー
                        </th>
                        <th>
                            日付
                        </th>
                    </tr>
                    <c:forEach var="member" items="${getMyFollowing}">
                        <tr>
                            <td>
                                <c:out value="${member.follow.name}"/>
                            </td>
                            <td>
                                <form method="POST" action="<c:url value='/following/destroy'/>">
                                    <button class="ui tiny animated button" type="submit" name="followedId" value="${member.id}">
                                        <div class="visible content">
                                            <i class="user icon"></i>
                                            フォロー中
                                        </div>
                                        <div class="hidden content">
                                            <i class="user icon"></i>
                                            フォロー解除
                                        </div>
                                    </button>
                                </form>
                            </td>
                            <td>
                                <fmt:formatDate value='${member.createdAt}' pattern='yyyy-MM-dd HH:mm'/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">
                    フォロー中
                    <c:out value="${getMyFollowingCnt}"/>
                </div>
                &nbsp;
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getMyFollowingCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/following?page=${i}' />">
                                    <c:out value="${i}"/>
                                </a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/' />">トップページへ戻る</a>
        </p>
    </c:param>
</c:import>