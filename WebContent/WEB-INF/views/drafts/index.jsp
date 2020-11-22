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
              position: 'bottom right',
              showProgress: 'top',
              progressUp: true,
              className: {
                  toast: 'ui message'
              }
              });
            </script>
        </c:if>

        <h2>My drafts</h2>

        <div class="circular ui icon yellow mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.yellow.button')
        .popup({
            position : 'bottom center',
            content  : 'Unsubmitted draft'
        })
        ;
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getMyDraftsCount == 0}">

                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>
                                <c:out value="${sessionScope.login_employee.name}" />
                                さんの下書きの日報はありません。
                            </h3>
                            <p>下書きを作成するとここに表示されます。</p>
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
                        <c:forEach var="report" items="${getMyAllDrafts}" varStatus="status">

                            <div class="ui yellow card">
                                <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                </a>
                                <div class="extra content">
                                    <form method="POST" action="<c:url value='/submission/update' />" class="left floated">
                                        <c:choose>
                                            <c:when test="${sessionScope.login_employee.admin_flag != 3}">
                                                <button type="submit" name="submit" value="${2}" class="circular ui mini icon green button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" name="submit" value="${4}" class="circular ui mini icon blue button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                    <a class="right floated date" href="<c:url value='/employees/show?id=${report.employee.id}' />"> <c:out value="${report.employee.name}" />
                                    </a>
                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getMyDraftsCount - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/draft?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
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

        <div class="ui teal image label">
            My drafts
            <div class="detail">${getMyDraftsCount}</div>
        </div>

        <c:if test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
            <a href="<c:url value='/remand/manager' />" class="ui image label"> Manager remand <span class="detail"> ${getManagerRemandReportsCount} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 2}">
            <a href="<c:url value='/approval/manager' />" class="ui image label"> Manager approval <span class="detail"> ${getManagerApprovalReportsCount} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1 || sessionScope.login_employee.admin_flag == 2}">
            <a href="<c:url value='/remand/director' />" class="ui image label"> Director remand <span class="detail"> ${getDirectorRemandReportsCount} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.login_employee.admin_flag == 3}">
            <a href="<c:url value='/approval/director' />" class="ui image label"> Director approval <span class="detail"> ${getDirectorApprovalReportsCount} </span>
            </a>
        </c:if>

    </c:param>
</c:import>