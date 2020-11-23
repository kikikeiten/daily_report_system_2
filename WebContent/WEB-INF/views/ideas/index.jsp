<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">

    <c:param name="content">

        <c:if test="${flush != null}">
            <script>
                $('body')
                    .toast({
                        class: 'success',
                        message: "${flush}",
                        position: 'bottom right',
                        showProgress: 'top',
                        progressUp: true,
                        className: {
                            toast: 'ui message'
                        }
                    })
                ;
            </script>
        </c:if>

        <h2>All swatches</h2>

        <a href="<c:url value='/advice/manager' />" class="circular ui icon olive mini button"
           data-variation="inverted"></a>
        <script>
            $('.olive.button')
                .popup({
                    position: 'bottom center',
                    content: 'Remanded to the manager'
                });
        </script>

        <div class="circular ui icon green mini button" data-variation="inverted"></div>
        <script>
            $('.green.button')
                .popup({
                    position: 'bottom center',
                    content: 'Waiting for manager approval'
                })
            ;
        </script>

        <a href="<c:url value='/advice/director' />" class="circular ui icon teal mini button"
           data-variation="inverted"></a>
        <script>
            $('.teal.button')
                .popup({
                    position: 'bottom center',
                    content: 'Remanded to the director'
                })
            ;
        </script>

        <div class="circular ui icon blue mini button" data-variation="inverted"></div>
        <script>
            $('.blue.button')
                .popup({
                    position: 'bottom center',
                    content: 'Waiting for director approval'
                })
            ;
        </script>

        <div class="circular ui icon violet mini button" data-variation="inverted"></div>
        <script>
            $('.violet.button')
                .popup({
                    position: 'bottom center',
                    content: 'Approved'
                })
            ;
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getIdeasCntButDrafts == 0}">

                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>日報はまだありません。</h3>
                            <p>作成されるとここに表示されます。</p>
                        </div>
                    </div>

                    <div class="ui three stackable raised link cards">

                        <c:forEach begin="0" end="5" step="1">
                            <div class="ui card">
                                <a class="content" href=""> <span class="right floated"></span> <span
                                        class="header"></span> <span class="description"> </span>
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
                        <c:forEach var="idea" items="${getIdeasButDrafts}" varStatus="status">
                            <c:choose>

                                <c:when test="${idea.reviewStatus == 1}">
                                    <div class="ui olive card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />"> <span
                                                class="right floated"><fmt:formatDate value='${idea.createdDate}'
                                                                                      pattern='MM / dd'/></span> <span
                                                class="header"><c:out value="${idea.title}"/></span> <span
                                                class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?id=${idea.id}' />"> <i
                                                    class="far fa-heart"></i> <c:out value="${idea.favors}"/>
                                            </a> <a class="right floated date"
                                                    href="<c:url value='/members/show?id=${idea.member.id}' />">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${idea.reviewStatus == 2}">
                                    <div class="ui green card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />"> <span
                                                class="right floated"><fmt:formatDate value='${idea.createdDate}'
                                                                                      pattern='MM / dd'/></span> <span
                                                class="header"><c:out value="${idea.title}"/></span> <span
                                                class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?id=${idea.id}' />"> <i
                                                    class="far fa-heart"></i> <c:out value="${idea.favors}"/>
                                            </a> <a class="right floated date"
                                                    href="<c:url value='/members/show?id=${idea.member.id}' />">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${idea.reviewStatus == 3}">
                                    <div class="ui teal card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />"> <span
                                                class="right floated"><fmt:formatDate value='${idea.createdDate}'
                                                                                      pattern='MM / dd'/></span> <span
                                                class="header"><c:out value="${idea.title}"/></span> <span
                                                class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?_id=${idea.id}' />"> <i
                                                    class="far fa-heart"></i> <c:out value="${idea.favors}"/>
                                            </a> <a class="right floated date"
                                                    href="<c:url value='/members/show?id=${idea.member.id}' />">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${idea.reviewStatus == 4}">
                                    <div class="ui blue card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />"> <span
                                                class="right floated"><fmt:formatDate value='${idea.createdDate}'
                                                                                      pattern='MM / dd'/></span> <span
                                                class="header"><c:out value="${idea.title}"/></span> <span
                                                class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?id=${idea.id}' />"> <i
                                                    class="far fa-heart"></i> <c:out value="${idea.favors}"/>
                                            </a> <a class="right floated date"
                                                    href="<c:url value='/members/show?id=${idea.member.id}' />">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${idea.reviewStatus == 6}">
                                    <div class="ui violet card">
                                        <a class="content" href="<c:url value='/ideas/show?id=${idea.id}' />"> <span
                                                class="right floated"><fmt:formatDate value='${idea.createdDate}'
                                                                                      pattern='MM / dd'/></span> <span
                                                class="header"><c:out value="${idea.title}"/></span> <span
                                                class="description"> </span>
                                        </a>
                                        <div class="extra content">
                                            <a class="left floated like"
                                               href="<c:url value='/favors?id=${idea.id}' />"> <i
                                                    class="far fa-heart"></i> <c:out value="${idea.favors}"/>
                                            </a> <a class="right floated date"
                                                    href="<c:url value='/members/show?id=${idea.member.id}' />">
                                            <c:out value="${idea.member.name}"/>
                                        </a>
                                        </div>
                                    </div>
                                </c:when>

                            </c:choose>
                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui centered mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getIdeasCntButDrafts - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/ideas?page=${i}' />"><c:out
                                            value="${i}"/></a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>

        </div>

        <a href="<c:url value='/' />" class="ui image label"> My swatches <span class="detail"> ${getMyIdeasCnt} </span>
        </a>

        <div class="ui teal image label">
            All swatches
            <div class="detail">${getIdeasCntButDrafts}</div>
        </div>

        <a href="<c:url value='/drafts' />" class="ui image label"> My drafts <span
                class="detail"> ${getMyDraftsCnt} </span>
        </a>

        <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
            <a href="<c:url value='/advice/manager' />" class="ui image label"> Manager remand <span
                    class="detail"> ${getManagerAdviceCnt} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.loginMember.role == 2}">
            <a href="<c:url value='/reviews/manager' />" class="ui image label"> Manager approval <span
                    class="detail"> ${getManagerReviewsCnt} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1 || sessionScope.loginMember.role == 2}">
            <a href="<c:url value='/advice/director' />" class="ui image label"> Director remand <span
                    class="detail"> ${getDirectorAdviceCnt} </span>
            </a>
        </c:if>

        <c:if test="${sessionScope.loginMember.role == 3}">
            <a href="<c:url value='/reviews/director' />" class="ui image label"> Director approval <span
                    class="detail"> ${getDirectorReviewsCnt} </span>
            </a>
        </c:if>

    </c:param>
</c:import>