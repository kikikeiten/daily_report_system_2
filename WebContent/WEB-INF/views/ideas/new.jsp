<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <h2>
                New swatch
            </h2>
            <div class="ui raised very padded container segment">
                <form method="POST" action="<c:url value='/ideas/create'/>" class="ui fluid form">
                    <c:import url="_form.jsp"/>
                </form>
            </div>
        </div>
    </c:param>
</c:import>