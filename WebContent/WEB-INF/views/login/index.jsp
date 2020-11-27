<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div class="ui error message">
                <i class="close icon"></i>
                <script>
                    $('.message .close').on('click', function () {
                        $(this).closest('.message').transition('fade');
                    });
                </script>
                社員番号かパスワードが間違っています。
            </div>
        </c:if>
        <div class="ui hidden divider"></div>
        <div class="ui raised very padded text container segment">
            <h2>Login</h2>
            <form class="ui fluid form" method="POST" action="<c:url value='/login' />">
                <div class="field">
                    <label for="code">User id</label>
                    <div class="ui left icon input">
                        <input type="text" name="code" value="${code}" placeholder="Username"/>
                        <i class="user icon"></i>
                    </div>
                </div>
                <div class="field">
                    <label for="password">Password</label>
                    <div class="ui left icon input">
                        <input type="password" name="password"/>
                        <i class="lock icon"></i> <input type="hidden" name="_token" value="${_token}"/>
                    </div>
                </div>
                <button class="ui primary button" type="submit">Enter</button>
            </form>
        </div>
    </c:param>
</c:import>