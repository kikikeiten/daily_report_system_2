<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui active dimmer">
    <div class="content">
        <h3>There is no idea waiting for the manager's review.</h3>
        <p>If another member submits an idea to the manager, it will be displayed here.</p>
    </div>
</div>
<div class="ui three stackable raised link cards">
    <c:forEach begin="0" end="5" step="1">
        <div class="ui card">
            <a class="content" href="">
                <span class="right floated"></span>
                <span class="header"></span>
                <span class="description"></span>
            </a>
            <div class="extra content">
                <button class="circular ui mini icon button">
                    <i class="far fa-paper-plane"></i>
                </button>
            </div>
        </div>
    </c:forEach>
</div>