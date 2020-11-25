<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${toast != null}">
            <script>
                $('body')
                    .toast({
                        class: 'success',
                        message: "${toast}",
                        position: 'bottom right',
                        showProgress: 'top',
                        progressUp: true,
                        className: {
                            toast: 'ui message'
                        }
                    });
            </script>
        </c:if>
        <c:choose>
            <c:when test="${member != null}">
                <h2><c:out value="${memberName}"/>さんにフォローさせる</h2>
                <c:choose>
                    <c:when test="${getMemberNotFollowingCnt == 0}">
                        <h3><c:out value="${memberName}"/>さんは全ての従業員をフォローしています。</h3>
                    </c:when>
                    <c:otherwise>
                        <table class="ui celled striped table">
                            <tbody>
                            <tr>
                                <th>氏名</th>
                                <th>フォロー</th>
                            </tr>
                            <c:forEach var="member" items="${getMemberNotFollowing}">
                                <tr>
                                    <td>
                                        <c:out value="${member.name}"/>
                                    </td>
                                    <td>
                                        <form method="POST" action="<c:url value='/management/unfollowing/create' />">
                                            <button class="ui tiny active button" type="submit" name="followedId" value="${member.id}">
                                                <i class="user icon"></i>フォロー
                                            </button>
                                            <input type="hidden" name="followingId" value="${member.id}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="ui label">未フォロー
                            <c:out value="${getMemberNotFollowingCnt}"/>
                        </div>
                        <div class="ui mini pagination menu">
                            <c:forEach var="i" begin="1" end="${((getMemberNotFollowingCnt - 1) / 12) + 1}" step="1">
                                <c:choose>
                                    <c:when test="${i == page}">
                                        <div class="item active">
                                            <c:out value="${i}"/>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="item" href="<c:url value='/management/unfollowing?id=${page_number}&page=${i}'/>">
                                            <c:out value="${i}"/></a>&nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/members' />">従業員一覧へ戻る</a>
        </p>
    </c:param>
</c:import>