<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${toast != null}">
            <script>
                $('body')
                    .toast({
                        class: 'success',
                        message: "${toast}",
                        position: 'bottom right',
                        showProgress: 'top',
                        progressUp: true,
                        className: {
                            toast: 'ui message'
                        }
                    });
            </script>
        </c:if>
        <h2>
            With advice from the director
        </h2>
        <div class="circular ui icon teal mini button" data-variation="inverted"></div>
        <script>
            $('.teal.button')
                .popup({
                    position: 'bottom center',
                    content: 'With advice from the director'
                });
        </script>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getDirectorAdviceCnt == 0}">
                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>
                                There is no idea with the advice of the director.
                            </h3>
                            <p>
                                Any advice from the director for ideas will be displayed here.
                            </p>
                        </div>
                    </div>
                    <div class="ui three stackable raised link cards">
                        <c:forEach begin="0" end="5" step="1">
                            <div class="ui card">
                                <a class="content" href="">
                                    <span class="right floated"></span>
                                    <span class="header"></span>
                                    <span class="description">
                                    </span>
                                </a>
                                <div class="extra content">
                                    <button class="circular ui mini icon button">
                                        <i class="far fa-paper-plane"></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="idea" items="${getDirectorAdvice}">
                            <div class="ui teal card">
                                <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />">
                                    <span class="right floated"><fmt:formatDate value='${idea.createdate}' pattern='MM / dd'/>
                                    <span class="header">
                                            <c:out value="${idea.title}"/>
                                    <span class="description">
                                    </span>
                                </a>
                                <div class="extra content">
                                    <form method="POST" action="<c:url value='/advice/director/create' />" class="left floated">
                                        <input type="hidden" name="ideaId" value="${idea.id}"/>
                                        <button type="submit" name="reviewStatus" value="${4}" class="circular ui mini icon blue button">
                                            <i class="fas fa-paper-plane"></i>
                                        </button>
                                    </form>
                                    <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                        <c:out value="${idea.member.name}"/>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getDirectorAdviceCnt - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/advice/director?page=${i}' />">
                                        <c:out value="${i}"/>
                                    </a>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <a href="<c:url value='/' />" class="ui image label">
            My ideas
            <span class="detail">
                <c:out value="${getMyIdeasCnt}"/>
            </span>
        </a>
        <a href="<c:url value='/ideas' />" class="ui image label">
            All ideas
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
    </c:param>
</c:import>