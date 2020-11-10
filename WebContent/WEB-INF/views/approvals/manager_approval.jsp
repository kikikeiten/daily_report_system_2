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
        <h2>Manager approval</h2>

        <div class="ui raised very padded container segment">

        <c:choose>
            <c:when test="${getManagerApprovalReportsCount == 0}">
                <h3>課長承認待ちの日報はありません。</h3>
                <p>他の社員が課長に提出するとここに表示されます。</p>
            </c:when>
            <c:otherwise>

            <div class="ui three stackable raised link cards">
                        <c:forEach var="report" items="${getAllManagerApprovalReports}" varStatus="status">

                            <div class="ui blue card">
                                <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                </a>

                                <div class="extra content">

                                    <div class="ui icon buttons">
                                        <c:if test="${sessionScope.login_employee.id != report.employee.id}">
                                            <form method="POST" action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}" class="circular ui mini icon positive button">
                                                    <i class="fas fa-thumbs-up"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 2}">
                                            &nbsp;
                                            <form method="POST" action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${1}" class="circular ui mini icon negative button">
                                                    <i class="far fa-thumbs-down"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>

                                    <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                    </a>

                                </div>
                            </div>

                        </c:forEach>
                    </div>

                <table id="ma_report_list" class="ui celled striped table">
                    <tbody>
                        <tr>
                            <th class="ma_report_name">氏名</th>
                            <th class="ma_report_date">日付</th>
                            <th class="ma_report_title">タイトル</th>
                            <th class="ma_report_action">操作</th>
                            <th class="ma_report_like">いいね数</th>
                            <th class="ma_report_approval">承認</th>
                        </tr>
                        <c:forEach var="report" items="${getAllManagerApprovalReports}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="ma_report_name"><c:out
                                        value="${report.employee.name}" /></td>
                                <td class="ma_report_date"><fmt:formatDate
                                        value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                                <td class="ma_report_title">${report.title}</td>
                                <td class="ma_report_action"><a
                                    href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                                <c:choose>
                                    <c:when test="${report.likes == 0}">
                                        <td class="ma_report_likes"><c:out
                                                value="${report.likes}" /></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="ma_report_likes"><a
                                            href="<c:url value='/likes?report_id=${report.id}' />"><c:out
                                                    value="${report.likes}" /></a></td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="ma_report_approval"><div
                                        style="display: inline-flex">
                                        <c:if
                                            test="${sessionScope.login_employee.id != report.employee.id}">

                                            <form method="POST"
                                                action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${4}"
                                                    class="ui positive button">承認</button>
                                            </form>
                                        </c:if>
                                        <c:if
                                            test="${sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 2}">
                                            &nbsp;
                                            <form method="POST"
                                                action="<c:url value='/manager/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${1}"
                                                    class="ui negative button">差し戻し</button>
                                            </form>
                                        </c:if>
                                    </div></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">承認待ち件数 ${getManagerApprovalReportsCount}</div>&nbsp;
                <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1"
                        end="${((getManagerApprovalReportsCount - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}" />
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item"
                                    href="<c:url value='/approval/manager?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

        </div>

        <br>
        <br>
        <button onclick="location.href='<c:url value='/reports/new' />'"
            class="ui positive button">新規日報</button>
        <br>
        <br>
        <p>
            <a href="<c:url value='/drafts' />">下書きの日報一覧（${getMyDraftsCount}）</a>
        </p>
        <p>
            <a href="<c:url value='/remand/director' />">部長差し戻しの日報一覧（${getDirectorRemandReportsCount}）</a>
        </p>
    </c:param>
</c:import>