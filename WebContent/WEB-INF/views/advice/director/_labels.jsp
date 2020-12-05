<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="<c:url value='/' />" class="ui image label">
    My ideas
    <span class="detail">
        <c:out value="${getMyIdeasCnt}"/>
    </span>
</a>
<a href="<c:url value='/ideas' />" class="ui image label">All ideas
    <span class="detail">
        <c:out value="${getIdeasCntButDrafts}"/>
    </span>
</a>
<a href="<c:url value='/drafts' />" class="ui image label">
    My drafts
    <span class="detail">
        <c:out value="${getMyDraftsCnt}"/>
    </span>
</a>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
    <a href="<c:url value='/advice/manager' />" class="ui image label">
        Manager's advice
        <span class="detail">
            <c:out value="${getManagerAdviceCnt}"/>
        </span>
    </a>
</c:if>
<c:if test="${sessionScope.loginMember.role == 2}">
    <a href="<c:url value='/reviews/manager' />" class="ui image label">
        Manager's reviews
        <span class="detail">
            <c:out value="${getManagerReviewsCnt}"/>
        </span>
    </a>
</c:if>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1 || sessionScope.loginMember.role == 2}">
    <div class="ui teal image label">
        Director's advice
        <div class="detail">
            <c:out value="${getDirectorAdviceCnt}"/>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.loginMember.role == 3}">
    <a href="<c:url value='/reviews/director' />" class="ui image label">
        Director's reviews
        <span class="detail">
            <c:out value="${getDirectorReviewsCnt}"/>
        </span>
    </a>
</c:if>