<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        <h2>Swatch list</h2>
        <div class="ui section divider"></div>
        <c:choose>
            <c:when test="${getReportsCountButDrafts == 0}">
                <h3>日報はまだありません。</h3>
                <p>作成されるとここに表示されます。</p>
            </c:when>
            <c:otherwise>

                <div class="ui three stackable raised link cards">
                    <c:forEach var="report" items="${getAllReportsButDrafts}"
                        varStatus="status">

                        <div class="ui pink card">
                            <a class="content"
                                href="<c:url value='/reports/show?id=${report.id}' />"> <span
                                class="right floated"><fmt:formatDate
                                        value='${report.report_date}' pattern='MM / dd' /></span> <span
                                class="header"><c:out value="${report.title}" /></span> <span
                                class="description"> </span>
                            </a>
                            <div class="extra content">
                                <a class="left floated like"
                                    href="<c:url value='/likes?report_id=${report.id}' />"> <i
                                    class="far fa-heart"></i> <c:out value="${report.likes}" />
                                </a> <a class="right floated date"
                                    href="<c:url value='/employees/show?id=${report.employee.id}' />">
                                    <c:out value="${report.employee.name}" />
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="ui section divider"></div>
                <div class="ui label">日報件数 ${getReportsCountButDrafts}</div>&nbsp;
        <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1"
                        end="${((getReportsCountButDrafts - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}" />
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item" href="<c:url value='/reports?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                    </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <br>
        <br>
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