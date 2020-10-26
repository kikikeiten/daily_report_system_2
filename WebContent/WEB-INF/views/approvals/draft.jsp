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
        <c:choose>
            <c:when test="${getMyDraftsCount == 0}">
                <h3>
                    <c:out value="${sessionScope.login_employee.name}" />
                    さんの下書きの日報はありません。
                </h3>
                <p>下書きを作成するとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="draft_list" class="ui striped table">
                    <tbody>
                        <tr>
                            <th class="draft_name">氏名</th>
                            <th class="draft_date">日付</th>
                            <th class="draft_title">タイトル</th>
                            <th class="draft_action">操作</th>
                            <th class="draft_approval">承認</th>
                        </tr>
                        <c:forEach var="draft" items="${getMyAllDrafts}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="draft_name"><c:out
                                        value="${draft.employee.name}" /></td>
                                <td class="draft_date"><fmt:formatDate
                                        value='${draft.report_date}' pattern='yyyy-MM-dd' /></td>
                                <td class="draft_title">${draft.title}</td>
                                <td class="draft_action"><a
                                    href="<c:url value='/reports/show?id=${draft.id}' />">詳細を見る</a></td>
                                <td class="draft_approval">
                                    <form method="POST"
                                        action="<c:url value='/submission/update' />">
                                        <c:choose>
                                            <c:when test="${sessionScope.login_employee.admin_flag != 3}">
                                                <button type="submit" name="submit" value="${2}" class="ui primary button">提出</button>
                                                <input type="hidden" name="report_id" value="${draft.id}" />
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" name="submit" value="${4}" class="ui primary button">提出</button>
                                                <input type="hidden" name="report_id" value="${draft.id}" />
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
            </c:otherwise>
        </c:choose>
        <br>
        <p>
            <a href="<c:url value='/reports/new' />">新規日報の登録</a>
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