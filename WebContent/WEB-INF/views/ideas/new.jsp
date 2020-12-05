<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <h2>Add a new idea</h2>
            <div class="ui raised very padded container segment">
                <form method="POST" action="<c:url value='/ideas/create'/>" class="ui fluid form">
                    <c:import url="_form.jsp"/>
                </form>
            </div>
            <button onclick="location.href='<c:url value='/ideas'/>'" class="circular ui icon button">
                <i class="fas fa-long-arrow-alt-left"></i>
            </button>
        </div>
    </c:param>
</c:import>