<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
                <h2>With advice from the manager</h2>
                <c:import url="_circles.jsp"/>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getManagerAdviceCnt == 0}">
                            <c:import url="_noAdvice.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <div class="ui three stackable raised link cards">
                                <c:forEach var="idea" items="${getManagerAdvice}">
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
                                </c:forEach>
                            </div>
                            <div class="ui hidden divider"></div>
                            <c:import url="_pages.jsp"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:import url="_labels.jsp"/>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
                <button onclick="location.href='<c:url value='/members'/>'" class="circular ui icon button">
                    <i class="fas fa-long-arrow-alt-left"></i>
                </button>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>