<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui centered mini pagination menu">
    <c:forEach var="i" begin="1" end="${((getMyFollowingIdeasCnt - 1) / 12) + 1}" step="1">
        <c:choose>
            <c:when test="${i == page}">
                <div class="item active">
                    <c:out value="${i}"/>
                </div>
            </c:when>
            <c:otherwise>
                <a class="item" href="<c:url value='/timeline?page=${i}' />">
                    <c:out value="${i}"/>
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>