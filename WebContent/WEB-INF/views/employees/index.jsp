<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div class="ui success message">
                <i class="close icon"></i>
                <script>
                    $('.message .close').on('click', function() {
                        $(this).closest('.message').transition('fade');
                    });
                </script>
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <c:choose>
            <c:when test="${employees_count == 0}">
                <h3>
                    従業員は未登録です。
                </h3>
                <p>登録されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
        <table id="employee_list" class="ui celled striped table">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォロー中一覧</th>
                    <th>強制フォロー</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td><c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose></td>
                        <td><a
                            href="<c:url value='/management/unfollow?id=${employee.id}' />">詳細を表示</a></td>
                        <td><a
                            href="<c:url value='/management/follow?id=${employee.id}' />">詳細を表示</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="ui label">従業員数 ${employees_count}</div>&nbsp;
        <div class="ui mini pagination menu">
            <c:forEach var="i" begin="1"
                end="${((employees_count - 1) / 10) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <div class="item active">
                            <c:out value="${i}" />
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a class="item"
                            href="<c:url value='/employees?page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        </c:otherwise>
        </c:choose>
        <br>
        <br>
        <button onclick="location.href='<c:url value='/employees/new' />'"
            class="ui positive button">新規従業員</button>
    </c:param>
</c:import>