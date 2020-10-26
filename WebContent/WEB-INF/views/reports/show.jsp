<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報「${report.title}」の詳細ページ</h2>
                <table class="ui striped table">
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>フォロー</th>
                            <c:choose>
                                <c:when
                                    test="${sessionScope.login_employee.id != report.employee.id}">
                                    <c:choose>
                                        <c:when
                                            test="${!fn:contains(list_report_id,report.employee.id)}">
                                            <td>
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
                                            <td>
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
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}"
                                    pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td><pre><c:out value="${report.content}" /></pre>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${report.created_at}"
                                    pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${report.updated_at}"
                                    pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>承認状況</th>
                            <td><c:if
                                    test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/remand/manager' />">課長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id != report.employee.id}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when test="${report.approval == 2}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/remand/director' />">部長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
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
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when test="${report.approval == 1}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id}">
                                            <a href="<c:url value='/approval/manager' />">課長承認待ち</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id == report.employee.id}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/remand/director' />">部長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
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
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when test="${report.approval == 1}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when test="${report.approval == 2}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when test="${report.approval == 3}">
                                            部長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id}">
                                            <a href="<c:url value='/approval/director' />">部長承認待ち</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id == report.employee.id}">
                                            部長承認待ち
                                        </c:when>
                                        <c:when test="${report.approval == 6}">
                                            承認済み
                                        </c:when>
                                    </c:choose>
                                </c:if></td>
                        </tr>

                        <tr>
                            <th>承認履歴</th>
                            <td><a
                                href="<c:url value='/approval/history?id=${report.id}' />">詳細を見る</a></td>
                        </tr>

                        <tr>
                            <th><c:out value="${name}" /> <c:out value="${position}" />
                                <c:out value="${approval_status}" />コメント</th>
                            <td><c:out value="${comment}" /></td>
                        </tr>

                        <tr>
                            <th>新規コメント</th>
                            <td><c:if
                                    test="${(sessionScope.login_employee.admin_flag == 2 && report.approval == 2 && sessionScope.login_employee.id != report.employee.id) || (sessionScope.login_employee.admin_flag == 3 && report.approval == 4 && sessionScope.login_employee.id != report.employee.id)}">
                                    <textarea name="comment" rows="10" cols="50" form="form_id"></textarea>
                                </c:if></td>
                        </tr>

                        <tr>
                            <th>承認</th>
                            <td><c:if
                                    test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/submission/update' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${2}" class="ui primary button">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/manager/remand/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${2}" class="ui positive button">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${report.approval == 2}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/director/remand/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}" class="ui positive button">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${report.approval == 4}">
                                        </c:when>
                                        <c:when test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if test="${sessionScope.login_employee.admin_flag == 2}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/submission/update' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${2}" class="ui primary button">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${report.approval == 1}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 2}">
                                            <div style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/manager/approval/create' />"
                                                    id="form_id">
                                                    <input type="hidden" name="report_id" value="${report.id}" />
                                                    <button type="submit" name="submit" value="${4}" class="ui positive button">承認</button>
                                                    &nbsp; <input type="hidden" name="report_id"
                                                        value="${report.id}" />
                                                    <button type="submit" name="submit" value="${1}" class="ui negative button">差し戻し</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag == 2}">
                                            <div style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/manager/approval/create' />"
                                                    id="form_id">
                                                    <input type="hidden" name="report_id" value="${report.id}" />
                                                    <button type="submit" name="submit" value="${4}" class="ui positive button">承認</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/director/remand/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}" class="ui positive button">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${report.approval == 4}">
                                        </c:when>
                                        <c:when test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if test="${sessionScope.login_employee.admin_flag == 3}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/submission/update' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}" class="ui primary button">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${report.approval == 1}">
                                        </c:when>
                                        <c:when test="${report.approval == 2}">
                                        </c:when>
                                        <c:when test="${report.approval == 3}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 3}">
                                            <div style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/director/approval/create' />"
                                                    id="form_id">
                                                    <input type="hidden" name="report_id" value="${report.id}" />
                                                    <button type="submit" name="submit" value="${6}" class="ui positive button">承認</button>
                                                    &nbsp; <input type="hidden" name="report_id"
                                                        value="${report.id}" />
                                                    <button type="submit" name="submit" value="${3}" class="ui negative button">差し戻し</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag == 3}">
                                            <div style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/director/approval/create' />"
                                                    id="form_id">
                                                    <input type="hidden" name="report_id" value="${report.id}" />
                                                    <button type="submit" name="submit" value="${6}" class="ui positive button">承認</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if></td>
                        </tr>


                    </tbody>
                </table>
                <br>
                <c:choose>
                    <c:when
                        test="${sessionScope.login_employee.id != report.employee.id}">
                        <form method="POST" action="<c:url value='/likes/create' />">
                            <input type="hidden" name="report_id" value="${report.id}">
                            <div class="ui labeled button" tabindex="0">
                                <button type="submit" name="likes" value="${1}"
                                    class="ui button">
                                    <i class="heart icon"></i> Like
                                </button>
                                <c:if test="${report.likes != 0}">
                                    <a href="<c:url value='/likes?report_id=${report.id}' />"
                                        class="ui basic label"><c:out value="${report.likes}" /></a>
                                </c:if>
                                <c:if test="${report.likes == 0}">
                                    <div class="ui basic label">0</div>
                                </c:if>
                            </div>
                        </form>
                    </c:when>
                    <c:when
                        test="${sessionScope.login_employee.id == report.employee.id}">
                        <form method="POST" action="<c:url value='/likes/create' />">
                            <input type="hidden" name="report_id" value="${report.id}">
                            <div class="ui labeled button" tabindex="0">
                                <button type="submit" name="likes" value="${1}"
                                    class="ui disabled button">
                                    <i class="heart icon"></i> Like
                                </button>
                                <c:if test="${report.likes != 0}">
                                    <a href="<c:url value='/likes?report_id=${report.id}' />"
                                        class="ui basic label"><c:out value="${report.likes}" /></a>
                                </c:if>
                                <c:if test="${report.likes == 0}">
                                    <div class="ui basic label">0</div>
                                </c:if>
                            </div>
                        </form>
                    </c:when>
                </c:choose>
                <br>
                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <c:if test="${approval == 0 || approval == 1 || approval == 3}">
                        <p>
                            <a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a>
                        </p>
                    </c:if>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value="/reports" />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>