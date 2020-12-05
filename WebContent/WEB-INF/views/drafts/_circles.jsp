<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="circular ui icon yellow mini button" data-variation="inverted"></div>
<script>
    $('.yellow.button')
        .popup({
            position: 'bottom center',
            content: 'Unsubmitted draft'
        });
</script>