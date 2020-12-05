<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
    <i class="fas fa-long-arrow-alt-left"></i>
</button>
<a href="<c:url value='/joins/my'/>" class="ui image label">
    My stamps
    <span class="detail">
        <c:out value="${getMyJoinsCnt}"/>
    </span>
</a>
<div class="ui teal image label">
    All stamps
    <div class="detail">
        <c:out value="${getJoinsCnt}"/>
    </div>
</div>