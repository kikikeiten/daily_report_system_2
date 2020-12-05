<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
    <i class="fas fa-home"></i>
</button>
<div class="ui teal image label">
    following
    <div class="detail">
        <c:out value="${getMyFollowingCnt}"/>
    </div>
</div>
<a href="<c:url value='/followers'/>" class="ui image label">
    follower
    <span class="detail">
        <c:out value="${getMyFollowerCnt}"/>
    </span>
</a>