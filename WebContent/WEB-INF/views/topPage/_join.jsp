<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="ui info message">
    <i class="close icon"></i>
    <div style="display: inline-flex">
        <c:if test="${latestJoin == 0}">
            <form method="POST" action="<c:url value='joins/my/punch-in/create' />">
                <button type="submit" class="ui positive button">Work!</button>
            </form>
        </c:if>
        <c:if test="${latestJoin == 1}">
            <form method="POST" action="<c:url value='/joins/my/punch-out/create' />">
                <button type="submit" class="ui negative button">Finish!</button>
            </form>
        </c:if>
        <c:if test="${latestJoin == 2}">
            <form method="POST" action="<c:url value='joins/my/punch-in/create' />">
                <button type="submit" class="ui positive button">Work!</button>
            </form>
        </c:if>
        <c:if test="${latestJoin == 3}">
            <form method="POST" action="<c:url value='/joins/my/punch-in/create' />">
                <button type="submit" class="ui positive button">Work!</button>
            </form>
                    &nbsp;
                </c:if>
        <c:if test="${latestJoin == null}">
            <form method="POST" action="<c:url value='/joins/my/punch-in/create' />">
                <button type="submit" class="ui positive button">Work!</button>
            </form>
        </c:if>
    </div>
    <span id="RealtimeClockArea2" class="ui label"></span>
    <script>
                function set2fig(num) {
                    var ret;
                    if (num < 10) {
                        ret = "0" + num;
                    } else {
                        ret = num;
                    }
                    return ret;
                }
                function showClock2() {
                    var nowTime = new Date();
                    var nowHour = set2fig(nowTime.getHours());
                    var nowMin = set2fig(nowTime.getMinutes());
                    var nowSec = set2fig(nowTime.getSeconds());
                    var msg = "現在時刻は" + nowHour + ":" + nowMin + ":" + nowSec + " です。";
                    document.getElementById("RealtimeClockArea2").innerHTML = msg;
                }
                setInterval('showClock2()', 1000);
            </script>
    &nbsp;
    <div class="ui right floated buttons">
        <button class="ui button" onclick="location.href='<c:url value='/joins/my'/>'">My打刻履歴</button>
        <c:if test="${sessionScope.loginMember.role == 2 || sessionScope.loginMember.role == 3}">
            <button class="ui button" onclick="location.href='<c:url value='/joins/all'/>'">全打刻履歴</button>
        </c:if>
    </div>
</div>