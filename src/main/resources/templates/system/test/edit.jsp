<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="form-horizontal" role="form">
    <div class="form-group">
        <label for="title" class="col-sm-2 control-label">标题</label>
        <div class="col-sm-10">
            <input type="text" id="title" class="form-control" name="title" value="${article.title}" />
        </div>
    </div>
    <div class="form-group">
        <label for="content" class="col-sm-2 control-label">内容</label>
        <div class="col-sm-10">
            <input type="text" id="content" class="form-control" name="content" value="${article.content}" />
        </div>
    </div>
</form>