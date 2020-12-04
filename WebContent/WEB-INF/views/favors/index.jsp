<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${idea != null}">
                <h2>Favors</h2>
                <div class="ui raised very padded container segment">
                    <c:choose>
                        <c:when test="${getFavorsCnt == 0}">
                            <c:import url="_noFavor.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <div class="ui text container">
                                <table class="ui padded single line striped table">
                                    <tbody>
                                    <c:forEach var="favor" items="${getFavors}">
                                        <tr>
                                            <td>
                                                <a href="<c:url value="/members/show?id=${favor.member.id}"/>"><c:out value="${favor.member.name}"/></a>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${favor.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
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
                <button onclick="location.href='<c:url value='/ideas'/>'" class="circular ui icon button">
                    <i class="fas fa-long-arrow-alt-left"></i>
                </button>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>