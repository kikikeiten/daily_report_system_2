<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>タイムライン</h2>
        <c:choose>
            <c:when test="${getMyFollowReportsCount == 0}">
                <h3>
                    <c:out value="${sessionScope.login_employee.name}" />
                    さんはまだ誰もフォローしていません。
                </h3>
                <p>作成されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="timeline_list" class="ui striped table">
                    <tbody>
                        <tr>
                            <th class="timeline_name">氏名</th>
                            <th class="timeline_follow">フォロー</th>
                            <th class="timeline_date">日付</th>
                            <th class="timeline_title">タイトル</th>
                            <th class="timeline_action">操作</th>
                            <th class="timeline_like">いいね数</th>
                        </tr>
                        <c:forEach var="report" items="${getMyFollowAllReports}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="timeline_name"><c:out
                                        value="${report.employee.name}" /></td>
                                <c:choose>
                                    <c:when
                                        test="${sessionScope.login_employee.id != report.employee.id}">
                                        <c:choose>
                                            <c:when
                                                test="${!fn:contains(list_report_id,report.employee.id)}">
                                                <td class="report_follow">
                                                    <form method="POST"
                                                        action="<c:url value='/follow/create' />">
                                                        <button class="ui tiny active button" type="submit"
                                                            name="following" value="${report.id}">
                                                            <i class="user icon"></i> フォロー
                                                        </button>
                                                    </form>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="report_follow">
                                                    <form method="POST"
                                                        action="<c:url value='/follow/destroy' />">
                                                        <button class="ui tiny animated button" type="submit"
                                                            name="employee_id" value="${report.id}">
                                                            <div class="visible content">
                                                                <i class="user icon"></i>フォロー中
                                                            </div>
                                                            <div class="hidden content">
                                                                <i class="user icon"></i>フォロー解除
                                                            </div>
                                                        </button>
                                                    </form>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="timeline_date"><fmt:formatDate
                                        value='${report.created_at}' pattern='yyyy-MM-dd HH:mm' /></td>
                                <td class="timeline_title">${report.title}</td>
                                <td class="timeline_action"><a
                                    href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                                <c:choose>
                                    <c:when test="${report.likes == 0}">
                                        <td class="timeline_likes"><c:out value="${report.likes}" /></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="timeline_likes"><a
                                            href="<c:url value='/likes/index?report_id=${report.id}' />"><c:out
                                                    value="${report.likes}" /></a></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getMyFollowReportsCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getMyFollowReportsCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                    </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/timeline?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                    </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <br>
        <p>
            <a href="<c:url value='/' />">トップページへ戻る</a>
        </p>
    </c:param>
</c:import>