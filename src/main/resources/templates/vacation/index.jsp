<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">我的流程</rapid:override>

<rapid:override name="body">
    <div class="layui-container-fluid">
        <%--<div class="layui-btn-group demoTable">
            <button class="layui-btn" data-type="getCheckData">新增</button>
            <button class="layui-btn layui-btn-normal" data-type="getCheckLength">编辑</button>
            <button class="layui-btn layui-btn-danger" data-type="isAll">删除</button>
        </div>--%>

        <div class="layui-row">
            <div class="layui-col-md12">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>任务ID</th>
                        <th style="display: none;">业务ID</th>
                        <th>流程标题</th>
                        <th>任务来路</th>
                        <th>当前任务</th>
                        <th>执行人</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="commonTaskDto" items="${commonTaskDtos}">
                        <tr>
                            <td>${commonTaskDto.taskId}</td>
                            <td style="display: none;">${commonTaskDto.businessKey}</td>
                            <td>${commonTaskDto.variables.get("businessTitle")}</td>
                            <td>${commonTaskDto.incomeing}</td>
                            <td>${commonTaskDto.taskName}</td>
                            <td>${commonTaskDto.assignee}</td>
                            <td>
                                <a class="layui-btn layui-btn-sm" href="${commonTaskDto.variables.get("businessDetailUrl")}?taskId=${commonTaskDto.taskId}">流程详情</a>
                                <a class="layui-btn layui-btn-sm layui-btn-warm" href="javascript:window.parent.addParentTab({href:'/workflow/workflow/graphics?instanceId=${commonTaskDto.executionId}',title:'流程图 - ${commonTaskDto.variables.get("businessTitle")}'})">流程图</a>
                                <a class="layui-btn layui-btn-sm layui-btn-danger" href="/workflow/workflow/deleteStartedProcess?instanceId=${commonTaskDto.executionId}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>