<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>日報〇〇の承認履歴一覧</h2>
        <c:choose>
            <c:when test="${承認履歴 == 0}">
                <h3>日報〇〇に承認履歴はありません。</h3>
                <p>日報が承認または差し戻しされるとここに表示されます。</p>
            </c:when>
            <c:otherwise>
                <table id="comment_list">
                    <tbody>
                        <tr>
                            <th class="comment_date">日付</th>
                            <th class="comment_comment">コメント</th>
                            <th class="comment_name">承認者</th>
                            <th class="comment_position">役職</th>
                            <th class="comment_status">承認状況</th>
                        </tr>
                        <c:forEach var="comment" items="${コメントリスト}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="comment_date"><fmt:formatDate
                                        value='${comment.created_at}' pattern='yyyy-MM-dd' /></td>
                                <td class="comment_comment"><c:out
                                        value="${comment.comment}" /></td>
                                <td class="comment_name"><c:out
                                        value="${comment.employee.name}" /></td>
                                <td><c:if test="${comment.employee.admin_flag == 2}">
                                課長
                                </c:if> <c:if
                                        test="${comment.employee.admin_flag == 3}">
                                部長
                                </c:if></td>
                                <td><c:if
                                        test="${comment.approval == 1 || comment.approval == 3}">
                                差し戻し
                                </c:if> <c:if
                                        test="${comment.approval == 4 || comment.approval == 6}">
                                承認
                                </c:if></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination">
                    （全 ${コメント件数} 件）<br />
                    <c:forEach var="i" begin="1" end="${((コメント件数 - 1) / 10) + 1}"
                        step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/approval/history?page=${i}' />"><c:out
                                        value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value='/reports/show?id=${レポートID}' />">日報詳細に戻る</a>
        </p>
    </c:param>
</c:import>