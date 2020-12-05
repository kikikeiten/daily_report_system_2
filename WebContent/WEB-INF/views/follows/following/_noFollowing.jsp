<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui active dimmer">
    <div class="content">
        <h3>You haven't followed anyone yet.</h3>
        <p>If you follow someone, your followers will appear here.</p>
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
