<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                            <div class="ui active dimmer">
                        <div class="content">
                            <h3>There's no favorite in <c:out value="${idea.member.name}"/>'s idea</h3>
                            <p>Will be displayed here when favored</p>
                        </div>
                    </div>
                    <div class="ui three stackable raised link cards">
                    <div class="ui text container">
                            <table class="ui padded single line striped table">
                        <c:forEach begin="0" end="3" step="1">
                            <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            </tr>
                        </c:forEach>
                        </table>
                            </div>
                    </div>
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
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getFavorsCnt - 1) / 12) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/favors?id=${idea.id}&page=${i}' />">
                                    <c:out value="${i}"/>
                                </a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                </div>
                </c:otherwise>
                </c:choose>
                </div>
                <c:import url="_labels.jsp"/>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>