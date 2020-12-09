<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>Idea "<a href="<c:url value="/ideas/show?id=${idea.id}"/>"><c:out value="${idea.title}"/></a>" advice history</h2>
        <div class="ui raised very padded container segment">
            <div class="ui text container">
                <c:choose>
                <c:when test="${getReviewsCnt == 0}">
                    <c:import url="_noAdvice.jsp"/>
                </c:when>
                <c:otherwise>
                <c:forEach var="review" items="${getReviews}">
                    <div class="ui threaded comments">
                        <div class="comment">
                            <div class="content">
                                <a class="author" href=<c:url value="/members/show?id=${review.member.id}"/>><c:out value="${review.member.name}"/></a>
                                <div class="metadata">
                                    <div class="date"><fmt:formatDate value='${review.createdAt}' pattern='yyyy/MM/dd HH:mm'/></div>
                                    <c:if test="${review.reviewStatus == 1 || review.reviewStatus == 3}">
                                        <div class="ui pink label">Advised</div>
                                    </c:if>
                                    <c:if test="${review.reviewStatus == 4 || review.reviewStatus == 6}">
                                        <div class="ui green label">Approved!</div>
                                    </c:if>
                                </div>
                                <div class="text">
                                    <c:choose>
                                        <c:when test="${review.advice == null}">
                                            No advice.
                                        </c:when>
                                        <c:otherwise>
                                            <pre style="white-space: pre-wrap ;"><c:out value="${review.advice}"/></pre>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ui divider"></div>
                </c:forEach>
                <div class="ui hidden divider"></div>
                <c:import url="_pages.jsp"/>
            </div>
            </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
        </div>
    </c:param>
</c:import>