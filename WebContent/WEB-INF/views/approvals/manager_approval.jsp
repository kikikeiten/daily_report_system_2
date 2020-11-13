<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <div class="circular ui icon green mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.green.button')
        .popup({
            position : 'bottom center',
            content  : 'Waiting for manager approval'
        })
        ;
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getManagerApprovalReportsCount == 0}">

                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>課長承認待ちの日報はありません。</h3>
                            <p>他の社員が課長に提出するとここに表示されます。</p>
                        </div>
                    </div>
                    <div class="ui three stackable raised link cards">

                        <c:forEach begin="0" end="5" step="1">
                            <div class="ui card">
                                <a class="content" href=""> <span class="right floated"></span> <span class="header"></span> <span class="description"> </span>
                                </a>
                                <div class="extra content">
                                    <button class="circular ui mini icon button">
                                        <i class="far fa-paper-plane"></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </c:when>

                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="report" items="${getAllManagerApprovalReports}" varStatus="status">

                            <div class="ui green card">
                                <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                </a>

                                <div class="extra content">

                                    <c:if test="${sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 2}">
                                            &nbsp;
                                            <form method="POST" action="<c:url value='/manager/approval/create' />" class="left floated">
                                            <input type="hidden" name="report_id" value="${report.id}" />
                                            <button type="submit" name="submit" value="${1}" class="circular ui mini icon olive button">
                                                <i class="fas fa-comment-medical"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${sessionScope.login_employee.id != report.employee.id}">
                                        <form method="POST" action="<c:url value='/manager/approval/create' />" class="left floated">
                                            <input type="hidden" name="report_id" value="${report.id}" />
                                            <button type="submit" name="submit" value="${4}" class="circular ui mini icon blue button">
                                                <i class="far fa-paper-plane"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                    </a>

                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getManagerApprovalReportsCount - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/approval/manager?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>

        </div>

        <a href="<c:url value='/' />" class="ui image label"> My swatches <span class="detail"> ${reports_count} </span>
        </a>

        <a href="<c:url value='/reports' />" class="ui image label"> All swatches <span class="detail"> ${getReportsCountButDrafts} </span>
        </a>

        <a href="<c:url value='/drafts' />" class="ui image label"> My drafts <span class="detail"> ${getMyDraftsCount} </span>
        </a>

        <div class="ui teal image label">
            Manager approval
            <div class="detail">${getManagerApprovalReportsCount}</div>
        </div>

        <a href="<c:url value='/remand/director' />" class="ui image label"> Director remand <span class="detail"> ${getDirectorRemandReportsCount} </span>
        </a>

    </c:param>
</c:import>