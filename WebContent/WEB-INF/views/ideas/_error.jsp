<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="${errors != null}">
    <div class="ui message">
        There's an error in the input contents.
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"/>
        </c:forEach>
    </div>
</c:if>