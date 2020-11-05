<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div class="ui error message">
                <i class="close icon"></i>
                <script>
                    $('.message .close').on('click', function() {
                        $(this).closest('.message').transition('fade');
                    });
                </script>
                社員番号かパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <script>
            $('body')
            .toast({
              class: 'success',
              message: "${flush}",
              showProgress: 'top',
              progressUp: true,
              className: {
                  toast: 'ui message'
              }
            })
          ;
            </script>
        </c:if>
        <br>
        <br>
        <div class="ui raised very padded text container segment">
            <h2>ログイン</h2>
            <form class="ui fluid form" method="POST"
                action="<c:url value='/login' />">
                <label for="code">社員番号</label><br /> <input type="text" name="code"
                    value="${code}" /> <br /> <br /> <label for="password">パスワード</label><br />
                <input type="password" name="password" /> <br /> <br /> <input
                    type="hidden" name="_token" value="${_token}" />
                <button class="ui primary button" type="submit">ログイン</button>
            </form>
        </div>
        <br>
        <br>
    </c:param>
</c:import>