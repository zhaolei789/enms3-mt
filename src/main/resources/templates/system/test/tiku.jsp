<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ include file="../../common/common/head-bootstrap.jsp" %>
    <title>测试题库</title>
</head>

<body>
<div class="container">

    <div class="row clearfix top bottom">
        <div class="col-md-12 column">
            <div class="btn-group">
                <button class="btn btn-sm btn-success" type="button"
                        data-toggle="topjui-dialog"
                        data-remote="/system/test/edit"
                        data-mtitle="新增文章"><em class="glyphicon glyphicon-plus"></em> 新增
                </button>
                <button class="btn btn-sm btn-info" type="button"><em class="glyphicon glyphicon-search"></em> 查询
                </button>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>标题</th>
                    <th>创建人</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageSet.list}" var="list">
                    <tr class="warning">
                        <td>${list.uuid}</td>
                        <td>${list.title}</td>
                        <td>${list.creator}</td>
                        <td>${list.createTime}</td>
                        <td>
                            <a href="#" data-toggle="topjui-dialog" data-remote="/system/test/edit?uuid=${list.uuid}"
                               data-mtitle="编辑文章">编辑</a>
                            <a href="">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <ul class="pagination">
                <li>
                    <a href="#">上一页</a>
                </li>
                <li>
                    <a href="#">1</a>
                </li>
                <li>
                    <a href="#">2</a>
                </li>
                <li>
                    <a href="#">3</a>
                </li>
                <li>
                    <a href="#">4</a>
                </li>
                <li>
                    <a href="#">5</a>
                </li>
                <li>
                    <a href="#">下一页</a>
                </li>
            </ul>
        </div>
    </div>

</div>

</body>
</html>
