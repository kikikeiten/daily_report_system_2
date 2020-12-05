<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="ui text container">
            <c:choose>
                <c:when test="${member != null}">
                    <h2>Edit <c:out value="${member.name}"/>'s information</h2>
                    <div class="ui raised very padded container segment">
                        <p>Enter the password only if you want to change it.</p>
                        <form method="POST" action="<c:url value='/members/update'/>" class="ui fluid form">
                            <c:import url="_form.jsp"/>
                            <button type="submit" class="circular ui icon positive button">
                                <i class="far fa-paper-plane"></i>
                            </button>
                            <button type="button" onclick="confirmDestroy();" class="circular ui negative icon button">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </form>
                        <form method="POST" action="<c:url value='/members/destroy'/>">
                            <input type="hidden" name="_token" value="${_token}"/>
                        </form>
                        <script>
                            function confirmDestroy() {
                                if (confirm("Are you sure you want to delete ${member.name}?")) {
                                    document.forms[1].submit();
                                }
                            }
                        </script>
                    </div>
                    <button onclick="location.href='<c:url value='/members/show?id=${member.id}'/>'" class="circular ui icon button">
                        <i class="fas fa-long-arrow-alt-left"></i>
                    </button>
                </c:when>
                <c:otherwise>
                    <h2>The data you were looking for wasn't found.</h2>
                    <button onclick="location.href='<c:url value='/members'/>'" class="circular ui icon button">
                        <i class="fas fa-long-arrow-alt-left"></i>
                    </button>
                </c:otherwise>
            </c:choose>
        </div>
    </c:param>
</c:import>