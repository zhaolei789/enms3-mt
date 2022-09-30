<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/topjui/ext/css/bootstrap-ext.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <title>测试页面</title>
</head>

<body>

<div class="container">

    <div class="row clearfix top bottom">
        <div class="col-md-12 column">
            <div class="btn-group">
                <button class="btn btn-sm btn-success" type="button" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章"><em class="glyphicon glyphicon-plus"></em> 新增</button>
                <button class="btn btn-sm btn-primary" type="button"><em class="glyphicon glyphfa fa-pencil"></em> 编辑</button>
                <button class="btn btn-sm btn-danger" type="button"><em class="glyphicon glyphicon-remove"></em> 删除</button>
                <button class="btn btn-sm btn-info" type="button"><em class="glyphicon glyphicon-search"></em> 查询</button>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-8 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        编号
                    </th>
                    <th>
                        产品
                    </th>
                    <th>
                        交付时间
                    </th>
                    <th>
                        状态
                    </th>
                    <th>
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        1
                    </td>
                    <td>
                        TB - Monthly
                    </td>
                    <td>
                        01/04/2012
                    </td>
                    <td>
                        Default
                    </td>
                    <td>
                        <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章">编辑</a>
                        <a href="">删除</a>
                    </td>
                </tr>
                <tr class="success">
                    <td>
                        1
                    </td>
                    <td>
                        TB - Monthly
                    </td>
                    <td>
                        01/04/2012
                    </td>
                    <td>
                        Approved
                    </td>
                    <td>
                        <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章">编辑</a>
                        <a href="">删除</a>
                    </td>
                </tr>
                <tr class="error">
                    <td>
                        2
                    </td>
                    <td>
                        TB - Monthly
                    </td>
                    <td>
                        02/04/2012
                    </td>
                    <td>
                        Declined
                    </td>
                    <td>
                        <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章">编辑</a>
                        <a href="">删除</a>
                    </td>
                </tr>
                <tr class="warning">
                    <td>
                        3
                    </td>
                    <td>
                        TB - Monthly
                    </td>
                    <td>
                        03/04/2012
                    </td>
                    <td>
                        Pending
                    </td>
                    <td>
                        <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章">编辑</a>
                        <a href="">删除</a>
                    </td>
                </tr>
                <tr class="info">
                    <td>
                        4
                    </td>
                    <td>
                        TB - Monthly
                    </td>
                    <td>
                        04/04/2012
                    </td>
                    <td>
                        Call in to confirm
                    </td>
                    <td>
                        <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="编辑文章">编辑</a>
                        <a href="">删除</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-4 column">
            <div class="list-group">
                <a href="#" class="list-group-item active">功能操作</a>
                <div class="list-group-item">
                    <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="新增文章">新增</a>
                </div>
                <div class="list-group-item">
                    <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit" data-mtitle="新增文章">新增</a>
                </div>
                <div class="list-group-item">
                    <span class="badge">14</span> Help
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addDialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    新增文章
                </h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/topjui/ext/js/topjui.function.js"></script>
<script src="${ctx}/static/topjui/ext/js/topjui.bs.dialog.js"></script>
<script src="${ctx}/static/topjui/ext/js/topjui.bs.plugins.js"></script>
</body>
</html>
