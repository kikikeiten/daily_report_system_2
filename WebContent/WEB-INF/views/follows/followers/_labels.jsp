<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
    <i class="fas fa-home"></i>
</button>
<a href="<c:url value='/following'/>" class="ui image label">
    following
    <span class="detail">
        <c:out value="${getMyFollowingCnt}"/>
    </span>
</a>
<div class="ui teal image label">
    follower
    <div class="detail">
        <c:out value="${getMyFollowerCnt}"/>
    </div>
</div>