<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <script>
            $('body')
            .toast({
              class: 'success',
              message: "${flush}",
              showProgress: 'top',
              progressUp: true,
              className: {
                  toast: 'ui message'
              }
            })
          ;
            </script>
        </c:if>
        <h2>Menbers</h2>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${employees_count == 0}">
                    <h3>従業員は未登録です。</h3>
                    <p>登録されるとここに表示されます。</p>
                </c:when>
                <c:otherwise>

                    <div class="ui three stackable cards">

                        <c:forEach var="employee" items="${employees}">

                            <div class="card">
                                <a class="content" href="<c:url value='/employees/show?id=${employee.id}' />"> <span class="header"> <c:out value="${employee.name}" />
                                </span> <span class="meta"> @ <c:out value="${employee.code}" />
                                </span> <span class="description"></span>
                                </a>
                                <div class="extra content">

                                    <c:choose>
                                        <c:when test="${sessionScope.login_employee.id != employee.id}">
                                            <c:choose>
                                                <c:when test="${!fn:contains(list_report_id, employee.id)}">
                                                    <form method="POST" action="<c:url value='/follow/create/2' />" class="left floated">
                                                        <button class="circular ui mini icon green basic button" type="submit" name="following" value="${employee.id}">
                                                            <i class="fas fa-user-plus"></i>
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form method="POST" action="<c:url value='/follow/destroy/2' />" class="left floated">
                                                        <button class="circular ui mini icon green button" type="submit" name="employee_id" value="${employee.id}">
                                                            <i class="fas fa-user-minus"></i>
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>

                                    <span class="right floated">
                                        <button onclick="location.href='<c:url value='/management/unfollow?id=${employee.id}' />'" class="circular ui mini icon blue button">
                                            <i class="far fa-paper-plane"></i>
                                        </button>
                                        <button onclick="location.href='<c:url value='/management/follow?id=${employee.id}' />'" class="circular ui mini icon blue button">
                                            <i class="far fa-paper-plane"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </c:forEach>

                    </div>


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
                                    <td><a href="<c:url value='/management/unfollow?id=${employee.id}' />">詳細を表示</a></td>
                                    <td><a href="<c:url value='/management/follow?id=${employee.id}' />">詳細を表示</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="ui label">従業員数 ${employees_count}</div>&nbsp;
        <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((employees_count - 1) / 10) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/employees?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
        <br>
        <br>
        <button onclick="location.href='<c:url value='/employees/new' />'" class="ui positive button">新規従業員</button>
    </c:param>
</c:import>