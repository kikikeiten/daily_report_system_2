<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
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
        <h2>課長差し戻しの日報一覧</h2>
        <c:choose>
            <c:when test="${getManagerRemandReportsCount == 0}">
                <h3>課長差し戻しの日報はありません。</h3>
                <p>提出した日報が課長に差し戻されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="mr_report_list" class="ui striped table">
                    <tbody>
                        <tr>
                            <th class="mr_report_name">氏名</th>
                            <th class="mr_report_date">日付</th>
                            <th class="mr_report_title">タイトル</th>
                            <th class="mr_report_action">操作</th>
                            <th class="mr_report_like">いいね数</th>
                            <th class="mr_report_approval">承認</th>
                        </tr>
                        <c:forEach var="report" items="${getAllManagerRemandReports}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="mr_report_name"><c:out
                                        value="${report.employee.name}" /></td>
                                <td class="mr_report_date"><fmt:formatDate
                                        value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                                <td class="mr_report_title">${report.title}</td>
                                <td class="mr_report_action"><a
                                    href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                                <c:choose>
                                    <c:when test="${report.likes == 0}">
                                        <td class="mr_report_likes"><c:out value="${report.likes}" /></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="mr_report_likes"><a
                                            href="<c:url value='/likes?report_id=${report.id}' />"><c:out
                                                    value="${report.likes}" /></a></td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="mr_report_approval">
                                    <form method="POST"
                                        action="<c:url value='/manager/remand/create' />">
                                        <input type="hidden" name="report_id" value="${report.id}" />
                                        <button type="submit" name="submit" value="${2}" class="ui positive button">再提出</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getManagerRemandReportsCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getManagerRemandReportsCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                        </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/remand/manager?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <br>
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