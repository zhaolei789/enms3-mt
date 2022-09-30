<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/common/head-bootstrap.jsp" %>
    <title>文章编辑 - ${config_siteConfig.cfgCompanyName}</title>
</head>

<body>
<div class="container">

    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">

            <form id="form" class="form-horizontal" method="post" action="/system/article/update">
                <input type="hidden" name="uuid" value="${article.uuid}">
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="title">标题</label>
                    <div class="col-sm-10 col-md-10 col-lg-10">
                        <input type="text" id="title" class="form-control" name="title" value="${article.title}" placeholder="文章标题">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="cid">文章栏目</label>
                    <div class="col-sm-4 col-md-4 col-lg-4">
                        <input type="text" id="cid" class="form-control" name="cid" value="${article.cid}"
                               placeholder="文章栏目">
                    </div>
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="status">发布状态</label>
                    <div class="col-sm-4 col-md-4 col-lg-4">
                        <input type="text" id="status" class="form-control" name="status" value="${article.status}"
                               placeholder="发布状态">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="keywords">关键词</label>
                    <div class="col-sm-4 col-md-4 col-lg-4">
                        <input type="text" id="keywords" class="form-control" name="keywords" value="${article.keywords}"
                               placeholder="关键词">
                    </div>
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="recommend">头条推荐</label>
                    <div class="col-sm-4 col-md-4 col-lg-4">
                        <input type="text" id="recommend" class="form-control" name="recommend" value="${article.recommend}"
                               placeholder="头条推荐">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="description">文章描述</label>
                    <div class="col-sm-10 col-md-10 col-lg-10">
                                <textarea name="description" id="description" class="form-control" rows="5"
                                          placeholder="文章描述">${article.description}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="thumbnail">封面缩略图</label>
                    <div class="col-sm-8 col-md-8 col-lg-8">
                        <input type="text" id="thumbnail" class="form-control" name="thumbnail" value="${article.thumbnail}" placeholder="封面缩略图">
                    </div>
                    <div class="col-sm-2 col-md-2 col-lg-2">
                        <button id="imgUpload" type="button" class="btn btn-danger">选择图片</button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label" for="contentEditor">内容</label>
                    <div class="col-sm-10 col-md-10 col-lg-10">
                        <textarea id="contentEditor" name="content" style="height:100px;">${article.content}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 col-md-2 col-lg-2 control-label"></label>
                    <div class="col-sm-4 col-md-4 col-lg-4">
                        <button type="submit" class="btn btn-primary">保存</button>
                        <button type="button" id="resetBtn" class="btn btn-danger">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>

<script type="text/javascript" src="/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${staticServer}/static/ueditor/ueditor.all.js"></script>

<script type="text/plain" id="uploadUeditor"></script>
<script type="text/javascript">
    //http://www.cnblogs.com/stupage/p/3145353.html
    //重新实例化一个编辑器，上传独立使用，防止在上面的editor编辑器中显示上传的图片或者文件
    var uploadUeditor = UE.getEditor("uploadUeditor");
    uploadUeditor.ready(function () {
        //设置编辑器不可用
        uploadUeditor.setDisabled();
        //隐藏编辑器，因为不会用到这个编辑器实例，所以要隐藏
        uploadUeditor.hide();
        //侦听图片上传
        uploadUeditor.addListener('afterConfirmUploadedImage', function (t, arg) {
            //将地址赋值给相应的input
            $("#thumbnail").attr("value", arg[0].src);
            //图片预览
            $("#preview").attr("src", arg[0].src);
        })
        //侦听文件上传
        uploadUeditor.addListener('afterConfirmUploadedFile', function (t, arg) {
            $("#file").attr("value", arg[0].url);
        })
    });

    //弹出图片上传的对话框
    function upImage() {
        var myImage = uploadUeditor.getDialog("insertimage");
        myImage.open();
    }

    //弹出文件上传的对话框
    function upFiles() {
        var myFiles = uploadUeditor.getDialog("attachment");
        myFiles.open();
    }

    $("#fileUpload").on("click", function () {
        //upFiles();
    });

    $("#imgUpload").on("click", function () {
        upImage();
    });
</script>

<script type="text/javascript">
    $(function () {
        //UE.delEditor('contentEditor');
        <!-- 实例化编辑器 -->
        var ue = UE.getEditor('contentEditor', {
            toolbars : [['fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', '|',
                'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist',
                'insertunorderedlist', 'lineheight', '|',
                'horizontal', 'spechars', 'map', 'paragraph', 'fontfamily', 'fontsize', 'insertcode', '|',
                'indent', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
                'link', 'unlink', '|', 'emotion', 'attachment', 'simpleupload', 'insertimage', '|', 'preview']],
            autoHeightEnabled: true,
            autoFloatEnabled: true
        });
    });
</script>

<script type="text/javascript">
    $(function () {
        // 装载表单数据
        //$("#form").loadData("#form", "/system/article/getDetailByUuid?uuid=" + $.getUrlParam("uuid"), {});


        $('#resetBtn').click(function () {
            $('#form').formValidation('disableSubmitButtons', false).formValidation('resetForm', true);
        });

        $('#form').formValidation({
            message: 'This value is not valid',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                title: {
                    message: 'The userNameId is not valid',
                    validators: {
                        notEmpty: {
                            message: '标题不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '标题必须在6-30个字符之间'
                        }
                    }
                },
                cid: {
                    message: 'The cid is not valid',
                    validators: {
                        notEmpty: {
                            message: '请选择文章栏目'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        different: {
                            field: 'username',
                            message: '密码不能和用户名相同'
                        }
                    }
                },
                rePassword: {
                    validators: {
                        notEmpty: {
                            message: '重复密码不能为空'
                        }
                    }
                },
                mobile: {
                    message: 'The mobile is not valid',
                    validators: {
                        notEmpty: {
                            message: '手机号码不能为空'
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
                email: {
                    message: 'The email is not valid',
                    validators: {
                        notEmpty: {
                            message: '电子邮箱不能为空'
                        },
                        emailAddress: {
                            message: '请输入正确的电子邮箱地址'
                        }
                    }
                }, verifyCode: {
                    message: 'The verifyCode is not valid',
                    validators: {
                        notEmpty: {
                            message: '认证码不能为空'
                        }
                    }
                }
            }
        });
    });
</script>

</body>
</html>