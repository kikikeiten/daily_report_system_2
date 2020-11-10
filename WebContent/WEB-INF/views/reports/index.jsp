<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <div class="circular ui icon olive mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.olive.button')
        .popup({
            position : 'bottom center',
            content  : 'Remanded to the manager'
        })
        ;
        </script>

        <div class="circular ui icon green mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.green.button')
        .popup({
            position : 'bottom center',
            content  : 'Waiting for manager approval'
        })
        ;
        </script>

        <div class="circular ui icon teal mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.teal.button')
        .popup({
            position : 'bottom center',
            content  : 'Remanded to the director'
        })
        ;
        </script>

        <div class="circular ui icon blue mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.blue.button')
        .popup({
            position : 'bottom center',
            content  : 'Waiting for director approval'
        })
        ;
        </script>

        <div class="circular ui icon violet mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.violet.button')
        .popup({
            position : 'bottom center',
            content  : 'Approved'
        })
        ;
        </script>

        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getReportsCountButDrafts == 0}">
                    <h3>日報はまだありません。</h3>
                    <p>作成されるとここに表示されます。</p>
                </c:when>
                <c:otherwise>

                    <div class="ui three stackable raised link cards">
                        <c:forEach var="report" items="${getAllReportsButDrafts}" varStatus="status">
                            <c:choose>

                                <c:when test="${report.approval == 1}">
                                    <div class="ui olive card">
                                        <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/likes?report_id=${report.id}' />"> <i class="far fa-heart"></i> <c:out value="${report.likes}" />
                                            </a> <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                            </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${report.approval == 2}">
                                    <div class="ui green card">
                                        <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/likes?report_id=${report.id}' />"> <i class="far fa-heart"></i> <c:out value="${report.likes}" />
                                            </a> <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                            </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${report.approval == 3}">
                                    <div class="ui teal card">
                                        <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/likes?report_id=${report.id}' />"> <i class="far fa-heart"></i> <c:out value="${report.likes}" />
                                            </a> <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                            </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${report.approval == 4}">
                                    <div class="ui blue card">
                                        <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/likes?report_id=${report.id}' />"> <i class="far fa-heart"></i> <c:out value="${report.likes}" />
                                            </a> <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                            </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${report.approval == 6}">
                                    <div class="ui violet card">
                                        <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like" href="<c:url value='/likes?report_id=${report.id}' />"> <i class="far fa-heart"></i> <c:out value="${report.likes}" />
                                            </a> <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                            </a>
                                        </div>
                                    </div>
                                </c:when>

                            </c:choose>
                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui centered mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getReportsCountButDrafts - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/reports?page=${i}' />"><c:out value="${i}" /></a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
        </div>

        <div class="ui teal image label">
            All swatches
            <div class="detail">${getReportsCountButDrafts}</div>
        </div>

        </c:otherwise>
        </c:choose>

        <div class="ui image label">
            My drafts
            <div class="detail">
                <a href="<c:url value='/drafts' />">${getMyDraftsCount}</a>
            </div>
        </div>

        <c:if test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
            <div class="ui image label">
                Manager remand
                <div class="detail">
                    <a href="<c:url value='/remand/manager' />">${getManagerRemandReportsCount}</a>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 2}">
            <div class="ui image label">
                Manager approval
                <div class="detail">
                    <a href="<c:url value='/approval/manager' />">${getManagerApprovalReportsCount}</a>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1 || sessionScope.login_employee.admin_flag == 2}">
            <div class="ui image label">
                Director remand
                <div class="detail">
                    <a href="<c:url value='/remand/director' />">${getDirectorRemandReportsCount}</a>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 3}">
            <div class="ui image label">
                Director approval
                <div class="detail">
                    <a href="<c:url value='/approval/director' />">${getDirectorApprovalReportsCount}</a>
                </div>
            </div>
        </c:if>

    </c:param>
</c:import>