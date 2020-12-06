<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${idea != null}">
                <h2>Idea "<c:out value="${idea.title}"/>" detail</h2>
                <div class="ui raised very padded container segment">
                    <div class="ui text container">
                        <div class="ui cards">
                            <div class="card">
                                <div class="content">
                                    <div class="header">
                                        <a href="<c:url value="/members/show?id=${idea.member.id}"/> ">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                    </div>
                                    <div class="meta">
                                        <c:if test="${sessionScope.loginMember.role == 0}">
                                            <div class="ui tiny label">
                                                Associate
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.loginMember.role == 1}">
                                            <div class="ui tiny label">
                                                Astrator
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.loginMember.role == 2}">
                                            <div class="ui tiny label">
                                                Manager
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.loginMember.role == 3}">
                                            <div class="ui tiny label">
                                                Director
                                            </div>
                                        </c:if>
                                        <div class="ui right floated">
                                            <c:choose>
                                                <c:when test="${sessionScope.loginMember.id != idea.member.id}">
                                                    <c:choose>
                                                        <c:when test="${!fn:contains(followIdea,idea.member.id)}">
                                                            <td>
                                                                <form method="POST" action="<c:url value='/following/create/idea'/>">
                                                                    <button class="circular ui icon green basic button" type="submit" name="followedId" value="${idea.id}">
                                                                        <i class="fas fa-user-plus"></i>
                                                                    </button>
                                                                </form>
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>
                                                                <form method="POST" action="<c:url value='/following/destroy/idea' />">
                                                                    <button class="circular ui icon red basic button" type="submit" name="memberId" value="${idea.id}">
                                                                        <i class="fas fa-user-minus"></i>
                                                                    </button>
                                                                </form>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    You
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="description">
                                        Elliot Fu is a film-maker from New York.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="ui celled striped table">
                            <thead>
                            <tr>
                                <th colspan="3">
                                    <c:out value="${idea.title}"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="collapsing">
                                    Date
                                </td>
                                <td><fmt:formatDate value="${idea.createdDate}" pattern="yyyy / MM / dd"/></td>
                            </tr>
                            <tr>
                                <td class="collapsing">
                                    Idea
                                </td>
                                <td>
                                    <pre><c:out value="${idea.content}"/></pre>
                                </td>
                            </tr>
                            <tr>
                                <td class="collapsing">
                                    Latest advice
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${advice == null}">
                                            No advice.
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${advice}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="collapsing">
                                    Status
                                </td>
                                <td>
                                    <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
                                        <c:choose>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">Draft</a></c:when>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}">Draft</c:when>
                                            <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/manager' />">With manager's advice</a></c:when>
                                            <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id != idea.member.id}">With manager's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 2}">Waiting for manager's review</c:when>
                                            <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/director' />">With director's advice</a></c:when>
                                            <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id != idea.member.id}">With director's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 4}">Waiting for director's review</c:when>
                                            <c:when test="${idea.reviewStatus == 6}">Approved !</c:when>
                                        </c:choose>
                                    </c:if>
                                    <c:if test="${sessionScope.loginMember.role == 2}">
                                        <c:choose>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">Draft</a></c:when>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}">Draft</c:when>
                                            <c:when test="${idea.reviewStatus == 1}">With manager's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id}"><a href="<c:url value='/reviews/manager' />">Waiting for manager's review</a></c:when>
                                            <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id == idea.member.id}">Waiting for manager's review</c:when>
                                            <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/director' />">With director's advice</a></c:when>
                                            <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id != idea.member.id}">With director's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 4}">Waiting for director's review</c:when>
                                            <c:when test="${idea.reviewStatus == 6}">Approved!</c:when>
                                        </c:choose>
                                    </c:if>
                                    <c:if test="${sessionScope.loginMember.role == 3}">
                                        <c:choose>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">Draft</a></c:when>
                                            <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}">Draft</c:when>
                                            <c:when test="${idea.reviewStatus == 1}">With manager's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 2}">Waiting for manager's review</c:when>
                                            <c:when test="${idea.reviewStatus == 3}">With director's advice</c:when>
                                            <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id}"><a href="<c:url value='/reviews/director' />">Waiting for director's review</a></c:when>
                                            <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id == idea.member.id}">Waiting for director's review</c:when>
                                            <c:when test="${idea.reviewStatus == 6}">Approved!</c:when>
                                        </c:choose>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="collapsing">
                                    Approver
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${review.member.name == null }">
                                            No approver
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${review.member.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="collapsing">
                                    Created
                                </td>
                                <td><fmt:formatDate value="${idea.createdAt}" pattern="yyyy / MM / dd HH:mm"/></td>
                            </tr>
                            <!-- 作成日時と更新日時が違う場合のみ表示 -->
                            <c:if test="${!fn:contains(idea.createdAt, idea.updatedAt) }">
                                <tr>
                                    <td class="collapsing">
                                        Updated
                                    </td>
                                    <td><fmt:formatDate value="${idea.updatedAt}" pattern="yyyy / MM / dd HH:mm"/></td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                        <c:choose>
                            <c:when test="${sessionScope.loginMember.id != idea.member.id}">
                                <form method="POST" action="<c:url value='/favors/create'/>">
                                    <input type="hidden" name="ideaId" value="${idea.id}">
                                    <div class="ui labeled button" tabindex="0">
                                        <button type="submit" name="favors" value="${1}" class="ui button">
                                            <i class="heart icon"></i>Like
                                        </button>
                                        <c:if test="${idea.favors != 0}">
                                            <a href="<c:url value='/favors?id=${idea.id}'/>" class="ui basic label">
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                        </c:if>
                                        <c:if test="${idea.favors == 0}">
                                            <div class="ui basic label">0</div>
                                        </c:if>
                                    </div>
                                </form>
                            </c:when>
                            <c:when test="${sessionScope.loginMember.id == idea.member.id}">
                                <form method="POST" action="<c:url value='/favors/create' />">
                                    <input type="hidden" name="ideaId" value="${idea.id}">
                                    <div class="ui labeled button" tabindex="0">
                                        <button type="submit" name="favors" value="${1}" class="ui disabled button">
                                            <i class="heart icon"></i>Like
                                        </button>
                                        <c:if test="${idea.favors != 0}">
                                            <a href="<c:url value='/favors?id=${idea.id}' />" class="ui basic label">
                                                <c:out value="${idea.favors}"/>
                                            </a>
                                        </c:if>
                                        <c:if test="${idea.favors == 0}">
                                            <div class="ui basic label">0</div>
                                        </c:if>
                                    </div>
                                </form>
                            </c:when>
                        </c:choose>
                        <c:if test="${(sessionScope.loginMember.role == 2 && idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id) || (sessionScope.loginMember.role == 3 && idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id)}">
                            <h3>Add your advice</h3>
                            <form class="ui form">
                                <textarea name="comment" rows="10" cols="30" form="formId"></textarea>
                                <div class="ui hidden divider"></div>
                                <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
                                    <c:choose>
                                        <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/drafts/update'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/advice/manager/create'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 2}"/>
                                        <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/advice/director/create'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 4}"/>
                                        <c:when test="${idea.reviewStatus == 6}"/>
                                    </c:choose>
                                </c:if>
                                <c:if test="${sessionScope.loginMember.role == 2}">
                                    <c:choose>
                                        <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/drafts/update'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${2}" class="circular ui icon green button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 1}"/>
                                        <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id && idea.member.role != 2}">
                                            <div style="display: inline-flex">
                                                <form method="POST" action="<c:url value='/reviews/manager/create'/>" id="formId">
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${1}" class="circular ui icon olive button">
                                                        <i class="fas fa-undo-alt"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id && idea.member.role == 2}">
                                            <div style="display: inline-flex">
                                                <form method="POST" action="<c:url value='/reviews/manager/create'/>" id="formId">
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                                                        <i class="far fa-paper-plane"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/advice/director/create'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 4}"/>
                                        <c:when test="${idea.reviewStatus == 6}"/>
                                    </c:choose>
                                </c:if>
                                <c:if test="${sessionScope.loginMember.role == 3}">
                                    <c:choose>
                                        <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}">
                                            <form method="POST" action="<c:url value='/drafts/update'/>">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${4}" class="circular ui icon blue button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 1}"/>
                                        <c:when test="${idea.reviewStatus == 2}"/>
                                        <c:when test="${idea.reviewStatus == 3}"/>
                                        <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id && idea.member.role != 3}">
                                            <div style="display: inline-flex">
                                                <form method="POST" action="<c:url value='/reviews/director/create'/>" id="formId">
                                                    <button type="submit" name="reviewStatus" value="${6}" class="circular ui icon violet button">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${3}" class="circular ui icon teal button">
                                                        <i class="fas fa-undo-alt"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id && idea.member.role == 3}">
                                            <div style="display: inline-flex">
                                                <form method="POST" action="<c:url value='/reviews/director/create'/>" id="formId">
                                                    <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                    <button type="submit" name="reviewStatus" value="${6}" class="circular ui icon violet button">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:when test="${idea.reviewStatus == 6}"/>
                                    </c:choose>
                                </c:if>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.loginMember.id == idea.member.id}">
                            <c:if test="${idea.reviewStatus == 0 || idea.reviewStatus == 1 || idea.reviewStatus == 3}">
                                <p>
                                    <a href="<c:url value="/ideas/edit?id=${idea.id}" />">この日報を編集する</a>
                                </p>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h2>The data you were looking for wasn't found.</h2>
            </c:otherwise>
        </c:choose>
        <button onclick="location.href='<c:url value='/ideas'/>'" class="circular ui icon button">
            <i class="fas fa-long-arrow-alt-left"></i>
        </button>
    </c:param>
</c:import>