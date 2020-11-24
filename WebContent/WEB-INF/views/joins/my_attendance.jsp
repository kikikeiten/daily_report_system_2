<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>My打刻履歴</h2>
        <c:choose>
            <c:when test="${getMyAttendancesCount == 0}">
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
                        <c:forEach var="attendance" items="${getMyAllAttendances}"
                            varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="my_attendance_date"><fmt:formatDate
                                        value='${attendance.attendance_date}' pattern='MM / dd' />
                                    <c:if test="${attendance.attendance_flag == 1}">
                                        <div class="ui green label">勤務中</div>
                                    </c:if>
                                    <c:if test="${attendance.attendance_flag == 2}">
                                        <div class="ui red label">打刻忘れ</div>
                                    </c:if>
                                    <c:if test="${attendance.attendance_flag == 3}">
                                        <div class="ui orange label">修正済み</div>
                                    </c:if>
                                    </td>
                                <td class="my_attendance_punchIn"><fmt:formatDate
                                        value='${attendance.punch_in}' pattern='HH : mm' /></td>
                                <td class="my_attendance_punchOut"><fmt:formatDate
                                        value='${attendance.punch_out}' pattern='HH : mm' /> <c:if
                                        test="${attendance.attendance_flag == 2}">
                                        未登録</c:if></td>
                                <td class="my_attendance_working"><fmt:formatDate
                                        value='${attendance.working}' pattern='HH : mm' /> <c:if
                                        test="${attendance.attendance_flag == 2}">
                                        未登録</c:if></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="ui label">履歴数 ${getMyAttendancesCount}</div>&nbsp;
        <div class="ui mini pagination menu">
                    <c:forEach var="i" begin="1"
                        end="${((getMyAttendancesCount - 1) / 10) + 1}" step="1">
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