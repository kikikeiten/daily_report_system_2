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
        <h2>Director approval</h2>

        <div class="circular ui icon blue mini button" data-variation="inverted"></div>
        <script type="text/javascript">
        $('.blue.button')
        .popup({
            position : 'bottom center',
            content  : 'Waiting for director approval'
        })
        ;
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getDirectorApprovalReportsCount == 0}">
                    <h3>部長承認待ちの日報はありません。</h3>
                    <p>他の社員が部長に提出するとここに表示されます。</p>
                </c:when>
                <c:otherwise>

                    <div class="ui three stackable raised link cards">
                        <c:forEach var="report" items="${getAllDirectorApprovalReports}" varStatus="status">

                            <div class="ui blue card">
                                <a class="content" href="<c:url value='/reports/show?id=${report.id}' />"> <span class="right floated"><fmt:formatDate value='${report.report_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${report.title}" /></span> <span class="description"> </span>
                                </a>
                                <div class="extra content">

                                    <div class="ui icon buttons">
                                        <c:if test="${sessionScope.login_employee.id != report.employee.id}">
                                            <form method="POST" action="<c:url value='/director/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${6}" class="circular ui mini icon positive button">
                                                    <i class="fas fa-thumbs-up"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sessionScope.login_employee.id != report.employee.id && report.employee.admin_flag != 3}">
                                            &nbsp;
                                            <form method="POST" action="<c:url value='/director/approval/create' />">
                                                <input type="hidden" name="report_id" value="${report.id}" />
                                                <button type="submit" name="submit" value="${3}" class="circular ui mini icon negative button">
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

                    <div class="ui label">承認待ち件数 ${getDirectorApprovalReportsCount}</div>&nbsp;
                <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getDirectorApprovalReportsCount - 1) / 10) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/approval/director?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

        <br>
        <br>
        <button onclick="location.href='<c:url value='/reports/new' />'" class="ui positive button">新規日報</button>
        <br>
        <br>
        <p>
            <a href="<c:url value='/drafts' />">下書きの日報一覧（${getMyDraftsCount}）</a>
        </p>
    </c:param>
</c:import>