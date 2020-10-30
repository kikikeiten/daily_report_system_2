<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>My打刻履歴</h2>
        <c:choose>
            <c:when test="${My打刻カウント == 0}">
                <h3>打刻はまだありません。</h3>
                <p>打刻するとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="my_attendance_list" class="ui celled striped table">
                    <tbody>
                        <tr>
                            <th class="my_attendance_date">日付</th>
                            <th class="my_attendance_punchIn">出勤時刻</th>
                            <th class="my_attendance_punchOut">退勤時刻</th>
                            <th class="my_attendance_working">勤務時間</th>
                        </tr>
                        <c:forEach var="attendance" items="${My打刻リスト}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="my_attendance_date"><c:out
                                        value="${attendance.attendance_date}" /></td>
                                <td class="my_attendance_punchIn"><fmt:formatDate
                                        value='${attendance.punch_in}' pattern='HH:mm' /></td>
                                <td class="my_attendance_punchOut"><fmt:formatDate
                                        value='${attendance.punch_out}' pattern='HH:mm' /></td>
                                <td class="my_attendance_working"><fmt:formatDate
                                        value='${attendance.working}' pattern='HH:mm' /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">日数 ${My打刻カウント}</div>&nbsp;
        <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1" end="${((My打刻カウント - 1) / 10) + 1}"
                        step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <div class="item active">
                                    <c:out value="${i}" />
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="item"
                                    href="<c:url value='/attendance/my?page=${i}' />"><c:out
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
            <a href="<c:url value='/' />">トップページに戻る</a>
        </p>
    </c:param>
</c:import>