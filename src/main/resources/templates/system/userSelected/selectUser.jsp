<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
    .selectbox {
        width: 500px;
        height: 220px;
        margin: 10px auto;
    }

    .selectbox div {
        float: left;
    }

    .selectbox .select-bar {
        padding: 0 20px;
    }

    .selectbox .select-bar select {
        width: 150px;
        height: 200px;
        border: 1px #A0A0A4 solid;
        padding: 4px;
        font-size: 14px;
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
        $('#add').click(function () {
            //先判断是否有选中
            if (!$("#unSelectedUser option").is(":selected")) {
                alert("请选择需要移动的选项")
            } else {
                //获取选中的选项，删除并追加给对方
                $('#unSelectedUser option:selected').appendTo('#selectedUser');
            }
        });

        //移到左边
        $('#remove').click(function () {
            //先判断是否有选中
            if (!$("#selectedUser option").is(":selected")) {
                alert("请选择需要移动的选项")
            }
            else {
                $('#selectedUser option:selected').appendTo('#unSelectedUser');
            }
        });

        //全部移到右边
        $('#add_all').click(function () {
            //获取全部的选项,删除并追加给对方
            $('#unSelectedUser option').appendTo('#selectedUser');
        });

        //全部移到左边
        $('#remove_all').click(function () {
            $('#selectedUser option').appendTo('#unSelectedUser');
        });

        //双击选项
        $('#unSelectedUser').dblclick(function () { //绑定双击事件
            //获取全部的选项,删除并追加给对方
            $("option:selected", this).appendTo('#selectedUser'); //追加给对方
        });

        //双击选项
        $('#selectedUser').dblclick(function () {
            $("option:selected", this).appendTo('#unSelectedUser');
        });

    });
</script>

<div class="selectbox">
    <input type="text" name="moduleNameId" value="<%= request.getParameter("moduleNameId")%>">
    <input type="text" name="puuid" value="<%= request.getParameter("puuid")%>">
    <div class="select-bar">
        <select name="unSelectedUser" multiple="multiple" id="unSelectedUser">
            <c:forEach items="${users}" var="user">
                <option value="${user.userNameId}">${user.userName}</option>
            </c:forEach>
        </select>
    </div>

    <div class="btn-bar">
        <br/><br/>
        <a href="#" id="add" data-toggle="topjui-linkbutton" data-options="iconCls:'fa fa-plus'">添加选中</a>
        <br/><br/>
        <a href="#" id="add_all" data-toggle="topjui-linkbutton" data-options="iconCls:'fa fa-plus'">添加全部</a>
        <br/><br/>
        <a href="#" id="remove" data-toggle="topjui-linkbutton" data-options="iconCls:'fa fa-trash'">移除选中</a>
        <br/><br/>
        <a href="#" id="remove_all" data-toggle="topjui-linkbutton" data-options="iconCls:'fa fa-trash'">称除全部</a>
    </div>

    <div class="select-bar">
        <select name="selectedUser" multiple="multiple" id="selectedUser"></select>
    </div>
</div>
<div style="text-align:center;"></div>