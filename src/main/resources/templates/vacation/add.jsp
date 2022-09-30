<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">启动流程</rapid:override>

<rapid:override name="body">
    <div class="layui-container">
        <div class="layui-row">
            <div class="layui-col-md12">

                <fieldset class="layui-elem-field layui-field-title">
                    <legend>启动流程</legend>
                </fieldset>
                <form class="layui-form layui-form-pane" action="">
                    <input type="hidden" name="procDefId" value="${param.procDefId}">

                    <div class="layui-form-item">
                        <label class="layui-form-label">流程名称</label>
                        <div class="layui-input-block">
                            <input type="text" name="processName" autocomplete="off" placeholder="请输入流程名称"
                                   class="layui-input" readonly="readonly"
                                   value="${param.processName}">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">流程标题</label>
                        <div class="layui-input-block">
                            <input type="text" name="businessTitle" autocomplete="off" placeholder="请输入流程标题"
                                   class="layui-input" required lay-verify="required"
                                   value="">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <button class="layui-btn" lay-submit lay-filter="startProcessForm">启动流程</button>
                        <a class="layui-btn layui-btn-danger"
                           href="javascript:window.parent.addParentTab({href:'/workflow/workflow/graphics?taskId=${leaveTaskBean.commonTaskDto.taskId}',title:'流程图 - ${taskBean.leave.title}'})"
                           target="_blank">
                            流程图
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
            var $ = layui.jquery
                    , form = layui.form
                    , layer = layui.layer
                    , laydate = layui.laydate;

            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value.length < 5) {
                        return '标题至少得5个字符啊';
                    }
                }
                , pass: [/(.+){6,12}$/, '密码必须6到12位']
                , content: function (value) {
                    layedit.sync(editIndex);
                }
            });

            //监听提交
            form.on('submit(startProcessForm)', function (data) {
                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    data: data.field,
                    url: '/workflow/workflow/start',
                    success: function (res) {
                        if (res.statusCode === 200) {
                            //updateSelectedTab(res.businessTitle, res.businessDetailUrl + '?taskId=' + res.taskId + '&businessTitle=' + res.businessTitle);
                            //window.location.href = res.businessDetailUrl + '?taskId=' + res.taskId + '&businessTitle=' + res.businessTitle;

                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);

                            updateSelectedTab(res.businessTitle, res.businessDetailUrl + '?taskId=' + res.taskId + '&businessTitle=' + res.businessTitle);
                            parent.parent.$('#index_tabs').iTabs('refresh');
                        } else {
                            layer.msg(res.message || res.statusCode, {shift: 6});
                        }
                    },
                    error: function (e) {
                        layer.msg('请求异常，请重试', {shift: 6});
                    }
                });
                return false;
            });

        });
    </script>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>