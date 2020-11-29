<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>All members</h2>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMembersCnt == 0}">
                    <h3>従業員は未登録です。</h3>
                    <p>登録されるとここに表示されます。</p>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="member" items="${getMembers}">
                            <div class="ui card">
                                <a class="content" href="<c:url value='/members/show?id=${member.id}' />">
                                    <span class="header">
                                        <c:out value="${member.name}"/>
                                    </span>
                                    <span class="meta"> @ <c:out value="${member.code}"/></span>
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
                                                        <button class="circular ui mini icon green button" type="submit" name="unfollowedId" value="${member.id}">
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
                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getMembersCnt - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/members?page=${i}' />"><c:out value="${i}"/></a>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="ui teal image label">All menbers
            <div class="detail">
                <c:out value="${getMembersCnt}"/>
            </div>
        </div>
        <button onclick="location.href='<c:url value='/members/new'/>'" class="ui positive button">新規従業員</button>
    </c:param>
</c:import>