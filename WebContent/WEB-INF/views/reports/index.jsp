<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日報 一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_follow">フォロー</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                    <th class="report_like">いいね数</th>
                    <th class="report_approval">承認状況</th>
                </tr>
                <c:forEach var="report" items="${getAllReportsButDrafts}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out
                                value="${report.employee.name}" /></td>
                        <c:choose>
                            <c:when
                                test="${sessionScope.login_employee.id != report.employee.id}">
                                <c:choose>
                                    <c:when
                                        test="${!fn:contains(list_report_id,report.employee.id)}">
                                        <td class="report_follow">
                                            <form method="POST" action="<c:url value='/follow/create' />">
                                                <button type="submit" name="following" value="${report.id}">フォロー</button>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="report_follow">
                                            <form method="POST"
                                                action="<c:url value='/follow/destroy' />">
                                                <button type="submit" name="employee_id"
                                                    value="${report.id}" onmouseover="this.innerText='フォロー解除'"
                                                    onmouseout="this.innerText='フォロー中'">フォロー中</button>
                                            </form>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                        <td class="report_date"><fmt:formatDate
                                value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${report.title}</td>
                        <td class="report_action"><a
                            href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                        <c:choose>
                            <c:when test="${report.likes == 0}">
                                <td class="report_likes"><c:out value="${report.likes}" /></td>
                            </c:when>
                            <c:otherwise>
                                <td class="report_likes"><a
                                    href="<c:url value='/likes?report_id=${report.id}' />"><c:out
                                            value="${report.likes}" /></a></td>
                            </c:otherwise>
                        </c:choose>
                        <td class="report_approval"><c:if
                                test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
                                <c:choose>
                                    <c:when test="${report.approval == 1 && sessionScope.login_employee.id == report.employee.id}">
                                        <a href="<c:url value='/remand/manager' />">課長差し戻し</a>
                                    </c:when>
                                    <c:when test="${report.approval == 1 && sessionScope.login_employee.id != report.employee.id}">
                                        課長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 2}">
                                        課長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                        <a href="<c:url value='/remand/director' />">部長差し戻し</a>
                                    </c:when>
                                    <c:when test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
                                        部長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 4}">
                                        部長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 6}">
                                        承認済み
                                    </c:when>
                                </c:choose>
                            </c:if> <c:if test="${sessionScope.login_employee.admin_flag == 2}">
                                <c:choose>
                                    <c:when test="${report.approval == 1 }">
                                        課長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id}">
                                        <a href="<c:url value='/approval/manager' />">課長承認待ち</a>
                                    </c:when>
                                    <c:when test="${report.approval == 2 && sessionScope.login_employee.id == report.employee.id}">
                                        課長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                        <a href="<c:url value='/remand/director' />">部長差し戻し</a>
                                    </c:when>
                                    <c:when test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
                                        部長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 4}">
                                        部長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 6}">
                                        承認済み
                                    </c:when>
                                </c:choose>
                            </c:if> <c:if test="${sessionScope.login_employee.admin_flag == 3}">
                                <c:choose>
                                    <c:when test="${report.approval == 1}">
                                        課長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 2}">
                                        課長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 3}">
                                        部長差し戻し
                                    </c:when>
                                    <c:when test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id}">
                                        <a href="<c:url value='/approval/director' />">部長承認待ち</a>
                                    </c:when>
                                    <c:when test="${report.approval == 4 && sessionScope.login_employee.id == report.employee.id}">
                                        部長承認待ち
                                    </c:when>
                                    <c:when test="${report.approval == 6}">
                                        承認済み
                                    </c:when>
                                </c:choose>
                            </c:if></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="pagination">
            （全 ${getReportsCountButDrafts} 件）<br />
            <c:forEach var="i" begin="1" end="${((getReportsCountButDrafts - 1) / 10) + 1}"
                step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/reports?page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='/reports/new' />">新規日報の登録</a>
        </p>
        <p>
            <a href="<c:url value='/drafts' />">下書きの日報一覧（${getMyDraftsCount}）</a>
        </p>
        <c:if
            test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
            <p>
                <a href="<c:url value='/remand/manager' />">課長差し戻しの日報一覧（${getManagerRemandReportsCount}）</a>
            </p>
        </c:if>
        <c:if test="${sessionScope.login_employee.admin_flag == 2}">
            <p>
                <a href="<c:url value='/approval/manager' />">課長承認待ちの日報一覧（${getManagerApprovalReportsCount}）</a>
            </p>
        </c:if>
        <c:if
            test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1 || sessionScope.login_employee.admin_flag == 2}">
            <p>
                <a href="<c:url value='/remand/director' />">部長差し戻しの日報一覧（${getDirectorRemandReportsCount}）</a>
            </p>
        </c:if>
        <c:if test="${sessionScope.login_employee.admin_flag == 3}">
            <p>
                <a href="<c:url value='/approval/director' />">部長承認待ちの日報一覧（${getDirectorApprovalReportsCount}）</a>
            </p>
        </c:if>
    </c:param>
</c:import>