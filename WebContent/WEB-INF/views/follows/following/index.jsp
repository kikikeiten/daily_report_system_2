<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>Following</h2>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyFollowingCnt == 0}">
                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>
                                You haven't followed anyone yet.
                            </h3>
                            <p>
                                If you follow someone, your followers will appear here.
                            </p>
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
                            <c:forEach var="member" items="${getMyFollowing}">
                                <tr>
                                    <td><a href="<c:url value="/members/show?id=${member.followedId.id}"/>"><c:out value="${member.followedId.name}" /></a></td>
                                    <td>
                                        <form method="POST" action="<c:url value='/following/destroy/index'/>">
                                            <button class="ui tiny animated button" type="submit" name="followedId" value="${member.id}">
                                                <div class="visible content">
                                                    <i class="user icon"></i>following
                                                </div>
                                                <div class="hidden content">
                                                    <i class="user icon"></i>unfollow
                                                </div>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="ui hidden divider"></div>
                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getMyFollowingCnt - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/following?page=${i}' />"> <c:out value="${i}" />
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