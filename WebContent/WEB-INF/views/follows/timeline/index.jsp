<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>タイムライン</h2>
        <div class="ui raised very padded container segment">
        <c:choose>
            <c:when test="${getMyFollowingIdeasCnt == 0}">
                <h3><c:out value="${sessionScope.loginMember.name}"/>さんはまだ誰もフォローしていません。</h3>
                <p>作成されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>氏名</th>
                        <th>フォロー</th>
                        <th>日付</th>
                        <th>タイトル</th>
                        <th>操作</th>
                        <th>いいね数</th>
                    </tr>
                    <c:forEach var="idea" items="${getMyFollowingIdeas}">
                        <tr>
                            <td>
                                <c:out value="${idea.member.name}"/>
                            </td>
                            <c:choose>
                                <c:when test="${sessionScope.loginMember.id != idea.member.id}">
                                    <c:choose>
                                        <c:when test="${!fn:contains(followIdea,idea.member.id)}">
                                            <td>
                                                <form method="POST" action="<c:url value='/following/create/idea' />">
                                                    <button class="ui tiny active button" type="submit" name="followedId" value="${idea.id}">
                                                        <i class="user icon"></i>フォロー
                                                    </button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="POST" action="<c:url value='/following/destroy/idea' />">
                                                    <button class="ui tiny animated button" type="submit" name="followedId" value="${idea.id}">
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
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <fmt:formatDate value='${idea.createdAt}' pattern='yyyy-MM-dd HH:mm'/>
                            </td>
                            <td>
                                <c:out value="${idea.title}"/>
                            </td>
                            <td>
                                <a href="<c:url value='/ideas/show?id=${idea.id}' />">詳細を見る</a>
                            </td>
                            <c:choose>
                                <c:when test="${idea.favors == 0}">
                                    <td>
                                        <c:out value="${idea.favors}"/>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <a href="<c:url value='/favors?idea_id=${idea.id}' />">
                                            <c:out value="${idea.favors}"/>
                                        </a>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">日報件数
                    <c:out value="${getMyFollowingIdeasCnt}"/>
                </div>&nbsp;
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getMyFollowingIdeasCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/timeline?page=${i}' />">
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