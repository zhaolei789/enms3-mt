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
    <title></title>
    <link type="text/css" href="${staticServer}/static/public/css/font.css" rel="stylesheet"/>
    <link type="text/css" href="${staticServer}/static/public/css/main.css" rel="stylesheet"/>
    <!-- TopJUI框架样式 -->
    <!--<link type="text/css" href="${staticServer}/topjui/css/topjui.core.min.css" rel="stylesheet">
    <link type="text/css" href="${staticServer}/topjui/themes/default/topjui.blue.css" rel="stylesheet" id="dynamicTheme"/>-->
    <!-- FontAwesome字体图标 -->
    <link type="text/css" href="${staticServer}/static/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- layui框架样式 -->
    <link type="text/css" href="${staticServer}/static/plugins/layui/css/layui.css" rel="stylesheet"/>
    <!-- jQuery相关引用 -->
    <script type="text/javascript" src="${staticServer}/static/plugins/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/jquery/jquery.cookie.js"></script>
    <!-- TopJUI框架配置 -->
    <script type="text/javascript" src="${staticServer}/static/public/js/topjui.config.js"></script>
    <!-- TopJUI框架核心-->
    <script type="text/javascript" src="${staticServer}/static/topjui/js/topjui.core.min.js"></script>
    <!-- TopJUI中文支持 -->
    <script type="text/javascript" src="${staticServer}/static/topjui/js/locale/topjui.lang.zh_CN.js"></script>
    <!-- layui框架js -->
    <script src="${staticServer}/static/plugins/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${staticServer}/static/public/js/topjui.im.js" charset="utf-8"></script>
    <style>
        body {
            margin: 10px;
        }

        blockquote p {
            padding: 5px;
        }

        .layui-table {
            margin-top: 0 !important;
        }

        .layui-elem-quote {
            margin-bottom: 0 !important;
        }

        .layui-table {
            margin-top: 0;
            border-left: 5px solid #e2e2e2 !important;
        }

        .title .icon-new1 {
            margin-left: 10px;
            color: #f00;
        }
    </style>
</head>

<body>
<%--${user.userName}--%>
<div class="layui-container-fluid">
    <div class="panel_box row">
        <div class="panel col">
            <a href="javascript:;" data-url="page/message/message.html">
                <div class="panel_icon">
                    <i class="layui-icon" data-icon=""></i>
                </div>
                <div class="panel_word newMessage">
                    <span>5</span>
                    <cite>新消息</cite>
                </div>
            </a>
        </div>
        <div class="panel col">
            <a href="javascript:;" data-url="page/user/allUsers.html">
                <div class="panel_icon" style="background-color:#FF5722;">
                    <i class="iconfont icon-dongtaifensishu" data-icon="icon-dongtaifensishu"></i>
                </div>
                <div class="panel_word userAll">
                    <span>3</span>
                    <cite>新增人数</cite>
                </div>
            </a>
        </div>
        <div class="panel col">
            <a href="javascript:;" data-url="page/user/allUsers.html">
                <div class="panel_icon" style="background-color:#009688;">
                    <i class="layui-icon" data-icon=""></i>
                </div>
                <div class="panel_word userAll">
                    <span>3</span>
                    <cite>用户总数</cite>
                </div>
            </a>
        </div>
        <div class="panel col">
            <a href="javascript:;" data-url="page/img/images.html">
                <div class="panel_icon" style="background-color:#5FB878;">
                    <i class="layui-icon" data-icon=""></i>
                </div>
                <div class="panel_word imgAll">
                    <span>31</span>
                    <cite>图片总数</cite>
                </div>
            </a>
        </div>
        <div class="panel col">
            <a href="javascript:;" data-url="page/news/newsList.html">
                <div class="panel_icon" style="background-color:#F7B824;">
                    <i class="iconfont icon-wenben" data-icon="icon-wenben"></i>
                </div>
                <div class="panel_word waitNews">
                    <span>13</span>
                    <cite>待审核文章</cite>
                </div>
            </a>
        </div>
        <div class="panel col max_panel">
            <a href="javascript:;" data-url="page/news/newsList.html">
                <div class="panel_icon" style="background-color:#2F4056;">
                    <i class="iconfont icon-text" data-icon="icon-text"></i>
                </div>
                <div class="panel_word allNews">
                    <span>30</span>
                    <em>文章总数</em>
                    <cite>文章列表</cite>
                </div>
            </a>
        </div>
    </div>

    <div class="layui-row layui-col-space10">
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">待办流程</blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
                <c:forEach items="${commonTaskDtos}" var="commonTaskDto">
                    <tr>
                        <td>
                            <a href="javascript:window.parent.addParentTab({href:'${commonTaskDto.variables.get("businessDetailUrl")}?taskId=${commonTaskDto.taskId}',title:'${commonTaskDto.variables.get("businessTitle")}'})">${commonTaskDto.variables.get("businessTitle")}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">公告通知</blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
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
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">知识文档</blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
                <c:forEach items="${archives}" var="archive">
                    <tr>
                        <td>
                            <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/archive/detail?uuid=${archive.uuid}',title:'${archive.title}'})">${archive.title}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="layui-row layui-col-space10">
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">公司新闻</blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
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
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">TopJUI前端框架<i class="iconfont icon-new1"></i></blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
                <tr>
                    <td>
                        服务器 IP 地址：${hostAddress}
                    </td>
                </tr>
                <tr>
                    <td>
                        JDK 运行版本：${sysProperty.getProperty("java.version")}
                    </td>
                </tr>
                <tr>
                    <td>
                        服务器运行时区：${sysProperty.getProperty("user.timezone")}
                    </td>
                </tr>
                <tr>
                    <td>
                        服务器系统名称：${sysProperty.getProperty("os.name")}
                    </td>
                </tr>
                <tr>
                    <td>
                        服务器系统架构：${sysProperty.getProperty("os.arch")}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="layui-col-md4">
            <blockquote class="layui-elem-quote title">关于本系统<i class="iconfont icon-new1"></i></blockquote>
            <table class="layui-table" lay-skin="line">
                <colgroup>
                    <col>
                    <col width="110">
                </colgroup>
                <tbody class="hot_news">
                <tr>
                    <td>
                        系统名称：${sysConfig.systemName}
                    </td>
                </tr>
                <tr>
                    <td>
                        系统版本：${sysConfig.systemVersion}
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

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?535aba9c920624592e3386b639507c1d";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</body>
</html>