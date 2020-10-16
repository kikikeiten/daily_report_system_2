<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>課長承認待ちの日報一覧</h2>
        <c:choose>
            <c:when test="${getManagerApprovalReportsCount == 0}">
                <h3>課長承認待ちの日報はありません。</h3>
                <p>他の社員が課長に提出するとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">氏名</th>
                            <th class="report_date">日付</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_action">操作</th>
                            <th class="report_like">いいね数</th>
                            <th class="report_approval">承認</th>
                        </tr>
                        <c:forEach var="report" items="${getAllManagerApprovalReports}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="report_name"><c:out
                                        value="${report.employee.name}" /></td>
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
                                        test="${sessionScope.login_employee.id != report.employee.id}">
                                        <div style="display: inline-flex">
                                            <form method="POST"
                                                action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}">承認</button>
                                            </form>
                                            &nbsp;
                                            <form method="POST"
                                                action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${1}">差し戻し</button>
                                            </form>
                                        </div>
                                    </c:if></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getManagerApprovalReportsCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getManagerApprovalReportsCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/approval/manager?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/reports/new' />">新規日報の登録</a>
        </p>
        <p>
            <a href="<c:url value='/drafts' />">下書きの日報一覧（${getMyDraftsCount}）</a>
        </p>
        <p>
            <a href="<c:url value='/remand/director' />">部長差し戻しの日報一覧（${getDirectorRemandReportsCount}）</a>
        </p>
    </c:param>
</c:import>