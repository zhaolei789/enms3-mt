<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<rapid:override name="title">流程处理</rapid:override>

<rapid:override name="body">
    <div class="layui-container">
        <div class="layui-row">
            <div class="layui-col-md12">

                <fieldset class="layui-elem-field layui-field-title">
                    <legend>流程处理</legend>
                </fieldset>
                <form class="layui-form layui-form-pane" method="post" action="/workflow/leave/doTask">
                    <input type="hidden" name="taskId" value="${commonTaskDto.taskId}">

                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">审批意见</label>
                        <div class="layui-input-block">
                        <textarea name="comment" id="comment" placeholder="请输入提交或审批信息" class="layui-textarea"
                                  required lay-verify="required"></textarea>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <c:forEach items="${commonTaskDto.outcomeList}" var="outcome">
                            <button class="layui-btn layui-btn-danger" lay-submit lay-filter="processDeal"
                                    value="${outcome}">${outcome}</button>
                            <%--<input type="submit" class="layui-btn" name="outcome" value="${outcome}"/>--%>
                        </c:forEach>
                        <a class="layui-btn layui-btn-warm"
                           href="javascript:window.parent.addParentTab({href:'/workflow/workflow/graphics?taskId=${commonTaskDto.taskId}',title:'流程图'})"
                           target="_blank">
                            流程图
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <div class="layui-row">
            <div class="layui-col-md12">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>审批人</th>
                        <th>批注内容</th>
                        <th>处理时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="commentBean" items="${commentBeanList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${commentBean.userName}</td>
                            <td>${commentBean.comment.fullMessage}</td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${commentBean.comment.time}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
            var $ = layui.jquery
                    , form = layui.form
                    , layer = layui.layer
                    , laydate = layui.laydate;

            //日期
            laydate.render({
                elem: '#beginTime'
            });
            laydate.render({
                elem: '#endTime'
            });

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

            $('#procDealDialog').on('click', function () {
                //多窗口模式，层叠置顶
                layer.open({
                    type: 2 //此处以iframe举例
                    , title: '流程处理'
                    , area: ['800px', '500px']
                    , shade: 0
                    , maxmin: true
                    , content: '/workflow/workflow/processDeal'
                    , zIndex: layer.zIndex //重点1
                    , success: function (layero) {
                        layer.setTop(layero); //重点2
                    }
                    , end: function () {

                    }
                });
                return false;
            });

            //监听提交
            form.on('submit(processDeal)', function (data) {
                data.field.outcome = data.elem.value;
                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    data: data.field,
                    url: '/workflow/leave/doTask',
                    success: function (res) {
                        if (res.statusCode === 200) {
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);
                            //parent.location.reload();
                            updateSelectedTab("待办流程", "/workflow/workflow/toDoList");
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