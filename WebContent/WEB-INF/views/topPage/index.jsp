<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:import url="_join.jsp"/>
        <c:import url="_reminder.jsp"/>
        <h2>My ideas</h2>
        <c:import url="_circles.jsp"/>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyIdeasCnt == 0}">
                    <c:import url="_noIdea.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="idea" items="${getMyIdeas}">
                            <c:choose>
                                <c:when test="${idea.reviewStatus == 0}">
                                    <div class="ui yellow card">
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
                                            <form method="POST" action="<c:url value='/drafts/update' />" class="left floated">
                                                <c:choose>
                                                    <c:when test="${sessionScope.loginMember.role != 3}">
                                                        <button type="submit" name="reviewStatus" value="${2}" class="circular ui mini icon green button">
                                                            <i class="far fa-paper-plane"></i>
                                                        </button>
                                                        <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" name="reviewStatus" value="${4}" class="circular ui mini icon blue button">
                                                            <i class="far fa-paper-plane"></i>
                                                        </button>
                                                        <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 1}">
                                    <div class="ui olive card">
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
                                            <form method="POST" action="<c:url value='/advice/manager/create' />" class="left floated">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${2}" class="circular ui mini icon green button">
                                                    <i class="fas fa-paper-plane"></i>
                                                </button>
                                            </form>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 2}">
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
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}' />">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 3}">
                                    <div class="ui teal card">
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
                                </c:when>
                                <c:when test="${idea.reviewStatus == 4}">
                                    <div class="ui blue card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />">
                                            <span class="right floated">
                                                <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/></span>
                                            <span class="header">
                                                <c:out value="${idea.title}"/></span>
                                            <span class="description"></span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}' />">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${idea.reviewStatus == 6}">
                                    <div class="ui violet card">
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
                                            <a class="left floated like" href="<c:url value='/favors?id=${idea.id}' />">
                                                <i class="far fa-heart"></i>
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                            <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                                <c:out value="${idea.member.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <c:import url="_pages.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>