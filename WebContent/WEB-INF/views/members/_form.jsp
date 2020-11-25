<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div class="ui message">
        入力内容にエラーがあります。<br/>
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"/>
        </c:forEach>
    </div>
</c:if>
<label for="code">メンバーID</label>
<input type="text" name="code" value="${member.code}"/>
<label for="name">氏名</label>
<input type="text" name="name" value="${member.name}"/>
<label for="password">パスワード</label>
<input type="password" name="password"/>
<label for="role">役割</label>
<select name="role" class="ui selection dropdown">
    <option value="0" <c:if test="${member.role == 0}">selected</c:if>>Associate</option>
    <option value="1"<c:if test="${member.role == 1}"> selected</c:if>>Administrator</option>
    <option value="2"<c:if test="${member.role == 2}"> selected</c:if>>Manager</option>
    <option value="3"<c:if test="${member.role == 3}"> selected</c:if>>Director</option>
</select>
<input type="hidden" name="_token" value="${_token}"/>
<button type="submit" class="ui positive button">投稿</button>