<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div class="ui raised very padded text container segment">
        <h2>退勤時刻の修正</h2>
        <form class="ui fluid form" method="POST"
            action="<c:url value='/leave/create' />">
            <label for="leave_time">パスワード</label><br /> <input type="text"
                name="leave_time" placeholder="HHmm" /> <br /> <br /> <input
                type="hidden" name="_token" value="${_token}" />
            <button class="ui primary button" type="submit">修正</button>
        </form>
    </div>
</body>
</html>