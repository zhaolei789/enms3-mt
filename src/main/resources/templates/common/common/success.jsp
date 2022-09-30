<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="head-bootstrap.jsp" %>
    <title>操作提示 - ${config_siteConfig.cfgCompanyName}</title>
</head>

<body>
<div class="container" style="margin-top: 100px;">

    <div class="row main-low-margin">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-success">
                <div class="panel-heading text-center">${resultTitle}</div>
                <div class="panel-body text-center">
                    <%--<img class="img-circle" src="/app/home/public/images/contact.png">--%>
                    <%--<hr>--%>
                    <p class="text-success">${resultContent}</p>
                    <%--<hr>--%>
                    <p>${redirectStr}</p>
                </div>
            </div>
        </div>
    </div>

</div>

${redirectJs}
<script>

</script>
</body>
</html>