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
                    <h3>課長承認待ちの日報はありません。</h3>
                    <p>他の社員が課長に提出するとここに表示されます。</p>
                </c:when>
                <c:otherwise>

                    <div class="ui three stackable raised link cards">
                        <c:forEach var="report" items="${getAllManagerApprovalReports}" varStatus="status">

                            <div class="ui green card">
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

                <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getManagerApprovalReportsCount - 1) / 10) + 1}" step="1">
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

        <div class="ui image label">
            All swatches
            <div class="detail">
                <a href="<c:url value='/reports' />"> ${getReportsCountButDrafts} </a>
            </div>
        </div>


        <div class="ui image label">
            My drafts
            <div class="detail">
                <a href="<c:url value='/drafts' />">${getMyDraftsCount}</a>
            </div>
        </div>

        <div class="ui teal image label">
            Manager approval
            <div class="detail">${getManagerApprovalReportsCount}</div>
        </div>

        <div class="ui image label">
            Director remand
            <div class="detail">
                <a href="<c:url value='/remand/director' />">${getDirectorRemandReportsCount}</a>
            </div>
        </div>

    </c:param>
</c:import>