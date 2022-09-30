<%@ page import="cn.ewsd.system.common.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
    .selectbox {
        margin: 10px 10px;
        padding-left: 25px;
    }

    .selectbox div {
        float: left;
    }

    .search {
        margin-top: 10px;
        padding-left: 55px;
    }

    .selectbox .select-bar {
        padding: 0 20px;
    }

    .selectbox .select-bar select {
        width: 200px;
        height: 280px;
        border: 1px #95B8E7 solid;
        padding: 4px;
    }

    .btn-bar {
    }

    .btn-bar p {
        margin-top: 16px;
    }

    .btn-bar p .btn {
        width: 50px;
        height: 30px;
        cursor: pointer;
        font-size: 14px;
    }
</style>
<script type="text/javascript">
    $(function () {
        //移到右边
        $('.add').click(function () {
            var index = $('.add').index(this);
            //先判断是否有选中
            if (!$('.unSelectedUser:eq(' + index + ') option').is(":selected")) {
                alert("请选择需要移动的选项")
            } else {
                //获取选中的选项，删除并追加给对方
                $('.unSelectedUser:eq(' + index + ') option:selected').appendTo('.selectedUser:eq(' + index + ')');
            }
        });

        //移到左边
        $('.remove').click(function () {
            var index = $('.remove').index(this);
            //先判断是否有选中
            if (!$('.selectedUser:eq(' + index + ') option').is(":selected")) {
                alert("请选择需要移动的选项")
            }
            else {
                $('.selectedUser:eq(' + index + ') option:selected').appendTo('.unSelectedUser:eq(' + index + ')');
            }
        });

        //全部移到右边
        $('.add_all').on('click', function () {
            var index = $('.add_all').index(this);
            //获取全部的选项,删除并追加给对方
            $('.unSelectedUser:eq(' + index + ') option').appendTo('.selectedUser:eq(' + index + ')');
        });

        //全部移到左边
        $('.remove_all').click(function () {
            var index = $('.remove_all').index(this);
            $('.selectedUser:eq(' + index + ') option').appendTo('.unSelectedUser:eq(' + index + ')');
        });

        //双击选项
        $('.unSelectedUser').dblclick(function () { //绑定双击事件
            //获取全部的选项,删除并追加给对方
            $("option:selected", this).appendTo('.selectedUser'); //追加给对方
        });

        //双击选项
        $('.selectedUser').dblclick(function () {
            $("option:selected", this).appendTo('.unSelectedUser');
        });

    });

    function showUser(newValue, oldValue) {
        var t = $('#localUnitId').combotree('tree');	// 获取树对象
        var n = t.tree('getSelected');		// 获取选择的节点
        $.getJSON("/ucenter/user/getList", {
            codeSetId: n.codesetid,
            codeItemId: n.id
        }, function (data) {
            $(".unSelectedUser").html("");
            for (var i = 0; i < data.length; i++) {
                $(".unSelectedUser").append('<option value="' + data[i].userNameId + '">' + data[i].userName + '</option>');
            }
        });
    }
</script>

<div class="search">
    <input type="text" id="localUnitId" name="localUnitId" value="<%= session.getAttribute(Constants.FIRST_DEPT)%>"
           data-toggle="topjui-combotree"
           data-options="url:'${ctx}/System/Organization/getListByPid?codeSetId=@k&pid=2',
           expandUrl:'${ctx}/System/Organization/getListByPid?pid={id}',
           width:'320',
           onChange:showUser">
</div>

<div class="selectbox">
    <input type="hidden" name="moduleNameId" value="<%= request.getParameter("moduleNameId")%>">
    <input type="hidden" name="puuid" value="<%= request.getParameter("puuid")%>">
    <div class="select-bar">
        <select name="unSelectedUser" multiple="multiple" class="unSelectedUser">
            <c:forEach items="${users}" var="user">
                <option value="${user.userNameId}">${user.userName}</option>
            </c:forEach>
        </select>
    </div>

    <div class="btn-bar">
        <br/><br/><br/><br/>
        <a href="#" data-toggle="topjui-linkbutton add" data-options="iconCls:'fa fa-plus'">添加选中</a>
        <br/><br/>
        <a href="#" data-toggle="topjui-linkbutton add_all" data-options="iconCls:'fa fa-plus'">添加全部</a>
        <br/><br/>
        <a href="#" data-toggle="topjui-linkbutton remove" data-options="iconCls:'fa fa-trash'">移除选中</a>
        <br/><br/>
        <a href="#" data-toggle="topjui-linkbutton remove_all" data-options="iconCls:'fa fa-trash'">称除全部</a>
    </div>

    <div class="select-bar">
        <select name="selectedUser" multiple="multiple" class="selectedUser"></select>
    </div>
</div>
<div style="text-align:center;"></div>