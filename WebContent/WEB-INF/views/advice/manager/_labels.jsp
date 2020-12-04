<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="<c:url value='/' />" class="ui image label">
    My ideas
    <span class="detail">
        <c:out value="${getMyIdeasCnt}" />
    </span>
</a>
<a href="<c:url value='/ideas' />" class="ui image label">
    All ideas
    <span class="detail">
        <c:out value="${getAllIdeasCntButDrafts}" />
    </span>
</a>
<a href="<c:url value='/drafts' />" class="ui image label">
    My drafts
    <span class="detail">
        <c:out value="${getMyDraftsCount}" />
    </span>
</a>
<div class="ui teal image label">
    Manager's advice
    <div class="detail">
        <c:out value="${getManagerAdviceCnt}" />
    </div>
</div>
<a href="<c:url value='/advice/director' />" class="ui image label">
    Director's advice
    <span class="detail">
        <c:out value="${getDirectorAdviceCnt}" />
    </span>
</a>