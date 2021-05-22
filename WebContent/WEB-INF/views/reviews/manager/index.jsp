<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${sessionScope.loginMember.role == 2}">
                <h2>Waiting for the manager's review</h2>
                <c:import url="_circles.jsp"/>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getManagerReviewsCnt == 0}">
                            <c:import url="_noReview.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <div class="ui three stackable raised link cards">
                                <c:forEach var="idea" items="${getManagerReviews}">
                                    <div class="ui green card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />">
                                    <span class="right floated">
                                        <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                    </span>
                                            <span class="header">
                                        <c:out value="${idea.title}"/>
                                    </span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <c:if test="${sessionScope.loginMember.id != idea.member.id && idea.member.role != 2}">
                                                <form method="POST" action="<c:url value='/reviews/manager/create' />" class="left floated">
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${1}" class="circular ui mini icon olive button">
                                                        <i class="fas fa-comment-medical"></i>
                                                    </button>
                                                </form>
                                            </c:if>
                                            <c:if test="${sessionScope.loginMember.id != idea.member.id}">
                                                <form method="POST" action="<c:url value='/reviews/manager/create' />" class="left floated">
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${4}" class="circular ui mini icon blue button">
                                                        <i class="far fa-paper-plane"></i>
                                                    </button>
                                                </form>
                                            </c:if>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="ui hidden divider"></div>
                            <c:import url="_pages.jsp"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:import url="_labels.jsp"></c:import>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
                <button onclick="location.href='<c:url value='/'/>'" class="circular ui icon button">
                    <i class="fas fa-long-arrow-alt-left"></i>
                </button>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>