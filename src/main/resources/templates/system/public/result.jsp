<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="head.jsp" %>
    <title>温馨提示 - ${cfgCompanyName}</title>
</head>

<body>
<div class="container" style="margin-top: 80px;">

    <%@include file="header.jsp" %>

    <div class="row main-low-margin">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="text-center">温馨提示</h4>
                </div>
                <div class="panel-body text-center">
                    <img class="img-circle" src="/app/system/public/image/contact.png">
                    <hr>
                    <p class="text-danger">${resultContent}</p>
                    <hr>
                    <p>${redirectStr}</p>
                </div>
            </div>
        </div>
    </div>

</div>

<%@include file="footer.jsp" %>
${redirectJs}
<script>

</script>
</body>
</html>