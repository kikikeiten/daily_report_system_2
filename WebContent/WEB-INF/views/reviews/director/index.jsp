<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
              });
            </script>
        </c:if>

        <h2>Waiting for the director's review</h2>

        <div class="circular ui icon blue mini button" data-variation="inverted"></div>
        <script>
        $('.blue.button')
        .popup({
            position : 'bottom center',
            content  : 'Waiting for the director\'s review'
        })
        ;
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getAllDirectorReviewsCount == 0}">

                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>There is no idea waiting for the director's review.</h3>
                            <p>If another member submits an idea to the director, it will be displayed here.</p>
                        </div>
                    </div>

                    <div class="ui three stackable raised link cards">
                        <c:forEach begin="0" end="5" step="1">
                            <div class="ui card">
                                <a class="content" href=""> <span class="right floated"></span> <span class="header"></span> <span class="description"> </span>
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
                        <c:forEach var="review" items="${getAllDirectorReviews}">

                            <div class="ui blue card">
                                <a class="content" href="<c:url value='/ideas/?id=${review.id}' />"> <span class="right floated"><fmt:formatDate value='${review.idea_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${review.title}" /></span> <span class="description"> </span>
                                </a>

                                <div class="extra content">

                                    <c:if test="${sessionScope.login_member.id != idea.member.id && idea.member.admin_role != 3}">
                                        <form method="POST" action="<c:url value='/reviews/director/create' />" class="left floated">
                                            <input type="hidden" name="idea_id" value="${review.id}" />
                                            <button type="submit" name="submit" value="${3}" class="circular ui mini icon teal button">
                                                <i class="fas fa-comment-medical"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${sessionScope.login_member.id != review.member.id}">
                                        <form method="POST" action="<c:url value='/reviews/director/create' />" class="left floated">
                                            <input type="hidden" name="idea_id" value="${review.id}" />
                                            <button type="submit" name="submit" value="${6}" class="circular ui mini icon purple button">
                                                <i class="far fa-paper-plane"></i>
                                            </button>
                                        </form>
                                    </c:if>

                                    <a class="right floated date" href="<c:url value='/members/?id=${review.member.id}' />"> <c:out value="${review.member.name}" />
                                    </a>

                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getAllDirectorReviewsCount - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/reviews/director?page=${i}' />"><c:out value="${i}" /></a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>

        </div>

        <a href="<c:url value='/' />" class="ui image label"> My ideas <span class="detail"> ${getMyIdeasCount} </span>
        </a>

        <a href="<c:url value='/ideas' />" class="ui image label"> All ideas <span class="detail"> ${getAllIdeasCountButDrafts} </span>
        </a>

        <a href="<c:url value='/drafts' />" class="ui image label"> My drafts <span class="detail"> ${getMyDraftsCount} </span>
        </a>

        <div class="ui image teal label">
            Director's reviews
            <div class="detail">${getAllDirectorReviewsCount}</div>
        </div>

    </c:param>
</c:import>