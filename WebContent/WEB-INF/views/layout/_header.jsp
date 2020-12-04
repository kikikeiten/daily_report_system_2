<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui hidden divider"></div>
<div class="ui borderless container menu">
    <a href="<c:url value='/'/>" class="item">
        <b>TipSwatch</b>
    </a>
    <c:if test="${sessionScope.loginMember != null}">
        <div class="item right">
            <button class="circular ui icon button custom">
                <i class="far fa-smile"></i>
            </button>
            <div class="ui fluid custom popup bottom left transition hidden">
                <div class="ui header">
                    <c:out value="${sessionScope.loginMember.name}"/>
                </div>
                <p class="ui right">
                    @
                    <c:out value="${sessionScope.loginMember.code}"/>
                </p>
                <c:if test="${sessionScope.loginMember.role == 0}">
                    <div class="ui large label">
                        Associate
                    </div>
                </c:if>
                <c:if test="${sessionScope.loginMember.role == 1}">
                    <div class="ui large label">
                        Administrator
                    </div>
                </c:if>
                <c:if test="${sessionScope.loginMember.role == 2}">
                    <div class="ui large label">
                        Manager
                    </div>
                </c:if>
                <c:if test="${sessionScope.loginMember.role == 3}">
                    <div class="ui large label">
                        Director
                    </div>
                </c:if>
                <div class="ui link list">
                    <c:if test="${sessionScope.loginMember.role == 1 || sessionScope.loginMember.role == 2 || sessionScope.loginMember.role == 3}">
                        <a href="<c:url value='/members' />" class="item">
                            All members
                        </a>
                    </c:if>
                    <a href="<c:url value='/ideas' />" class="item">
                        All ideas
                    </a>
                    <a href="<c:url value='/timeline' />" class="item">
                        Timeline
                    </a>
                    <a href="<c:url value='/following' />" class="item">
                        Following
                        <span class="ui mini circular label">
                            <c:out value="${getMyFollowingCnt}"/>
                        </span>
                    </a>
                    <a href="<c:url value='/followers' />" class="item">
                        Follower
                        <span class="ui mini circular label">
                            <c:out value="${getMyFollowerCnt}"/>
                        </span>
                    </a>
                    <c:if test="${sessionScope.loginMember.role == 2}">
                        <a href="<c:url value='/reviews/manager'/>" class="item">
                            Waiting
                            <span class="ui mini circular label">
                                <c:out value="${getManagerReviewsCnt}"/>
                            </span>
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.loginMember.role == 3}">
                        <a href="<c:url value='/reviews/director' />" class="item">
                            Waiting
                            <span class="ui mini circular label">
                                <c:out value="${getDirectorReviewsCnt}"/>
                            </span>
                        </a>
                    </c:if>
                    <a class="item" href="<c:url value='/logout'/>">
                        Logout
                    </a>
                </div>
            </div>&nbsp;&nbsp;
            <script>
                $('.custom.button')
                    .popup({
                        popup: $('.custom.popup'),
                        on: 'click'
                    });
            </script>
            <button onclick="location.href='<c:url value='/ideas/new'/>'" class="ui positive button">
                <i class="fas fa-edit"></i>
            </button>
        </div>
    </c:if>
</div>