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
        <h2>下書きの日報一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="draft_name">氏名</th>
                    <th class="draft_date">日付</th>
                    <th class="draft_title">タイトル</th>
                    <th class="draft_action">操作</th>
                    <th class="draft_submit">承認</th>
                </tr>
                <c:forEach var="draft" items="${getMyAllDrafts}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="draft_name"><c:out value="${draft.employee.name}" /></td>
                        <td class="draft_date"><fmt:formatDate
                                value='${draft.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="draft_title">${draft.title}</td>
                        <td class="draft_action"><a
                            href="<c:url value='/reports/show?id=${draft.id}' />">詳細を見る</a></td>
                        <td class="draft_submit">
                            <form method="POST"
                                action="<c:url value='/approvals/submission' />">
                                <c:choose>
                                    <c:when test="${sessionScope.login_employee.admin_flag != 3}">
                                        <button type="submit" name="submit" value="${2}">提出</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" name="submit" value="${4}">提出</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="pagination">
            （全 ${getMyDraftsCount} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((getMyDraftsCount - 1) / 10) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/draft?page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='/reports/new' />">新規日報の登録</a>
        </p>
    </c:param>
</c:import>