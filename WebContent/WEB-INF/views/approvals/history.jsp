<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>日報「<c:out value="${report_title}"></c:out>」の承認履歴一覧</h2>
        <c:choose>
            <c:when test="${getReportApprovalsCount == 0}">
                <h3>日報「<c:out value="${report_title}"></c:out>」に承認履歴はありません。</h3>
                <p>日報が承認または差し戻しされるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="approval_list" class="ui striped table">
                    <tbody>
                        <tr>
                            <th class="approval_date">承認日時</th>
                            <th class="approval_status">承認状況</th>
                            <th class="approval_comment">コメント</th>
                            <th class="approval_name">承認者</th>
                            <th class="approval_position">役職</th>
                        </tr>
                        <c:forEach var="approval" items="${getReportApprovals}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="approval_date"><fmt:formatDate
                                        value='${approval.created_at}' pattern='yyyy-MM-dd HH:mm' /></td>
                                <td class="approval_status"><c:if
                                        test="${approval.approval == 1 || approval.approval == 3}">
                                差し戻し
                                </c:if> <c:if
                                        test="${approval.approval == 4 || approval.approval == 6}">
                                承認
                                </c:if></td>
                                <td class="approval_comment"><c:out
                                        value="${approval.comment}" /></td>
                                <td class="approval_name"><c:out
                                        value="${approval.employee.name}" /></td>
                                <td class="approval_position"><c:if test="${approval.employee.admin_flag == 2}">
                                課長
                                </c:if> <c:if
                                        test="${approval.employee.admin_flag == 3}">
                                部長
                                </c:if></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${getReportApprovalsCount} 件）<br />
                    <c:forEach var="i" begin="1"
                        end="${((getReportApprovalsCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/approval/history?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <br>
        <p>
            <a href="<c:url value='/reports/show?id=${report_id}' />">日報詳細ページに戻る</a>
        </p>
    </c:param>
</c:import>