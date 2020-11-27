<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${toast != null}">
            <script>
                $('body')
                    .toast({
                        class: 'success',
                        message: "${toast}",
                        position: 'bottom right',
                        showProgress: 'top',
                        progressUp: true,
                        className: {
                            toast: 'ui message'
                        }
                    });
            </script>
        </c:if>
        <h2>My drafts</h2>
        <div class="circular ui icon yellow mini button" data-variation="inverted"></div>
        <script>
            $('.yellow.button')
                .popup({
                    position: 'bottom center',
                    content: 'Unsubmitted draft'
                });
        </script>
        <div class="ui raised very padded container segment">
            <c:choose>
                <c:when test="${getMyDraftsCnt == 0}">
                    <div class="ui active dimmer">
                        <div class="content">
                            <h3><c:out value="${sessionScope.loginMember.name}"/>さんの下書きの日報はありません。</h3>
                            <p>下書きを作成するとここに表示されます。</p>
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
                </c:when>
                <c:otherwise>
                    <div class="ui three stackable raised link cards">
                        <c:forEach var="idea" items="${getMyDrafts}">
                            <div class="ui yellow card">
                                <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />">
                                    <span class="right floated">
                                        <fmt:formatDate value='${idea.createdDate}' pattern='MM / dd'/>
                                    </span>
                                    <span class="header">
                                            <c:out value="${idea.title}"/>
                                    </span>
                                    <span class="description">
                                    </span>
                                </a>
                                <div class="extra content">
                                    <form method="POST" action="<c:url value='/drafts/update' />" class="left floated">
                                        <c:choose>
                                            <c:when test="${sessionScope.loginMember.role != 3}">
                                                <button type="submit" name="reviewStatus" value="${2}" class="circular ui mini icon green button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" name="reviewStatus" value="${4}" class="circular ui mini icon blue button">
                                                    <i class="far fa-paper-plane"></i>
                                                </button>
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                    <a class="right floated date" href="<c:url value='/members/show?id=${idea.member.id}' />">
                                        <c:out value="${idea.member.name}"/>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="ui hidden divider"></div>
                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getMyDraftsCnt - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/drafts?page=${i}' />">
                                        <c:out value="${i}"/>
                                    </a>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="_labels.jsp"/>
    </c:param>
</c:import>