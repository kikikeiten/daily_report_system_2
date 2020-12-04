<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <h2>Add a new member</h2>
            <div class="ui raised very padded container segment">
                <form method="POST" action="<c:url value='/members/create' />" class="ui fluid form">
                    <c:import url="_form.jsp"/>
                    <button type="submit" class="circular ui icon positive button">
                        <i class="far fa-paper-plane"></i>
                    </button>
                </form>
            </div>
            <button onclick="location.href='<c:url value='/members'/>'" class="circular ui icon button">
                <i class="fas fa-long-arrow-alt-left"></i>
            </button>
        </div>
    </c:param>
</c:import>