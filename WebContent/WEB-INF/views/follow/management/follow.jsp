<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
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
        <c:choose>
            <c:when test="${employee != null}">
                <h2>
                    <c:out value="${employee_name}" />
                    さんにフォローさせる
                </h2>
                <c:choose>
                    <c:when test="${getEmployeeNotFollowingCount == 0}">
                        <h3>
                            <c:out value="${employee_name}" />
                            さんは全ての従業員をフォローしています。
                        </h3>
                    </c:when>
                    <c:otherwise>
                        <table id="following_list" class="ui celled striped table">
                            <tbody>
                                <tr>
                                    <th class="following_name">氏名</th>
                                    <th class="follow">フォロー</th>
                                </tr>
                                <c:forEach var="following" items="${getEmployeeNotFollowing}"
                                    varStatus="status">
                                    <tr class="row${status.count % 2}">
                                        <td class="following_name"><c:out
                                                value="${following.name}" /></td>
                                        <td class="follow">
                                            <form method="POST"
                                                action="<c:url value='/management/follow/create' />">
                                                <button class="ui tiny active button" type="submit"
                                                    name="follow_id" value="${following.id}">
                                                    <i class="user icon"></i> フォロー
                                                </button>
                                                <input type="hidden" name="employee_operated"
                                                    value="${employee_operated}">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="ui label">未フォロー ${getEmployeeNotFollowingCount}</div>&nbsp;
                        <div class="ui mini pagination menu">
                            <c:forEach var="i" begin="1"
                                end="${((getEmployeeNotFollowingCount - 1) / 10) + 1}" step="1">
                                <c:choose>
                                    <c:when test="${i == page}">
                                        <div class="item active">
                                            <c:out value="${i}" />
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="item"
                                            href="<c:url value='/management/follow?id=${page_number}&page=${i}' />"><c:out
                                                value="${i}" /></a>&nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <br>
        <br>
        <p>
            <a href="<c:url value='/employees' />">従業員一覧へ戻る</a>
        </p>
    </c:param>
</c:import>