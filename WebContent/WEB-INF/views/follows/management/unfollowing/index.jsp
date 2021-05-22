<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${member != null}">
                <h2>List of members <c:out value="${member.name}"/> isn't following</h2>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getMemberNotFollowingCnt == 0}">
                            <c:import url="_noUnfollowing.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <div class="ui text container">
                                <table class="ui padded single line striped table">
                                    <tbody>
                                    <c:forEach var="member" items="${getMemberNotFollowing}">
                                        <tr>
                                            <td>
                                                <a href="<c:url value="/members/show?id=${member.id}"/>"><c:out value="${member.name}"/></a>
                                            </td>
                                            <td>
                                                <form method="POST" action="<c:url value='/management/unfollowing/create' />">
                                                    <button class="ui tiny active button" type="submit" name="followedId" value="${member.id}">
                                                        <i class="user icon"></i>
                                                        follow
                                                    </button>
                                                    <input type="hidden" name="followingId" value="${followingId}">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="ui hidden divider"></div>
                                <c:import url="_pages.jsp"/>
                            </div>
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