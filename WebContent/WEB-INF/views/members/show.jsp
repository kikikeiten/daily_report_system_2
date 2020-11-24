<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${member != null}">
                <div class="ui header">
                    <i class="far fa-smile"></i>
                    <div class="content">
                        <c:out value="${member.name}" />
                    </div>
                    <div class="ui label">
                        <c:choose>
                            <c:when test="${member.role == 0}">Associate</c:when>
                            <c:when test="${member.role == 1}">Administrator</c:when>
                            <c:when test="${member.role == 2}">Manager</c:when>
                            <c:when test="${member.role == 3}">Director</c:when>
                        </c:choose>
                    </div>
                    <div class="ui sub header">@<c:out value="${member.code}" /></div>
                </div>

                <div class="ui raised very padded container segment">

                    <table class="ui celled striped table">
                        <tbody>
                            <tr>
                                <th>社員番号</th>
                                <td><c:out value="${member.code}" /></td>
                            </tr>
                            <tr>
                                <th>氏名</th>
                                <td><c:out value="${member.name}" /></td>
                            </tr>
                            <tr>
                                <th>権限</th>
                                <td><c:choose>
                                        <c:when test="${member.role == 1}">管理者</c:when>
                                        <c:otherwise>一般</c:otherwise>
                                    </c:choose></td>
                            </tr>
                            <tr>
                                <th>登録日時</th>
                                <td><fmt:formatDate value="${member.createdAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                            </tr>
                            <tr>
                                <th>更新日時</th>
                                <td><fmt:formatDate value="${member.updatedAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                            </tr>
                        </tbody>
                    </table>

                </div>

                <p>
                    <a href="<c:url value='/members/edit?id=${member.id}' />">この従業員情報を編集する</a>
                </p>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/members' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>