<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>With advice from the director</h2>
        <c:import url="_circular.jsp"/>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getDirectorAdviceCnt == 0}">
                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>There's no idea with the advice of the director.</h3>
                            <p>Any advice from the director for ideas will be displayed here.</p>
                        </div>
                    </div>
                    <div class="ui three stackable raised link cards">
                        <c:forEach begin="0" end="5" step="1">
                            <div class="ui card">
                                <a class="content" href="">
                                    <span class="right floated"></span>
                                    <span class="header"></span>
                                    <span class="description"></span>
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
                                    <span class="right floated"><fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/></span>
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
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <c:import url="_page.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>