<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>All members</h2>
        <c:if test="${sessionScope.loginMember.role != 0}">
            <button onclick="location.href='<c:url value='/members/new'/>'" class="circular ui icon positive button">
                <i class="fas fa-user-plus"></i>
            </button>
        </c:if>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMembersCnt == 0}">
                    <c:import url="_noMember.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="member" items="${getMembers}">
                            <div class="ui card">
                                <a class="content" href="<c:url value='/members/show?id=${member.id}' />">
                                    <span class="header">
                                        <c:out value="${member.name}"/>
                                    </span>
                                    <span class="meta">
                                        @
                                        <c:out value="${member.code}"/>
                                    </span>
                                    <span class="description"></span>
                                </a>
                                <div class="extra content">
                                    <c:choose>
                                        <c:when test="${sessionScope.loginMember.id != member.id}">
                                            <c:choose>
                                                <c:when test="${!fn:contains(followIdea, member.id)}">
                                                    <form method="POST" action="<c:url value='/following/create/member' />" class="left floated">
                                                        <button class="circular ui mini icon green basic button" type="submit" name="followedId" value="${member.id}">
                                                            <i class="fas fa-user-plus"></i>
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form method="POST" action="<c:url value='/following/destroy/member' />" class="left floated">
                                                        <button class="circular ui mini icon red basic button" type="submit" name="unfollowedId" value="${member.id}">
                                                            <i class="fas fa-user-minus"></i>
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                    <span class="right floated">
                                        <button onclick="location.href='<c:url value='/management/following?id=${member.id}'/>'" class="circular ui mini icon blue button">
                                            <i class="far fa-paper-plane"></i>
                                        </button>
                                        <button onclick="location.href='<c:url value='/management/unfollowing?id=${member.id}'/>'" class="circular ui mini icon blue button">
                                            <i class="far fa-paper-plane"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <c:import url="_pages.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>