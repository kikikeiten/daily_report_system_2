<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui active dimmer">
    <div class="content">
        <h3>This idea has no advice history</h3>
        <p>If someone adds advice to this idea, it'll be displayed here.</p>
    </div>
</div>
<c:forEach begin="0" end="1" step="1">
    <div class="ui threaded comments">
        <div class="comment">
            <div class="content">
                <a class="author" href="#"></a>
                <div class="metadata">
                    <div class="date"></div>
                </div>
                <div class="text">&nbsp;</div>
            </div>
        </div>
    </div>
    <div class="ui divider"></div>
</c:forEach>