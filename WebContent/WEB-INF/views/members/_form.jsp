<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="_error.jsp"/>
<div class="field">
    <label for="code">Member ID</label>
    <input type="text" name="code" value="${member.code}"/>
</div>
<div class="field">
    <label for="name">Name</label>
    <input type="text" name="name" value="${member.name}"/>
</div>
<div class="field">
    <label for="password">Password</label>
    <input type="password" name="password"/>
</div>
<div class="grouped fields">
    <label for="role">Role</label>
    <div class="field">
        <div class="ui radio checkbox">
            <input value="0" type="radio" name="role" <c:if test="${member.role == 0 || member.role == null}">checked="checked"</c:if>>
            <label>Associate</label>
        </div>
    </div>
    <div class="field">
        <div class="ui radio checkbox">
            <input value="1" type="radio" name="role" <c:if test="${member.role == 1}">checked="checked"</c:if>>
            <label>Administrator</label>
        </div>
    </div>
    <div class="field">
        <div class="ui radio checkbox">
            <input value="2" type="radio" name="role" <c:if test="${member.role == 2}">checked="checked"</c:if>>
            <label>Manager</label>
        </div>
    </div>
    <div class="field">
        <div class="ui radio checkbox">
            <input value="3" type="radio" name="role" <c:if test="${member.role == 3}">checked="checked"</c:if>>
            <label>Director</label>
        </div>
    </div>
</div>
<input type="hidden" name="_token" value="${_token}"/>