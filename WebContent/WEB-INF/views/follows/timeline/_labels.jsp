<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
    <i class="fas fa-long-arrow-alt-left"></i>
</button>
<div class="ui teal image label">
    Timeline
    <div class="detail">
        <c:out value="${getMyFollowingIdeasCnt}"/>
    </div>
</div>