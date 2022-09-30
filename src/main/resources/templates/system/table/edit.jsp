<%@ page language="java" pageEncoding="UTF-8" %>
<input type="hidden" id="uuid" name="uuid">
<input type="hidden" name="password">
<table class="editTable">
    <tr>
        <td class="label">所属模块</td>
        <td>
            <input type="text" name="puuid" data-toggle="topjui-combotreegrid"
                   data-options="required:true,
                   expandAll:false,
                   idField:'id',
                   treeField:'text',
                   width:450,
                   panelHeight:250,
                   fitColumns:true,
                   columns:[[
                       {field:'text',title:'模块名称',width:100}
                   ]],
                   url:'${ctx}/system/module/getListById?id=1',
                   expandUrl:'${ctx}/system/module/getListByPid?pid={id}',
                   getFatherIdsUrl:'${ctx}/system/module/getFatherIds?id={id}'">
        </td>
    </tr>
    <tr>
        <td class="label">表类型</td>
        <td>
            <div data-toggle="topjui-radio">
                <input type="radio" name="tableType" label="数据表格" value="datagrid" checked="checked">
                <input type="radio" name="tableType" label="树形表格" value="treegrid">
            </div>
        </td>
    </tr>
    <tr>
        <td class="label">表描述</td>
        <td>
            <input type="text" name="tableDesc" data-toggle="topjui-textbox"
                   data-options="required:true,width:450,prompt:'必填'">
        </td>
    </tr>
    <tr>
        <td class="label">表名</td>
        <td>
            <input type="text" name="tableName" data-toggle="topjui-textbox"
                   data-options="required:true,width:450,prompt:'必填'">
        </td>
    </tr>
</table>