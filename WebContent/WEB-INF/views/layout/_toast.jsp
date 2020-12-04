<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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