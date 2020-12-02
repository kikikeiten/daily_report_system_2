<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${member != null}">
                <h2>List of members <c:out value="${member.name}"/> isn't following</h2>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getMemberNotFollowingCnt == 0}">

                            <div class="ui active dimmer">
                        <div class="content">
                            <h3><c:out value="${member.name}"/> follows all the members.</h3>
                            <p>Any members <c:out value="${member.name}"/> doesn't follow will be displayed here.</p>
                        </div>
                    </div>
                    <div class="ui three stackable raised link cards">
                    <div class="ui text container">
                            <table class="ui padded single line striped table">
                        <c:forEach begin="0" end="3" step="1">
                            <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            </tr>
                        </c:forEach>
                        </table>
                            </div>
                    </div>
                        </c:when>
                        <c:otherwise>
                        <div class="ui text container">
                            <table class="ui padded single line striped table">
                                <tbody>
                                <c:forEach var="member" items="${getMemberNotFollowing}">
                                    <tr>
                                        <td>
                                            <a href="<c:url value="/members/show?id=${member.id}"/>"><c:out value="${member.name}"/></a>
                                        </td>
                                        <td>
                                            <form method="POST" action="<c:url value='/management/unfollowing/create' />">
                                                <button class="ui tiny active button" type="submit" name="followedId" value="${member.id}">
                                                    <i class="user icon"></i>follow
                                                </button>
                                                <input type="hidden" name="followingId" value="${followingId}">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="ui hidden divider"></div>
                            <div class="ui mini pagination menu">
                                <c:forEach var="i" begin="1" end="${((getMemberNotFollowingCnt - 1) / 12) + 1}" step="1">
                                    <c:choose>
                                        <c:when test="${i == page}">
                                            <div class="item active">
                                                <c:out value="${i}"/>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="item" href="<c:url value='/management/unfollowing?id=${member.id}&page=${i}'/>">
                                                <c:out value="${i}"/></a>&nbsp;
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:import url="_labels.jsp"/>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>