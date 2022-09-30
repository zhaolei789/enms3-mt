<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%@ include file="../../common/common/head-print.jsp" %>
    <%@ include file="../../common/common/head-bootstrap.jsp" %>
    <style>
        .syntaxhighlighter td.code .container::before, .syntaxhighlighter td.code .container::after {
            display: none;
        }

        span {
            margin-right: 10px;
            color: #999;
        }
    </style>
    <link href="${staticServer}/static/plugins/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css"
          rel="stylesheet" type="text/css"/>
    <style id="printStyle">
        .content1 {
            font-size: 12px;
        }

        .content1 img {
            width: 400px;
            height: auto;
        }
    </style>
    <title>打印示例</title>
</head>
<body>
<div class="datagrid-toolbar" class="topjui-toolbar">
    <a href="javascript:printPreview()" class="l-btn l-btn-small l-btn-plain">
        <span class="l-btn-left l-btn-icon-left">
            <span class="l-btn-text">打印预览</span>
            <span class="l-btn-icon fa fa-print">&nbsp;</span>
        </span>
    </a>
</div>
<div class="container visible-lg-block">
    <div class="row margin-top-10">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">${documentArticleView.title}</h3>
                </div>
                <div class="list-group text-center">
                    <div class="list-group-item">
                        <span><i class="fa fa-user"></i> ${documentArticleView.creator}</span>
                        <span><i class="fa fa-clock-o"></i> ${fn:substring(documentArticleView.createTime,0,19)}</span>
                    </div>
                </div>
                <div id="printArea0">
                    <div id="singleArea">
                        <div class="panel-body content1">${documentArticle.content}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid visible-xs-block visible-sm-block visible-md-block">
    <div class="row margin-top-10">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">${documentArticleView.title}</h3>
                </div>
                <div class="list-group text-center">
                    <div class="list-group-item">
                        <span><i class="fa fa-user"></i> ${documentArticleView.creator}</span>
                        <span><i class="fa fa-clock-o"></i> ${fn:substring(documentArticleView.createTime,0,19)}</span>
                    </div>
                </div>
                <div class="panel-body content2">${documentArticle.content}</div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${staticServer}/static/plugins/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<script type="text/javascript"
        src="${staticServer}/static/jquery/plugins/jquery.imgAutoSize.js"></script>
<script type="text/javascript">
    $(function ($) {
        SyntaxHighlighter.all();
        $('.content1').imgAutoSize(30);
        $('.content2').imgAutoSize(30);
    });
</script>
<script type="text/javascript">
    function printPreview() {
        LODOP = getLodop();
        var strBodyStyle = "<style>" + document.getElementById("printStyle").innerHTML + "</style>";
        for (var i = 0; i < 1; i++) {
            var strHtml = strBodyStyle + "<body>" + document.getElementById("printArea" + i).innerHTML + "</body>"
            LODOP.PRINT_INITA(10, 20, 810, 610, "打印预览");
            LODOP.NewPage();
            LODOP.ADD_PRINT_HTM(20, 10, "93%", "95%", strHtml);
        }
        LODOP.PREVIEW();
    }
</script>
</body>
</html>