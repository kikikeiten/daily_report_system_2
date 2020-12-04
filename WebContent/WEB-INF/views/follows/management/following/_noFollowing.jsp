<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui active dimmer">
    <div class="content">
        <h3><c:out value="${member.name}"/> hasn't followed anyone yet.</h3>
        <p>If <c:out value="${member.name}"/> follows, your followers will be displayed here</p>
    </div>
</div>
<div class="ui text container">
    <table class="ui padded single line striped table">
        <c:forEach begin="0" end="3" step="1">
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </c:forEach>
    </table>
</div>