<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${member != null}">
                <div class="ui header">
                    <i class="far fa-smile"></i>
                    <div class="content">
                        <c:out value="${member.name}"/>
                    </div>
                    <div class="ui label">
                        <c:choose>
                            <c:when test="${member.role == 0}">Associate</c:when>
                            <c:when test="${member.role == 1}">Administrator</c:when>
                            <c:when test="${member.role == 2}">Manager</c:when>
                            <c:when test="${member.role == 3}">Director</c:when>
                        </c:choose>
                    </div>
                    <div class="ui sub header">
                        @
                        <c:out value="${member.code}"/>
                    </div>
                </div>
                <div class="ui raised very padded container segment">
                    <table class="ui celled striped table">
                        <thead>
                        <tr>
                            <th colspan="3">Profile</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Member ID</td>
                            <td><c:out value="${member.code}"/></td>
                        </tr>
                        <tr>
                            <td>Name</td>
                            <td><c:out value="${member.name}"/></td>
                        </tr>
                        <tr>
                            <td>Role</td>
                            <td>
                                <c:choose>
                                    <c:when test="${member.role == 0}">Associate</c:when>
                                    <c:when test="${member.role == 1}">Administrator</c:when>
                                    <c:when test="${member.role == 2}">Manager</c:when>
                                    <c:when test="${member.role == 3}">Director</c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>Created</td>
                            <td><fmt:formatDate value="${member.createdAt}" pattern="yyyy / MM / dd HH:mm"/></td>
                        </tr>
                        <tr>
                            <td>Updated</td>
                            <td><fmt:formatDate value="${member.updatedAt}" pattern="yyyy / MM / dd HH:mm"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <button onclick="location.href='<c:url value='/members'/>'" class="circular ui icon button">
                    <i class="fas fa-long-arrow-alt-left"></i>
                </button>
                <button onclick="location.href='<c:url value='/members/edit?id=${member.id}'/>'" class="circular ui icon button">
                    <i class="fas fa-cogs"></i>
                </button>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
            </c:otherwise>
        </c:choose>
        <c:if test="${member == null}">
            <button onclick="location.href='<c:url value='/members'/>'" class="circular ui icon button">
                <i class="fas fa-long-arrow-alt-left"></i>
            </button>
        </c:if>
    </c:param>
</c:import>