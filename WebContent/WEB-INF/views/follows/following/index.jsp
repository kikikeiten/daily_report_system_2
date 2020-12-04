<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>Following</h2>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyFollowingCnt == 0}">
                    <c:import url="_noFollowing.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="ui text container">
                        <table class="ui padded single line striped table">
                            <tbody>
                            <c:forEach var="member" items="${getMyFollowing}">
                                <tr>
                                    <td><a href="<c:url value="/members/show?id=${member.followedId.id}"/>"><c:out value="${member.followedId.name}"/></a></td>
                                    <td>
                                        <form method="POST" action="<c:url value='/following/destroy/index'/>">
                                            <button class="ui tiny animated button" type="submit" name="followedId" value="${member.id}">
                                                <div class="visible content">
                                                    <i class="user icon"></i>
                                                    following
                                                </div>
                                                <div class="hidden content">
                                                    <i class="user icon"></i>
                                                    unfollow
                                                </div>
                                            </button>
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
    </c:param>
</c:import>