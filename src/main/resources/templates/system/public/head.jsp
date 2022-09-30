<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="/app/system/public/image/favicon.ico"/>
<link rel="stylesheet" href="${staticServer}/static/formValidation/css/formValidation.min.css">
<link rel="stylesheet" href="${staticServer}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${staticServer}/static/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/app/system/public/css/style.css">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script type="text/javascript" src="${staticServer}/static/bootstrap/js/html5shiv.js"></script>
<script type="text/javascript" src="${staticServer}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->

<!--[if lt IE 9]>
<script type="text/javascript" src="${staticServer}/static/jquery/jquery-1.10.2.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${staticServer}/static/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${staticServer}/static/jquery/plugins/jquery.common.js"></script>
<script type="text/javascript" src="${staticServer}/static/jquery/plugins/jquery.form.js"></script>
<script type="text/javascript" src="${staticServer}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="${staticServer}/static/bootstrap/plugins/bootstrap-paginator/bootstrap-paginator.js"></script>
<script type="text/javascript"
        src="${staticServer}/static/bootstrap/plugins/bootstrap-paginator/bootstrap-paginator-ext.js"></script>
<script type="text/javascript" src="${staticServer}/static/formValidation/js/formValidation.min.js"></script>
<script type="text/javascript" src="${staticServer}/static/formValidation/js/framework/bootstrap.min.js"></script>
<script type="text/javascript" src="${staticServer}/static/formValidation/js/language/zh_CN.js"></script>
<script type="text/javascript">
    $(function () {
        $(window).resize(function () {
            $("#main-container").css("min-height", $(window).height() - 343);
        }).resize();
    });
    var rows = 10;
</script>