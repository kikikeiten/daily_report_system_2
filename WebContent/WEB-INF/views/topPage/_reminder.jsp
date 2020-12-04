<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${get4getMyDraftsCnt != 0 && get4getManagerReviewsCnt != 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />">
            Unsubmitted drafts&nbsp;
            <span class="ui yellow circular label">
                <c:out value="${get4getMyDraftsCnt}"/>
            </span><br/>
        </a>
        <a href="<c:url value='/reviews/manager' />">
            Ideas waiting for manager's reviews&nbsp;
            <span class="ui  green circular label">
                <c:out value="${get4getManagerReviewsCnt}"/>
            </span>
        </a>
    </div>
</c:if>
<c:if test="${get4getMyDraftsCnt != 0 && get4getDirectorReviewsCnt != 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />">
            Unsubmitted drafts&nbsp;
            <span class="ui yellow circular label">
                <c:out value="${get4getMyDraftsCnt}"/>
            </span><br/>
        </a>
        <a href="<c:url value='/reviews/director' />">
            Ideas waiting for director's reviews&nbsp;
            <span class="ui blue circular label">
                <c:out value="${get4getDirectorReviewsCnt}"/>
            </span>
        </a>
    </div>
</c:if>
<c:if test="${get4getMyDraftsCnt != 0 && get4getManagerReviewsCnt == 0 && get4getDirectorReviewsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/drafts' />">Unsubmitted drafts&nbsp;
            <span class="ui yellow circular label">
                <c:out value="${get4getMyDraftsCnt}"/>
            </span>
        </a>
    </div>
</c:if>
<c:if test="${get4getManagerReviewsCnt != 0 && get4getMyDraftsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/reviews/manager' />">
            Ideas waiting for manager's reviews&nbsp;
            <span class="ui green circular label">
                <c:out value="${get4getManagerReviewsCnt}"/>
            </span>
        </a>
    </div>
</c:if>
<c:if test="${get4getDirectorReviewsCnt != 0 && get4getMyDraftsCnt == 0}">
    <div class="ui error message">
        <i class="close icon"></i>
        <script>
            $('.message .close').on('click', function () {
                $(this).closest('.message').transition('fade');
            });
        </script>
        <a href="<c:url value='/reviews/director' />">
            Ideas waiting for director's reviews&nbsp;
            <span class="ui blue circular label">
                <c:out value="${get4getDirectorReviewsCnt}"/>
            </span>
        </a>
    </div>
</c:if>