<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>Follower</h2>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyFollowerCnt == 0}">
                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>You have no followers yet.</h3>
                            <p>If someone follows you, your followers will appear here.</p>
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
                            <c:forEach var="member" items="${getMyFollower}">
                                <tr>
                                    <td><a href="<c:url value="/members/show?id=${member.followingId.id}"/>"><c:out value="${member.followingId.name}" /></a></td>
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
                        <div class="ui mini pagination menu">
                            <c:forEach var="i" begin="1" end="${((getMyFollowerCnt - 1) / 12) + 1}" step="1">
                                <c:choose>
                                    <c:when test="${i == page}">
                                        <div class="item active">
                                            <c:out value="${i}" />
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="item" href="<c:url value='/followers?page=${i}' />"> <c:out value="${i}" />
                                        </a>&nbsp;
                            </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>