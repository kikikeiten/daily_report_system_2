<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div class="ui message">
        入力内容にエラーがあります。
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}"/>
        </c:forEach>
    </div>
</c:if>
<div class="field">
    <label for="createdDate">Date</label>
    <input type="date" name="createdDate" value="<fmt:formatDate value='${idea.createdDate}' pattern='yyyy-MM-dd' />"/>
</div>
<div class="field">
    <label for="name">Name</label>
    <c:out value="${sessionScope.loginMember.name}"/>
</div>
<div class="field">
    <label for="title">Title</label>
    <input type="text" name="title" value="${idea.title}"/>
</div>
<div class="field">
    <label for="content">Content</label>
    <textarea name="content" rows="10" cols="50"><c:out value="${idea.content}"/></textarea>
</div>
<c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
    <c:choose>
        <c:when test="${reviewStatus == null || reviewStatus == 0}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${0}" class="circular ui icon yellow button">
                <i class="fas fa-save"></i>
            </button>
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
        <c:when test="${reviewStatus == 1}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
        <c:when test="${reviewStatus == 3}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
    </c:choose>
</c:if>
<c:if test="${sessionScope.loginMember.role == 2}">
    <c:choose>
        <c:when test="${reviewStatus == null || reviewStatus == 0}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${0}" class="circular ui icon yellow button">
                <i class="fas fa-save"></i>
            </button>
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
        <c:when test="${reviewStatus == 3}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
    </c:choose>
</c:if>
<c:if test="${sessionScope.loginMember.role == 3}">
    <c:choose>
        <c:when test="${reviewStatus == null || reviewStatus == 0}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${0}" class="circular ui icon yellow button">
                <i class="fas fa-save"></i>
            </button>
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
        <c:when test="${reviewStatus == 3}">
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                <i class="far fa-paper-plane"></i>
            </button>
        </c:when>
    </c:choose>
</c:if>