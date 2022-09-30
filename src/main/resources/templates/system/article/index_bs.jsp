<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/common/head-bootstrap.jsp" %>
    <title>新闻管理</title>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">

            <table id="dataTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>标题 <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/edit_bs',title:'新增文章'})"><i class="fa fa-plus-square"></i></a></th>
                    <th>发布人</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody class="tbody"></tbody>
            </table>
            <ul class="pagination" id="pagination"></ul>
        </div>
    </div>
</div>

<script>

    $('#table').bootstrapTable({
        url: '/system/article/getArticleList/0/20',
        columns: [{
            field: 'uuid',
            title: 'UUID'
        }, {
            field: 'title',
            title: '标题'
        }, {
            field: 'creator',
            title: '发布人'
        }, {
            field: 'createTime',
            title: '发布时间'
        }],
        pagination: true,
        checkbox: true
    });

    function test(){
        var selects = $("#dataTable").bootstrapTable('getSelections');
        var newSelects = $.parseJSON(JSON.stringify(selects));
        console.log(newSelects);
    }

    var page = 1, url = "/news/getPageSetData?code=news&page=pageNum&rows=" + rows,
            dataTableId = "#dataTable", paginationId = "#pagination";
    showPageList(page, url, dataTableId, paginationId, rows);

    // 更新局部页面
    function changeModel(data) {
        if (data != null) {
            $(dataTableId + " .tbody").html("");
            $.each(data.rows, function (index, item) { //遍历返回的json
                $(dataTableId + " .tbody").append(
                        '<tr>' +
                        '<td><a href="/news/detail/uuid/' + item.uuid + '" target="_blank">' + item.title + '</a></td>' +
                        '<td>' + item.creator + '</td>' +
                        '<td>' + item.createTime + '</td>' +
                        '<td>' +
                        '<a href="javascript:window.parent.addParentTab({href:\'/system/article/edit_bs?uuid=' + item.uuid + '\',title:\'' + item.title + '\'})"><i class="fa fa-pencil-square fa-2x"></i></a> ' +
                        '<a href="/system/article/delete_bs?uuid=' + item.uuid + '"><i class="fa fa-minus-circle fa-2x"></i></a>' +
                        '</td>' +
                        '</tr>'
                );
            });
        }
    }
</script>
</body>
</html>