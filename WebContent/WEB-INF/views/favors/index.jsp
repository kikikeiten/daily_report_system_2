<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${idea != null}">
                <h2>いいねした人</h2>
                <table id="likes_list" class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th class="likes_name">氏名</th>
                        <th class="likes_date">いいねした日時</th>
                    </tr>
                    <c:forEach var="favor" items="${getFavors}">
                        <tr>
                            <td class="likes_name"><c:out value="${favor.member.name}"/></td>
                            <td class="likes_date"><fmt:formatDate value="${favor.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">いいね数 ${getFavorsCnt}</div>
                &nbsp;
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((getFavorsCnt - 1) / 10) + 1}"
                               step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item"
                                   href="<c:url value='/favors?id=${ideaId}&page=${i}' />"><c:out value="${i}"/></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <br>
        <p>
            <a href="<c:url value="/ideas" />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>