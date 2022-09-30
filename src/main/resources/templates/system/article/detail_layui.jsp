<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="${staticServer}/static/topjui/css/grid.css">
    <link rel="stylesheet" type="text/css" href="${staticServer}/static/plugins/layui/css/layui.css">
    <script type="text/javascript" src="${staticServer}/static/plugins/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/topjui/topjui.function.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/layui/layui.js"></script>

    <style>
        body {
            margin: 10px;
        }

        .layui-elem-field legend {
            font-weight: bold;
        }

        .layui-colla-title {
            font-weight: bold;
        }

        .layui-colla-content p {
            line-height: 30px;
        }
    </style>
    <script type="text/javascript">
        $(function () {

            layui.use(['element'], function () {
                var element = layui.element();

                //监听折叠
                element.on('collapse(test)', function (data) {
                    //layer.msg('展开状态：'+ data.show);
                });
            });
        });
    </script>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <fieldset class="layui-elem-field">
                <legend>调用方法</legend>
                <div class="layui-field-box">
                    内容区域
                </div>
            </fieldset>

            <fieldset class="layui-elem-field">
                <legend>参数说明</legend>
                <div class="layui-field-box">
                    内容区域
                </div>
            </fieldset>

            <fieldset class="layui-elem-field layui-field-title">
                <legend>参数说明</legend>
                <div class="layui-field-box">
                    内容区域
                </div>
            </fieldset>

            <div class="layui-collapse" lay-filter="test">
                <div class="layui-colla-item">
                    <h2 class="layui-colla-title">调用方法</h2>
                    <div class="layui-colla-content layui-show">
                        <p>导出功能需要后端配合才能实现，点击导出按钮执行导出请求时，TopJUI会传递以下三个参数到后端</p>
                        <p>excelTitle：要导出的Excel名称</p>
                        <p>colName：中文列名</p>
                        <p>fieldName：字段名</p>
                        <p>后端接收到以上参数组合查询语句，配合第三方Excel导出插件封装后实现通用Excel导出功能。</p>
                    </div>
                </div>
                <div class="layui-colla-item">
                    <h2 class="layui-colla-title">参数说明</h2>
                    <div class="layui-colla-content layui-show">
                        <p>
                            演示地址：<a href="http://demo.ewsd.cn/system/index/index" target="_blank">http://demo.ewsd.cn/system/index/index</a>
                        </p>
                        <p>用户名 / 密码：topjui / topjui</p>
                        <p>登录后进入：系统管理平台 => 用户管理 => 用户列表 => 导出 => 导出Excel列表</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

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

</body>
</html>
