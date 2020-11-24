<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <c:choose>
                <c:when test="${idea != null}">
                    <h2>
                        日報「
                        <c:out value="${idea.title}"/>
                        」の編集ページ
                    </h2>
                    <form method="POST" action="<c:url value='/ideas/update' />" class="ui fluid form">
                        <c:import url="_form.jsp"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <h2>
                        お探しのデータは見つかりませんでした。
                    </h2>
                </c:otherwise>
            </c:choose>
            <p>
                <a href="<c:url value='/ideas' />">
                    一覧に戻る
                </a>
            </p>
        </div>
    </c:param>
</c:import>