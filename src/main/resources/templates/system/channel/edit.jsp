<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<input type="hidden" name="uuid">
<input type="hidden" name="id">
<input type="hidden" name="viewNums">
<table class="editTable">
    <tr>
        <td colspan="4">
            <div class="divider">
                <span>栏目信息</span>
            </div>
        </td>
    </tr>
    <tr>
        <td class="label">栏目名称</td>
        <td colspan="3"><input type="text" data-toggle="topjui-textbox" name="text"
                               data-options="required:true,width:700"></td>
    </tr>
    <tr>
        <td class="label">封面缩略图</td>
        <td colspan="3">
            <input type="text" data-toggle="topjui-uploadbox" name="thumbnail"
                   data-options="width:700,
				   editable:false,
				   buttonText:'上传图片',
				   accept:'images',
				   uploadUrl:'/json/response/upload.json'">
        </td>
    </tr>
    <tr>
        <td class="label">关键词</td>
        <td colspan="3"><input type="text" data-toggle="topjui-textbox" name="keywords" data-options="width:700"></td>
    </tr>
    <tr>
        <td class="label">描述</td>
        <td colspan="3"><input type="text" data-toggle="topjui-textbox" name="description" data-options="width:700">
        </td>
    </tr>
    <tr>
        <td class="label">内容</td>
        <td colspan="3">
			<textarea name="content" style="height:300px;"
                      data-toggle="topjui-ueditor" data-options="id:'editContainer'"></textarea>
        </td>
    </tr>
    <tr>
        <td colspan="4">
            <div class="divider">
                <span>栏目配置</span>
            </div>
        </td>
    </tr>
    <tr>
        <td class="label">层级</td>
        <td>
            <input type="text" name="levelId"
                   data-toggle="topjui-combobox"
                   data-options="required:true,
                   textField:'text',
                   valueField:'code',
                   panelHeight:132,
                   width:278,
                   url:'${ctx}/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=ACDA&levelId=4'">
        </td>
        <td class="label">父级栏目</td>
        <td>
            <input type="text" name="pid" data-toggle="topjui-combotree"
                   data-options="required:true,
                   url:'${ctx}/system/channel/getChannelsById?id=1',
                   expandUrl:'${ctx}/system/channel/getChannelsByPid?pid={id}',
                   expandAll:true,
                   width:278,
                   panelHeight:250">
        </td>
    </tr>
    <tr>
        <td class="label">代码</td>
        <td><input type="text" data-toggle="topjui-textbox" name="code" data-options="width:278"></td>
        <td class="label">URL地址</td>
        <td><input type="text" data-toggle="topjui-textbox" name="url" data-options="width:278"></td>
    </tr>
    <tr>
        <td class="label">新窗口打开</td>
        <td>
            <input type="text" name="target"
                   data-toggle="topjui-combobox"
                   data-options="required:true,
                   textField:'text',
                   valueField:'code',
                   width:278,
                   url:'${ctx}/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=ACA&levelId=4'">
        </td>
        <td class="label">状态</td>
        <td><input type="text" name="status"
                   data-toggle="topjui-combobox"
                   data-options="required:true,
                   textField:'text',
                   valueField:'code',
                   width:278,
                   url:'${ctx}/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=ACC&levelId=1'"></td>
    </tr>
    <tr>
        <td class="label">存在子项</td>
        <td>
            <input type="text" name="state"
                   data-toggle="topjui-combobox"
                   data-options="required:true,
                   textField:'text',
                   valueField:'code',
                   width:278,
                   url:'${ctx}/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=ACF&levelId=1'">
        </td>
        <td class="label">图标</td>
        <td><input type="text" data-toggle="topjui-textbox" name="iconCls" data-options="width:278"></td>
    </tr>
    <tr>
        <td class="label">排序</td>
        <td><input type="text" data-toggle="topjui-numberspinner" name="sort" data-options="width:278"></td>
        <td class="label"></td>
        <td></td>
    </tr>
</table>