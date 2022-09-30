<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">流程模板</rapid:override>

<rapid:override name="body">
    <div class="layui-container-fluid">

        <blockquote class="layui-elem-quote">流程模板</blockquote>

        <table class="layui-table">
            <colgroup>
                <col>
                <col width="150">
            </colgroup>
            <thead>
            <tr>
                <th>流程定义</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="temp" items="${list}">
                <tr>
                    <td>${temp }</td>
                    <td><a class="layui-btn layui-btn-sm layui-btn-danger" href="/workflow/workflow/deploy?processName=${temp}">部署流程</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            <%--<a href="/workflow/workflow/deployed" class="btn btn-primary btn-lg active" role="button">已部署流程</a>
            <a href="/workflow/workflow/started" class="btn btn-success btn-lg active" role="button">已启动流程</a>
            <a href="/workflow/workflow/task" class="btn btn-info btn-lg active" role="button">任务列表</a>--%>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>