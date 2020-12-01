<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="<c:url value='/management/following?id=${member.id}'/>" class="ui image label"><c:out value="${member.name}" />'s following<span class="detail"> <c:out value="${getMemberFollowingCnt}" />
</span>
</a>
<div class="ui teal image label">
    <c:out value="${member.name}" />
    isn't follow
    <div class="detail">
    <c:out value="${getMemberNotFollowingCnt}" />
    </div>
</div>