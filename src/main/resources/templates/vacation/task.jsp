<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">所有任务列表</rapid:override>

<rapid:override name="body">
    <div class="layui-container-fluid">

        <blockquote class="layui-elem-quote">所有任务列表</blockquote>

        <table class="layui-table">
            <thead>
            <tr>
                <th>任务ID</th>
                <th>流程标题</th>
                <th>任务来路</th>
                <th>当前任务</th>
                <th>执行人</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.taskId}</td>
                    <td>${task.variables.get("businessTitle")}</td>
                    <td>${task.incomeing}</td>
                    <td>${task.taskName}</td>
                    <td>${task.assignee}</td>
                    <td>
                        <a class="layui-btn layui-btn-sm" href="/workflow/workflow/complete?id=${task.taskId}">完成</a>
                        <a class="layui-btn layui-btn-warm"
                           href="/workflow/workflow/graphics?taskId=${task.taskId}">流程图</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            <%--<a href="/workflow/workflow" class="btn btn-primary btn-lg active" role="button">流程模板</a>
            <a href="/workflow/workflow/deployed" class="btn btn-success btn-lg active" role="button">已部署流程</a>
            <a href="/workflow/workflow/started" class="btn btn-info btn-lg active" role="button">已启动流程</a>--%>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>