<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${errors != null}">
    <div class="ui message">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" />
            <br />
        </c:forEach>
    </div>
</c:if>
<label for="code">社員番号</label>
<br />
<input type="text" name="code" value="${employee.code}" />
<br />
<br />
<label for="name">氏名</label>
<br />
<input type="text" name="name" value="${employee.name}" />
<br />
<br />
<label for="password">パスワード</label>
<br />
<input type="password" name="password" />
<br />
<br />
<label for="admin_flag">権限</label>
<br />
<select name="admin_flag">
    <option value="0"
        <c:if test="${employee.admin_flag == 0}"> selected</c:if>>一般</option>
    <option value="1"
        <c:if test="${employee.admin_flag == 1}"> selected</c:if>>管理者</option>
    <option value="2"
        <c:if test="${employee.admin_flag == 2}"> selected</c:if>>課長</option>
    <option value="3"
        <c:if test="${employee.admin_flag == 3}"> selected</c:if>>部長</option>
</select>
<br />
<br />
<input type="hidden" name="_token" value="${_token}" />
<button type="submit" class="ui positive button">投稿</button>