<%@ page import="cn.ewsd.system.common.Common" %>
<%@ page import="cn.ewsd.common.utils.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div style='margin:0 auto;display:none;'>
    <img src="/app/system/public/image/logo_300.png">
</div>

<!-- 导航条 -->
<style>
    .ewsd-navbar-blue {
        border-bottom: solid 2px #195E9E;
    }
</style>
<nav class="navbar navbar-fixed-top navbar-default ewsd-navbar" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/system/member/controlPanel/api">
                <img src="/app/system/public/image/logo_home.png" width="170" height="50">
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/system/member/controlPanel/api">控制面板</a>
                </li>
                <li>
                    <a href="/system/member/controlPanel/api">业务系统</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/system/member/controlPanel/api">
                        <img class="user_avatar" src="/static/images/avatar/1.png" alt="会员头像">
                        <%=Common.getSessionByName(request, "memberUserName")%>
                    </a>
                </li>
                <li>
                    <a href="/system/member/logout/api"><i class="fa fa-sign-out"></i> 注销</a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>