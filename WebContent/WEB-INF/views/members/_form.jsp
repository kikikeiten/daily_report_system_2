<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${errors != null}">
    <div class="ui message">
        入力内容にエラーがあります。<br/>
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"/>
            <br/>
        </c:forEach>
    </div>
</c:if>

<label for="code">メンバーID</label>
<br/>
<input type="text" name="code" value="${member.code}"/>
<br/>
<br/>
<label for="name">氏名</label>
<br/>
<input type="text" name="name" value="${member.name}"/>
<br/>
<br/>
<label for="password">パスワード</label>
<br/>
<input type="password" name="password"/>
<br/>
<br/>
<label for="role">役割</label>
<br/>
<select name="role" class="ui selection dropdown">
    <option value="0"
            <c:if test="${member.role == 0}"> selected</c:if>>Associate
    </option>
    <option value="1"
            <c:if test="${member.role == 1}"> selected</c:if>>Administrator
    </option>
    <option value="2"
            <c:if test="${member.role == 2}"> selected</c:if>>Manager
    </option>
    <option value="3"
            <c:if test="${member.role == 3}"> selected</c:if>>Director
    </option>
</select>
<br/>
<br/>
<input type="hidden" name="_token" value="${_token}"/>
<button type="submit" class="ui positive button">投稿</button>