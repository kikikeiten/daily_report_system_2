<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button onclick="location.href='<c:url value='/ideas/show?id=${idea.id}'/>'" class="circular ui icon button">
    <i class="fas fa-long-arrow-alt-left"></i>
</button>
<div class="ui teal image label">
    Advice
    <div class="detail">
        <c:out value="${getReviewsCnt}" />
    </div>
</div>