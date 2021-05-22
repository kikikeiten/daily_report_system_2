<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>My drafts</h2>
        <c:import url="_circles.jsp"/>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyDraftsCnt == 0}">
                    <c:import url="_noDraft.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="idea" items="${getMyDrafts}">
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