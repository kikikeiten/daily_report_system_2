<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <div class="ui text container">

            <c:choose>
                <c:when test="${member != null}">
                    <h2>${member.name}さんの従業員情報 編集ページ</h2>
                    <p>（パスワードは変更する場合のみ入力してください）</p>
                    <form method="POST" action="<c:url value='/members/update' />" class="ui fluid form">
                        <c:import url="_form.jsp"/>
                    </form>
                    <br>
                    <p>
                        <a href="#" onclick="confirmDestroy();">このメンバー情報を削除する</a>
                    </p>
                    <form method="POST" action="<c:url value='/members/destroy' />">
                        <input type="hidden" name="_token" value="${_token}"/>
                    </form>
                    <script>
                        function confirmDestroy() {
                            if (confirm("本当に削除してよろしいですか？")) {
                                document.forms[1].submit();
                            }
                        }
                    </script>
                </c:when>
                <c:otherwise>
                    <h2>お探しのデータは見つかりませんでした。</h2>
                </c:otherwise>
            </c:choose>
            <p>
                <a href="<c:url value='/members' />">一覧に戻る</a>
            </p>
        </div>
    </c:param>
</c:import>