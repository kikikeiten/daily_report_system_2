<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui active dimmer">
    <div class="content">
        <h3>There's no favorite in <c:out value="${idea.member.name}"/>'s idea</h3>
        <p>Will be displayed here when favored</p>
    </div>
</div>
<div class="ui three stackable raised link cards">
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
</div>