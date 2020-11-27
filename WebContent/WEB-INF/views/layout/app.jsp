<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TipSwatch</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.7/dist/semantic.min.css">
    <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.7/dist/semantic.min.js"></script>
    <link href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/layout/_header.jsp"/>
<div class="ui hidden divider"></div>
<div id="content">
    <div class="ui container">${param.content}</div>
</div>
<div class="ui hidden divider"></div>
<c:import url="/WEB-INF/views/layout/_footer.jsp"/>
</body>
</html>