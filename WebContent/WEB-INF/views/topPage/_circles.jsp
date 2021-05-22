<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="<c:url value='/drafts' />" class="circular ui icon yellow mini button" data-variation="inverted"></a>
<script>
    $('.yellow.button').popup({
        position: 'bottom center',
        content: 'Unsubmitted draft'
    });
</script>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
    <a href="<c:url value='/advice/manager' />" class="circular ui icon olive mini button" data-variation="inverted"></a>
    <script>
        $('.olive.button').popup({
            position: 'bottom center',
            content: 'Remanded to the manager'
        });
    </script>
</c:if>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1 || sessionScope.loginMember.role == 2}">
    <div class="circular ui icon green mini button" data-variation="inverted"></div>
    <script>
        $('.green.button').popup({
            position: 'bottom center',
            content: 'Waiting for manager approval'
        });
    </script>
</c:if>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1 || sessionScope.loginMember.role == 2}">
    <a href="<c:url value='/advice/director' />" class="circular ui icon teal mini button" data-variation="inverted"></a>
    <script>
        $('.teal.button').popup({
            position: 'bottom center',
            content: 'Remanded to the director'
        });
    </script>
</c:if>
<div class="circular ui icon blue mini button" data-variation="inverted"></div>
<script>
    $('.blue.button').popup({
        position: 'bottom center',
        content: 'Waiting for director approval'
    });
</script>
<div class="circular ui icon violet mini button" data-variation="inverted"></div>
<script>
    $('.violet.button').popup({
        position: 'bottom center',
        content: 'Approved'
    });
</script>