<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>Follower</h2>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyFollowerCnt == 0}">
                    <c:import url="_noFollower.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui text container">
                        <table class="ui padded single line striped table">
                            <c:forEach var="member" items="${getMyFollower}">
                                <tr>
                                    <td><a href="<c:url value="/members/show?id=${member.followingId.id}"/>"><c:out value="${member.followingId.name}"/></a></td>
                                    <c:choose>
                                        <c:when test="${!fn:contains(followIdea,member.followingId.id)}">
                                            <td>
                                                <form method="POST" action="<c:url value='/followers/create' />">
                                                    <button class="ui tiny active button" type="submit" name="followerId" value="${member.id}">
                                                        <i class="user icon"></i>follow
                                                    </button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="POST" action="<c:url value='/followers/destroy' />">
                                                    <button class="ui tiny animated button" type="submit" name="followerId" value="${member.id}">
                                                        <div class="visible content">
                                                            <i class="user icon"></i>following
                                                        </div>
                                                        <div class="hidden content">
                                                            <i class="user icon"></i>unfollow
                                                        </div>
                                                    </button>
                                                </form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="ui hidden divider"></div>
                        <c:import url="_pages.jsp"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>