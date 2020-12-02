<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="<c:url value='/following'/>" class="ui image label">following<span class="detail"> <c:out value="${getMyFollowingCnt}" />
</span>
</a>
<div class="ui teal image label">
    follower
    <div class="detail">
        <c:out value="${getMyFollowerCnt}" />
    </div>
</div>