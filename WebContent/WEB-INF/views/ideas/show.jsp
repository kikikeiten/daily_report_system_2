<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${idea != null}">
                <h2>日報「<c:out value="${idea.title}"/>」の詳細ページ</h2>
                <table class="ui celled striped table">
                    <tbody>
                    <tr>
                        <th>氏名</th>
                        <td>
                            <c:out value="${idea.member.name}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>フォロー</th>
                        <c:choose>
                        <c:when test="${sessionScope.loginMember.id != idea.member.id}">
                        <c:choose>
                        <c:when test="${!fn:contains(followIdea,idea.member.id)}">
                        <td>
                            <form method="POST" action="<c:url value='/follow/create'/>">
                                <button class="ui tiny active button" type="submit" name="followedId" value="${idea.id}">
                                    <i class="user icon"></i>フォロー
                                </button>
                            </form>
                        </td>
                        </c:when>
                        <c:otherwise>
                        <td>
                            <form method="POST" action="<c:url value='/follow/destroy' />">
                                <button class="ui tiny animated button" type="submit" name="memberId" value="${idea.id}">
                                    <div class="visible content">
                                        <i class="user icon"></i>フォロー中
                                    </div>
                                    <div class="hidden content">
                                        <i class="user icon"></i>フォロー解除
                                    </div>
                                </button>
                            </form>
                        </td>
                        </c:otherwise>
                        </c:choose>
                        </c:when>
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                    <tr>
                        <th>日付</th>
                        <td>
                            <fmt:formatDate value="${idea.createdDate}" pattern="yyyy / MM / dd"/>
                        </td>
                    </tr>
                    <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${idea.content}"/></pre>
                    <tr>
                        <th>登録日時</th>
                        <td>
                            <fmt:formatDate value="${idea.createdAt}" pattern="yyyy / MM / dd HH:mm"/>
                        </td>
                    </tr>
                    <tr>
                        <th>更新日時</th>
                        <td>
                            <fmt:formatDate value="${idea.updatedAt}" pattern="yyyy / MM / dd HH:mm"/>
                        </td>
                    </tr>
                    <tr>
                        <th>承認状況</th>
                        <td>
                            <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
                                <c:choose>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">下書き</a></c:when>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}">下書き</c:when>
                                    <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/manager' />">課長差し戻し</a></c:when>
                                    <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id != idea.member.id}">課長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 2}">課長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/director' />">部長差し戻し</a></c:when>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id != idea.member.id}">部長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 4}">部長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 6}">承認済み</c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${sessionScope.loginMember.role == 2}">
                                <c:choose>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">下書き</a></c:when>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}"></c:when>
                                    <c:when test="${idea.reviewStatus == 1}">課長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id}"><a href="<c:url value='/reviews/manager' />">課長承認待ち</a></c:when>
                                    <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id == idea.member.id}">課長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/advice/director' />">部長差し戻し</a></c:when>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id != idea.member.id}">部長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 4}">部長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 6}">承認済み</c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${sessionScope.loginMember.role == 3}">
                                <c:choose>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}"><a href="<c:url value='/drafts' />">下書き</a></c:when>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id != idea.member.id}">下書き</c:when>
                                    <c:when test="${idea.reviewStatus == 1}">課長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 2}">課長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 3}">部長差し戻し</c:when>
                                    <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id}"><a href="<c:url value='/reviews/director' />">部長承認待ち</a></c:when>
                                    <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id == idea.member.id}">部長承認待ち</c:when>
                                    <c:when test="${idea.reviewStatus == 6}">承認済み</c:when>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>承認履歴</th>
                        <td>
                            <a href="<c:url value='/advice/history?id=${idea.id}'/>"></a>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <c:out value="${name}"/>
                            <c:out value="${role}"/>
                            <c:out value="${reviewStatus}"/>コメント
                        </th>
                        <td>
                            <c:out value="${advice}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>新規コメント</th>
                        <td>
                            <c:if test="${(sessionScope.loginMember.role == 2 && idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id) || (sessionScope.loginMember.role == 3 && idea.approval == 4 && sessionScope.loginMember.id != idea.member.id)}">
                                <form class="ui form">
                                    <textarea name="comment" rows="10" cols="50" form="formId"></textarea>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>承認</th>
                        <td>
                            <c:if test="${sessionScope.loginMember.role == 0 || sessionScope.loginMember.role == 1}">
                                <c:choose>
                                    <c:when test="${idea.reviewStatus == 0 && sessionScope.loginMember.id == idea.member.id}">
                                        <form method="POST" action="<c:url value='/drafts/update'/>">
                                            <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            <button type="submit" name="reviewStatus" value="${2}" class="ui positive button">提出</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 1 && sessionScope.loginMember.id == idea.member.id}">
                                        <form method="POST" action="<c:url value='/advice/manager/create'/>">
                                            <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            <button type="submit" name="reviewStatus" value="${2}" class="ui positive button">再提出</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 2}"/>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}">
                                        <form method="POST" action="<c:url value='/advice/director/create'/>">
                                            <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            <button type="submit" name="reviewStatus" value="${4}" class="ui positive button">再提出</button>
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
                                            <input type="hidden" name="report_id" value="${idea.id}"/>
                                            <button type="submit" name="reviewStatus" value="${2}" class="ui positive button">提出</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 1}"/>
                                    <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id && idea.member.role != 2}">
                                        <div style="display: inline-flex">
                                            <form method="POST" action="<c:url value='/reviews/manager/create'/>" id="formId">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${4}" class="ui positive button">承認</button>
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${1}" class="ui negative button">差し戻し</button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 2 && sessionScope.loginMember.id != idea.member.id && idea.member.role == 2}">
                                        <div style="display: inline-flex">
                                            <form method="POST" action="<c:url value='/reviews/manager/create'/>" id="formId">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${4}" class="ui positive button">承認</button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 3 && sessionScope.loginMember.id == idea.member.id}">
                                        <form method="POST" action="<c:url value='/advice/director/create'/>">
                                            <input type="hidden" name="ideaId" value="${idea.id}"/>
                                            <button type="submit" name="reviewStatus" value="${4}" class="ui positive button">再提出</button>
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
                                            <button type="submit" name="reviewStatus" value="${4}" class="ui positive button">提出
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 1}"/>
                                    <c:when test="${idea.reviewStatus == 2}"/>
                                    <c:when test="${idea.reviewStatus == 3}"/>
                                    <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id && idea.member.role != 3}">
                                        <div style="display: inline-flex">
                                            <form method="POST" action="<c:url value='/reviews/director/create'/>" id="formId">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${6}" class="ui positive button">
                                                    承認
                                                </button>
                                                &nbsp; <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${3}" class="ui negative button">
                                                    差し戻し
                                                </button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 4 && sessionScope.loginMember.id != idea.member.id && idea.member.role == 3}">
                                        <div style="display: inline-flex">
                                            <form method="POST" action="<c:url value='/reviews/director/create'/>" id="formId">
                                                <input type="hidden" name="ideaId" value="${idea.id}"/>
                                                <button type="submit" name="reviewStatus" value="${6}" class="ui positive button">承認</button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${idea.reviewStatus == 6}"/>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
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
                <c:if test="${sessionScope.loginMember.id == idea.member.id}">
                    <c:if test="${reviewStatus == 0 || reviewStatus == 1 || reviewStatus == 3}">
                        <p>
                            <a href="<c:url value="/ideas/edit?id=${idea.id}" />">この日報を編集する</a>
                        </p>
                    </c:if>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value="/ideas" />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>