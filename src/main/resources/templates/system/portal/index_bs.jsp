<%@ page import="cn.ewsd.system.common.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <link type="text/css" href="${staticServer}/static/topjui/themes/default/topjui.red.css" rel="stylesheet" id="dynamicTheme"/>
    <link rel="stylesheet" type="text/css" href="${staticServer}/static/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${staticServer}/static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="${staticServer}/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/bootstrap/js/bootstrap.min.js"></script>
    <script>
        /* 静态演示中获取contextPath，动态演示非必须 开始 */
        var contextPath = "";
        var firstPathName = window.location.pathname.split("/")[1];
        if (!(firstPathName == "html" || firstPathName == "json" || firstPathName == "topjui")) {
            contextPath = "/" + firstPathName;
        }
        /* 静态演示中获取contextPath，动态演示非必须 结束 */
    </script>
    <style>
        body {
            font: 13px/normal "microsoft yahei", "Times New Roman", "宋体", Times, serif;
            letter-spacing: 1px;
        }

        .container-fluid {
            margin-top: 5px !important;
        }

        .panel {
            margin-bottom: 10px;
        }

        .panel-heading {
            font-weight: bold;
        }

        .panel-body {
            height: 165px;
        }

        ul {
            margin: 0;
            padding-left: 10px;
        }

        ul li {
            line-height: 27px;
        }

        .colorPic {
            padding-left: 0;
        }

        .colorPic li {
            list-style: none;
            width: 46%;
            height: 120px;
            float: left;
            margin: 10px;;
        }

        /*div[class^=col-] {
            position: relative;
            min-height: 1px;
            padding-right: 5px;
            padding-left: 5px;
        }*/

        .row .no_gutter[class^="col-"], .row .no_gutter [class*="col-"] {
            padding-right: 5px;
            padding-left: 5px;
        }
    </style>
</head>

<body>

<div class="container-fluid" style="margin-top: 20px;">
    <div class="row">
        <div class="col-md-9">
            <div class="row no_gutter">
                <div class="col-md-6">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-edge"></i> 公告通知
                        </div>
                        <div class="panel-body">
                            <ul>
                                <c:forEach items="${notices}" var="notice">
                                    <li><a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/detail?uuid=${notice.uuid}',title:'${notice.title}'})">${notice.title}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-user"></i> 最新文章
                        </div>
                        <table class="table table-hover">
                            <tbody>
                            <c:forEach items="${documents}" var="document">
                                <tr>
                                    <td>
                                        <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/detail?uuid=${document.uuid}',title:'${document.title}'})">${document.title}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>

                <div class="col-md-6">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-edge"></i> 知识文档
                        </div>
                        <div class="panel-body">
                            <ul>
                                <c:forEach items="${archives}" var="archive">
                                    <li>
                                        <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/archive/detail?uuid=${archive.uuid}',title:'${archive.title}'})">${archive.title}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-user"></i> 客商管理
                        </div>
                        <table class="table table-hover">
                            <tbody>
                            <c:forEach items="${documents}" var="document">
                                <tr>
                                    <td>
                                        <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/detail?uuid=${document.uuid}',title:'${document.title}'})">${document.title}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>

            <div class="row no_gutter">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart"></i> 快速入口
                        </div>
                        <div class="panel-body">

                            <style>
                                .link a {
                                    color: #fff;
                                    text-decoration: none;
                                }

                                .link .fa {
                                    height: 135px;
                                    line-height: 135px;
                                }

                                .link .bg {
                                    height: 135px;
                                    text-align: center
                                }

                                .link .bg:hover {
                                    background-color: #009688;
                                }

                                .bg-green {
                                    background-color: #5FB878;
                                }

                                .bg-blue {
                                    background-color: #1E9FFF;
                                }

                                .bg-yellow {
                                    background-color: #F7B824;
                                }

                                .bg-red {
                                    background-color: #FF5722;
                                }

                                .bg span {
                                    font-weight: bold;
                                    font-size: 30px;
                                    word-break: normal;
                                    white-space: pre-wrap;
                                }

                            </style>
                            <div class="row">
                                <div class="col-md-3 link">
                                    <a href="javascript:window.parent.addParentTab({href:contextPath + '/html/complex/datagrid.html',title:'数据表格'})">
                                        <div class="bg bg-green">
                                            <div class="fa fa-users fa-3x"></div>
                                            <span>数据表格</span>
                                        </div>
                                    </a>
                                </div>
                                <div class="col-md-3 link">
                                    <a href="javascript:window.parent.addParentTab({href:contextPath + '/html/complex/treegrid.html',title:'树形表格'})">
                                        <div class="bg bg-blue">
                                            <div class="fa fa-users fa-3x"></div>
                                            <span>树形表格</span>
                                        </div>
                                    </a>
                                </div>
                                <div class="col-md-3 link">
                                    <a href="javascript:window.parent.addParentTab({href:contextPath + '/html/complex/edatagrid.html',title:'可编辑表格'})">
                                        <div class="bg bg-yellow">
                                            <div class="fa fa-users fa-3x"></div>
                                            <span>可编辑表格</span>
                                        </div>
                                    </a>
                                </div>
                                <div class="col-md-3 link">
                                    <a href="javascript:window.parent.addParentTab({href:contextPath + '/html/form/input_index.html',title:'文本输入'})">
                                        <div class="bg bg-red">
                                            <div class="fa fa-users fa-3x"></div>
                                            <span>文本输入</span>
                                        </div>
                                    </a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="row no_gutter">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-sticky-note"></i> TopJUI前端框架
                        </div>
                        <div class="panel-body">
                            <ul>
                                <li>
                                    TopJUI前端框架，不用写JS代码的EasyUI!
                                </li>
                                <li>
                                    纯HTML调用EasyUI功能组件，专注你的后端业务开发！
                                </li>
                                <li>
                                    在线演示：<a href="http://demo.topjui.com" target="_blank">静态演示</a>，可整合不同后端代码
                                </li>
                                <li>
                                    开源下载：开源中国<a href="http://git.oschina.net/xvpindex/TopJUI" target="_blank">下载源代码</a>
                                </li>
                                <li>
                                    开发文档：<a href="http://www.topjui.com/system/index/index.html" target="_blank">在线开发文档</a> QQ交流群：593783047
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row no_gutter">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-newspaper-o"></i> 关于本系统
                        </div>
                        <table class="table table-hover">
                            <tbody>
                            <tr>
                                <td>
                                    系统名称：${sysConfig.cfgSystemName}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    系统版本：${sysConfig.cfgSystemVersion}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    技术架构：基于TopJUI + SSHM构建 <a href="http://www.zuoyour.com/emis.html" target="_blank">了解详情</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    合作洽谈：service@ewsd.cn
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    技术支持：<a href="http://www.zuoyour.com" target="_blank">湖南佐佑时代科技有限公司</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row no_gutter">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-edge"></i> 服务器信息
                        </div>
                        <div class="panel-body">
                            <ul>
                                <li>JDK运行版本：1.8.0_60</li>
                                <li>服务器运行时区：Asia/Shanghai</li>
                                <li>服务器系统名称：Linux</li>
                                <li>服务器系统架构：amd64</li>
                                <li>服务器系统版本：2.6.32-573.22.1.el6.x86_64</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>