<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="ui teal image label">
    <c:out value="${member.name}" />
    's following
    <div class="detail">
    <c:out value="${getMemberFollowingCnt}" />
    </div>
</div>
<a href="<c:url value='/management/unfollowing?id=${member.id}'/>" class="ui image label"><c:out value="${member.name}" /> isn't follow<span class="detail"> <c:out value="${getMemberNotFollowingCnt}" />
</span>
</a>