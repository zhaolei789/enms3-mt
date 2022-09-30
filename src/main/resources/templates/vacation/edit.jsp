<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<rapid:override name="title">我的流程</rapid:override>

<rapid:override name="body">
    <div class="container">

        <div class="row">
            <div class="col-md-12">
                <form id="form" class="form-horizontal" method="post" action="/workflow/chuanYue/update">
                    <input type="hidden" name="uuid">
                    <div class="form-group">
                        <label class="col-lg-2 control-label" for="title">标题</label>

                        <div class="col-lg-10">
                            <input type="text" id="title" class="form-control" name="title" value="${chuanYue.title}"
                                   placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label" for="creator">创建人</label>

                        <div class="col-lg-4">
                            <input type="text" id="creator" class="form-control" name="creator" placeholder="创建人">
                        </div>

                        <label class="col-lg-2 control-label" for="level">等级</label>

                        <div class="col-lg-4">
                            <input type="text" id="level" class="form-control" name="level" placeholder="等级">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label" for="content">留言内容</label>

                        <div class="col-lg-10">
                        <textarea name="content" id="content" class="form-control" rows="7"
                                  placeholder="留言内容"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label"></label>
                        <div class="col-lg-4">
                            <button type="submit" class="btn btn-primary">提交留言</button>
                            <button type="button" class="btn btn-danger" id="resetBtn">重置表单</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>

    <script type="text/javascript">
        $(function () {
            // 装载表单数据
            $("form").loadData("#form", "/oa/chuanYue/getDetailByUuid?uuid=" + $.getUrlParam("uuid"), {});

            // 重置表单
            $('#resetBtn').click(function () {
                $('#form').formValidation('disableSubmitButtons', false).formValidation('resetForm', true);
            });

            // 验证参数
            var options = {
                message: 'This value is not valid',
                icon: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    title: {
                        message: 'The username is not valid',
                        validators: {
                            notEmpty: {
                                message: '标题不能为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 50,
                                message: '长度为2-50个汉字'
                            }
                        }
                    },
                    tel: {
                        message: 'The username is not valid',
                        validators: {
                            notEmpty: {
                                message: '联系电话不能为空'
                            },
                            stringLength: {
                                min: 11,
                                max: 11,
                                message: '请输入11位手机号码'
                            },
                            regexp: {
                                regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                                message: '请输入正确的手机号码'
                            }
                        }
                    },
                    content: {
                        message: 'The username is not valid',
                        validators: {
                            notEmpty: {
                                message: '留言内容不能为空'
                            },
                            stringLength: {
                                min: 10,
                                max: 500,
                                message: '长度为10-500个汉字'
                            }
                        }
                    }
                }
            };

            //验证并提交表单
            $("#form").ajaxSubmitWithVerify(options);
        });
    </script>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>