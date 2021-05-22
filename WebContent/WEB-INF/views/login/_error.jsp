<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${hasError}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        Your member ID or password is incorrect.
    </div>
</c:if>