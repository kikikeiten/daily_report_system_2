<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
    <i class="fas fa-long-arrow-alt-left"></i>
</button>
<div class="ui teal image label">
    My stamps
    <div class="detail">
        <c:out value="${getMyJoinsCnt}"/>
    </div>
</div>
<c:if test="${sessionScope.loginMember.role != 0}">
    <a href="<c:url value='/joins/all'/>" class="ui image label">
        All stamps
        <span class="detail">
            <c:out value="${getJoinsCnt}"/>
        </span>
    </a>
</c:if>