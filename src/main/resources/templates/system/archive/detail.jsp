<%@ page import="cn.ewsd.common.utils.FileUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../../common/common/head-bootstrap.jsp" %>
<script type="text/javascript"
        src="${staticServer}/static/jquery/plugins/jquery.imgAutoSize.js"></script>
<script type="text/javascript">
    $(function ($) {
        $('.content').imgAutoSize(30);
    });
</script>
<div class="visible-lg-block">
    <div class="container">
        <div class="row margin-top-10">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title text-center">${archive.title}</h3>
                    </div>
                    <div class="list-group text-center">
                        <div class="list-group-item">
                            <span><i class="fa fa-user"></i> ${archive.creator}</span>
                            <span><i class="fa fa-clock-o"></i> ${fn:substring(archive.createTime,0,19)}</span>
                        </div>
                    </div>
                    <div class="panel-body content">${archive.description}</div>
                    <div class="list-group">
                        <c:forEach items="${attachments}" var="attachment" varStatus="status">
                            <div class="list-group-item">
                                <span>
                                    <i class="fa fa-paperclip fa-fw"></i>
                                    <a href="${attachment.filePath}">${attachment.fileName}</a>
                                    ${FileUtils.convertFileSize(attachment.fileSize)}
                                </span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid visible-xs-block visible-sm-block visible-md-block">
    <div class="row margin-top-10">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">${archive.title}</h3>
                </div>
                <div class="list-group text-center">
                    <div class="list-group-item">
                        <span><i class="fa fa-user"></i> ${archive.creator}</span>
                        <span><i class="fa fa-clock-o"></i> ${fn:substring(archive.createTime,0,19)}</span>
                    </div>
                </div>
                <div class="panel-body content">${archive.description}</div>
                <div class="list-group">
                    <c:forEach items="${attachments}" var="attachment" varStatus="status">
                        <div class="list-group-item">
                                <span>
                                    <i class="fa fa-paperclip fa-fw"></i>
                                    <a href="${attachment.filePath}">${attachment.fileName}</a>
                                    ${FileUtils.convertFileSize(attachment.fileSize)}
                                </span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>