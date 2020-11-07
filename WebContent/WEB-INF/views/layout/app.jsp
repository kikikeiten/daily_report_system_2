<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>TipSwatch</title>
<link rel="stylesheet" href="<c:url value='/css/reset.css' />">
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<script
    src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
    href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.7/dist/semantic.min.css">
<script
    src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.7/dist/semantic.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <c:if test="${attendance_flag == 1}">
            <div class="ui top right attached green label">勤務中</div>
        </c:if>
        <br>
        <div class="ui borderless container menu">
            <a href="<c:url value='/' />" class="item"><b>TipSwatch</b></a>
            <c:if test="${sessionScope.login_employee != null}">
                <c:if
                    test="${sessionScope.login_employee.admin_flag == 1 || sessionScope.login_employee.admin_flag == 2 || sessionScope.login_employee.admin_flag == 3}">
                    <a href="<c:url value='/employees' />" class="item">従業員管理</a>&nbsp;
                    </c:if>
                <a href="<c:url value='/reports' />" class="item">日報管理</a>&nbsp;
                    <a href="<c:url value='/timeline' />" class="item">タイムライン</a>&nbsp;
                    <a href="<c:url value='/following' />" class="item"><b><c:out
                            value="${getMyFollowingCount}" /></b>フォロー中</a>&nbsp;
                    <a href="<c:url value='/follower' />" class="item"><b><c:out
                            value="${getMyFollowerCount}" /></b>フォロワー</a>&nbsp;
                    <c:if
                    test="${sessionScope.login_employee.admin_flag == 2}">
                    <a href="<c:url value='/approval/manager' />" class="item">承認待ち<b><c:out
                                value="${getManagerApprovalReportsCount}" />件</b></a>&nbsp;
                    </c:if>
                <c:if test="${sessionScope.login_employee.admin_flag == 3}">
                    <a href="<c:url value='/approval/director' />" class="item">承認待ち<b><c:out
                                value="${getDirectorApprovalReportsCount}" />件</b></a>&nbsp;
                    </c:if>
            </c:if>

            <c:if test="${sessionScope.login_employee != null}">
                <div class="item right">
                    <c:out value="${sessionScope.login_employee.name}" />
                    <c:if test="${sessionScope.login_employee.admin_flag == 0}">
                        <div class="ui large label">社員</div>
                    </c:if>
                    <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                        <div class="ui large label">管理者</div>
                    </c:if>
                    <c:if test="${sessionScope.login_employee.admin_flag == 2}">
                        <div class="ui large label">課長</div>
                    </c:if>
                    <c:if test="${sessionScope.login_employee.admin_flag == 3}">
                        <div class="ui large label">部長</div>
                    </c:if>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button onclick="location.href='<c:url value='/logout' />'"
                        class="ui button" class="item right">ログアウト</button>
                </div>
            </c:if>
        </div>
    </div>
    <br>
    <div id="content">
        <div class="ui container">${param.content}</div>
    </div>
    <div id="footer">© 2020 KIKI.</div>
</body>
</html>