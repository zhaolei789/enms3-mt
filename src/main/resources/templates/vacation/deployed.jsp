<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">已部署流程</rapid:override>

<rapid:override name="body">
    <div class="layui-container-fluid">

        <blockquote class="layui-elem-quote">已部署流程</blockquote>

        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col>
                <col width="400">
            </colgroup>
            <thead>
            <tr>
                <th>流程定义ID</th>
                <th>流程名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="temp" items="${list}">
                <tr>
                    <td>${temp.id}</td>
                    <td>${temp.name}</td>
                    <td>
                            <%--<a href="/workflow/workflow/start?id=${temp.id}">直接启动流程</a>--%>
                            <%--<a href="javascript:window.parent.addParentTab({href:'/workflow/workflow/add?procDefId=${temp.id}&processName=${temp.name}',title:'${temp.name}'})">启动流程</a>--%>
                        <a class="layui-btn layui-btn-sm" id="startProcess" href="javascript:void(0)"
                           data-proc-def-id="${temp.id}"
                           data-process-name="${temp.name}">启动流程</a>
                        <a class="layui-btn layui-btn-sm layui-btn-warm"
                           href="javascript:window.parent.addParentTab({href:'/workflow/vacation/generateImageByProcessDefinitionId?processDefinitionId=${temp.id}',title:'流程图 - ${temp.name}'})">流程图</a>
                        <a class="layui-btn layui-btn-sm layui-btn-danger"
                           href="/workflow/vacation/delete?deploymentId=${temp.deploymentId}">删除流程</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

            <%--<a href="" class="btn btn-primary btn-lg active" role="button">流程模板</a>
            <a href="started" class="btn btn-success btn-lg active" role="button">已启动流程</a>
            <a href="task" class="btn btn-info btn-lg active" role="button">任务列表</a>--%>
    </div>

    <script>
        layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
            var $ = layui.jquery
                    , layer = layui.layer;

            $('#startProcess').on('click', function () {
                var data = $(this).data();
                //多窗口模式，层叠置顶
                layer.open({
                    type: 2 //此处以iframe举例
                    , title: '启动流程'
                    , area: ['600px', '300px']
                    , shade: 0
                    , maxmin: true
                    , content: '/workflow/workflow/add?procDefId=' + data.procDefId + '&processName=' + data.processName
                    , zIndex: layer.zIndex //重点1
                    , success: function (layero) {
                        layer.setTop(layero); //重点2
                    }
                    , end: function () {

                    }
                });
                return false;
            });

        });
    </script>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>