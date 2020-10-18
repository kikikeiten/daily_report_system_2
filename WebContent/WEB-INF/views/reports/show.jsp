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
                <table>
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
                                            <td class="follow">
                                                <form method="POST"
                                                    action="<c:url value='/follow/create' />">
                                                    <button type="submit" name="following" value="${report.id}">フォロー</button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="follow">
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
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate
                                    value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>

                        <tr>
                            <th>内容</th>
                            <td><pre><c:out value="${report.content}" /></pre></td>
                        </tr>

                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate
                                    value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>

                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate
                                    value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>

                        <tr>
                            <th>いいね数</th>
                            <c:choose>
                                <c:when test="${report.likes == 0}">
                                    <td class="report_likes"><c:out
                                            value="${report.likes}" /></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="report_likes"><a
                                        href="<c:url value='/likes/index?report_id=${report.id}' />"><c:out
                                                value="${report.likes}" /></a></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>

                        <tr>
                            <th>承認状況</th>
                            <td><c:if
                                    test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/remand/manager' />">課長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id != report.employee.id}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/remand/director' />">部長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
                                            部長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4}">
                                            部長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                            承認済み
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if
                                    test="${sessionScope.login_employee.admin_flag == 2}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id}">
                                            <a
                                                href="<c:url value='/approval/manager' />">課長承認待ち</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id == report.employee.id}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/remand/director' />">部長差し戻し</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id != report.employee.id}">
                                            部長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4}">
                                            部長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                            承認済み
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if
                                    test="${sessionScope.login_employee.admin_flag == 3}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <a
                                                href="<c:url value='/drafts' />">下書き</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id != report.employee.id}">
                                            下書き
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1}">
                                            課長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2}">
                                            課長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3}">
                                            部長差し戻し
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id}">
                                            <a
                                                href="<c:url value='/approval/director' />">部長承認待ち</a>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id == report.employee.id}">
                                            部長承認待ち
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                            承認済み
                                        </c:when>
                                    </c:choose>
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
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${2}">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/manager/remand/create' />">
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${2}">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/director/remand/create' />">
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${4}">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if
                                    test="${sessionScope.login_employee.admin_flag == 2}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/submission/update' />">
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${2}">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 2}">
                                            <div
                                                style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/manager/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${4}">承認</button>
                                                </form>
                                                &nbsp;
                                                <form method="POST"
                                                    action="<c:url value='/manager/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${1}">差し戻し</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag == 2}">
                                            <div
                                                style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/manager/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${4}">承認</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/director/remand/create' />">
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${4}">再提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if> <c:if
                                    test="${sessionScope.login_employee.admin_flag == 3}">
                                    <c:choose>
                                        <c:when
                                            test="${report.approval == 0 && sessionScope.login_employee.id == report.employee.id}">
                                            <form method="POST"
                                                action="<c:url value='/submission/update' />">
                                                <input type="hidden"
                                                    name="report_id" value="${report.id}" />
                                                <button type="submit"
                                                    name="submit" value="${4}">提出</button>
                                            </form>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 1}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 2}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 3}">
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 3}">
                                            <div
                                                style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/director/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${6}">承認</button>
                                                </form>
                                                &nbsp;
                                                <form method="POST"
                                                    action="<c:url value='/director/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${3}">差し戻し</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 4 && sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag == 3}">
                                            <div
                                                style="display: inline-flex">
                                                <form method="POST"
                                                    action="<c:url value='/director/approval/create' />">
                                                    <input type="hidden"
                                                        name="report_id" value="${report.id}" />
                                                    <button
                                                        type="submit" name="submit" value="${6}">承認</button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when
                                            test="${report.approval == 6}">
                                        </c:when>
                                    </c:choose>
                                </c:if></td>
                        </tr>


                    </tbody>
                </table>
                <br>
                <c:if
                    test="${sessionScope.login_employee.id != report.employee.id}">
                    <form method="POST"
                        action="<c:url value='/likes/create' />">
                        <input type="hidden" name="report_id"
                            value="${report.id}">
                        <button type="submit" name="likes" value="${1}">いいね！</button>
                    </form>
                </c:if>
                <c:if
                    test="${sessionScope.login_employee.id == report.employee.id}">
                    <c:if
                        test="${approval == 0 || approval == 1 || approval == 3}">
                        <p>
                            <a
                                href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a>
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