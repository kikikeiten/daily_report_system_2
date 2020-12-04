<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>All swatches</h2>
        <c:import url="_circular.jsp"/>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getIdeasCntButDrafts == 0}">
                    <c:import url="_noIdea.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="idea" items="${getIdeasButDrafts}">
                            <c:choose>
                                <c:when test="${idea.reviewStatus == 1}">
                                    <div class="ui olive card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}'/>">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                            </span>
                                            <span class="header"><c:out value="${idea.title}"/></span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}'/>">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}'/>">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 2}">
                                    <div class="ui green card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}'/>">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                            </span>
                                            <span class="header">
                                                <c:out value="${idea.title}"/>
                                            </span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}'/>">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}'/>">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 3}">
                                    <div class="ui teal card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}'/>">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                            </span>
                                            <span class="header">
                                                <c:out value="${idea.title}"/>
                                            </span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/favors?_id=${idea.id}'/>">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}'/>">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 4}">
                                    <div class="ui blue card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}'/>">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                            </span>
                                            <span class="header">
                                                <c:out value="${idea.title}"/>
                                            </span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?id=${idea.id}' />">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}'/>">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 6}">
                                    <div class="ui violet card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}'/>">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                            </span>
                                            <span class="header">
                                                <c:out value="${idea.title}"/>
                                            </span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}'/>">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}'/>">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <div class="ui centered mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getIdeasCntButDrafts - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/ideas?page=${i}' />">
                                        <c:out value="${i}"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>