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

        <h2>With advice from the manager</h2>

        <div class="circular ui icon olive mini button" data-variation="inverted"></div>
        <script>
        $('.olive.button')
        .popup({
            position : 'bottom center',
            content  : 'With advice from the manager'
        });
        </script>

        <div class="ui raised very padded container segment">

            <c:choose>
                <c:when test="${getAllManagerAdviceCount == 0}">

                    <div class="ui active dimmer">
                        <div class="content">
                            <h3>There is no idea with the advice of the manager.</h3>
                            <p>Any advice from the manager for ideas will be displayed here.</p>
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
                        <c:forEach var="advice" items="${getAllManagerAdvice}">

                            <div class="ui olive card">
                                <a class="content" href="<c:url value='/ideas/?id=${advice.id}' />"> <span class="right floated"><fmt:formatDate value='${advice.idea_date}' pattern='MM / dd' /></span> <span class="header"><c:out value="${advice.title}" /></span> <span class="description"> </span>
                                </a>

                                <div class="extra content">

                                    <form method="POST" action="<c:url value='/advice/manager/create' />" class="left floated">
                                        <input type="hidden" name="idea_id" value="${advice.id}" />
                                        <button type="submit" name="submit" value="${2}" class="circular ui mini icon green button">
                                            <i class="fas fa-paper-plane"></i>
                                        </button>
                                    </form>

                                    <a class="right floated date" href="<c:url value='/members/?id=${advice.member.id}' />"> <c:out value="${advice.member.name}" />
                                    </a>

                                </div>
                            </div>

                        </c:forEach>
                    </div>

                    <div class="ui hidden divider"></div>

                    <div class="ui mini pagination menu">
                        <c:forEach var="i" begin="1" end="${((getAllManagerAdviceCount - 1) / 12) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <div class="item active">
                                        <c:out value="${i}" />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="item" href="<c:url value='/advice/manager?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
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

        <div class="ui teal image label">
            Manager's advice
            <div class="detail">${getAllManagerAdviceCount}</div>
        </div>

        <a href="<c:url value='/advice/director' />" class="ui image label"> Director's advice <span class="detail"> ${getAllDirectorAdviceCount} </span>
        </a>

    </c:param>
</c:import>