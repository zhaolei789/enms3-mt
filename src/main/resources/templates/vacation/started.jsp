<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">已启动流程</rapid:override>

<rapid:override name="body">
    <div class="layui-container-fluid">

        <blockquote class="layui-elem-quote">已启动流程</blockquote>

        <table class="layui-table">
            <thead>
            <tr>
                <th>流程定义ID</th>
                <th>流程实例ID</th>
                <th>流程名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="temp" items="${list}">
                <tr>
                    <td>${temp.processDefinitionId}</td>
                    <td>${temp.id}</td>
                    <td>${temp.processDefinitionName}</td>
                    <td>
                        <a class="layui-btn layui-btn-sm layui-btn-warm" href="/workflow/workflow/graphics?instanceId=${temp.id}">流程图</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            <%--<a href="" class="btn btn-primary btn-lg active" role="button">流程模板</a>
            <a href="deployed" class="btn btn-success btn-lg active" role="button">已部署流程</a>
            <a href="task" class="btn btn-info btn-lg active" role="button">任务列表</a>--%>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>