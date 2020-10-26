<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" />
            <br />
        </c:forEach>
    </div>
</c:if>
<label for="report_date">日付</label>
<br />
<input type="date" name="report_date"
    value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />
<br />
<br />
<label for="name">氏名</label>
<br />
<c:out value="${sessionScope.login_employee.name}" />
<br />
<br />
<label for="title">タイトル</label>
<br />
<input type="text" name="title" value="${report.title}" />
<br />
<br />
<label for="content">内容</label>
<br />
<textarea name="content" rows="10" cols="50">${report.content}</textarea>
<br />
<br />
<c:if
    test="${sessionScope.login_employee.admin_flag == 0 || sessionScope.login_employee.admin_flag == 1}">
    <c:choose>
        <c:when test="${approval == null || approval == 0}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${0}" class="ui primary button">下書き</button>
    &nbsp;
    <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${2}" class="ui positive button">提出</button>
        </c:when>
        <c:when test="${approval == 1}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${2}" class="ui positive button">再提出</button>
        </c:when>
        <c:when test="${approval == 3}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${4}" class="ui positive button">再提出</button>
        </c:when>
    </c:choose>
</c:if>

<c:if test="${sessionScope.login_employee.admin_flag == 2}">
    <c:choose>
        <c:when test="${approval == null || approval == 0}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${0}" class="ui primary button">下書き</button>
            &nbsp;
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${2}" class="ui positive button">提出</button>
        </c:when>
        <c:when test="${approval == 3}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${4}" class="ui positive button">再提出</button>
        </c:when>
    </c:choose>
</c:if>

<c:if test="${sessionScope.login_employee.admin_flag == 3}">
    <c:choose>
        <c:when test="${approval == null || approval == 0}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${0}" class="ui primary button">下書き</button>
            &nbsp;
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${4}" class="ui positive button">提出</button>
        </c:when>
        <c:when test="${approval == 3}">
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit" name="submit" value="${4}" class="ui positive button">再提出</button>
        </c:when>
    </c:choose>
</c:if>