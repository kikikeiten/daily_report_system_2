<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${get4getMyDraftsCnt != 0 && get4getManagerReviewsCnt != 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function() {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />"> 下書きのメモが<c:out value="${get4getMyDraftsCnt}" />件あります。
        </a> <a href="<c:url value='/reviews/manager' />"> 課長承認待ちの日報が<c:out value="${get4getManagerReviewsCnt}" />件あります。
        </a>
    </div>
</c:if>
<c:if test="${get4getMyDraftsCnt != 0 && get4getDirectorReviewsCnt != 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function() {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />"> 下書きのメモが<c:out value="${get4getMyDraftsCnt}" />件あります。
        </a> <a href="<c:url value='/reviews/director' />"> 部長承認待ちの日報が<c:out value="${get4getDirectorReviewsCnt}" />件あります。
        </a>
    </div>
</c:if>
<c:if test="${get4getMyDraftsCnt != 0 && get4getManagerReviewsCnt == 0 && get4getDirectorReviewsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function() {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />"> 下書きのメモが<c:out value="${get4getMyDraftsCnt}" />件あります。
        </a>
    </div>
</c:if>
<c:if test="${get4getManagerReviewsCnt != 0 && get4getMyDraftsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function() {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/reviews/manager' />"> 課長承認待ちの日報が<c:out value="${get4getManagerReviewsCnt}" />件あります。
        </a>
    </div>
</c:if>
<c:if test="${get4getDirectorReviewsCnt != 0 && get4getMyDraftsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function() {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/reviews/director' />"> 部長承認待ちの日報が<c:out value="${get4getDirectorReviewsCnt}" />件あります。
        </a>
    </div>
</c:if>