<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <h2>日報 新規登録ページ</h2>
            <form method="POST" action="<c:url value='/reports/create' />"
                class="ui fluid form">
                <c:import url="_form.jsp" />
            </form>
            <br>
            <p>
                <a href="<c:url value='/reports' />">一覧に戻る</a>
            </p>
        </div>
    </c:param>
</c:import>