<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />

<table class="editTable">
	<tr>
		<td class="label">导入说明</td>
		<td>
			<input type="text" data-toggle="topjui-textarea" data-options="id:'importExcelRemark',height:75,readonly:true" name="importExcelRemark"
				   value="1、导入模板与导出Excel的文件格式相同，使用‘导出Excel’文件作为导入模板；2、删除导入模板中除标题行外的其余行，在导入模板中维护好数据选择附件上传；3、点击'开始导入'按钮即可导入，导入成功后右下角将提示导入的行数。">
		</td>
	</tr>
	<tr style="display: none;">
		<td class="label">SQL语句</td>
		<td>
			<input type="text" data-toggle="topjui-textarea" data-options="id:'importExcelSql',height:150,readonly:true" name="importExcelSql">
		</td>
	</tr>
	<tr>
		<td class="label">上传导入文件</td>
		<td>
			<input type="text" id="attachPath" name="attachPath" data-toggle="topjui-textbox" data-width="371" data-readonly="true"/>
			<input type="button" value="选择附件" data-toggle="topjui-upload"
				   data-options="
				   url:'${ctx}/system/attachment/kindeditorUpload?dir=temp&module=importExcel&category=default&puuid=<%=request.getParameter("uuid")%>',
				   fieldId:'attachPath'"/>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function () {
		//窗口打开时，触发事件
		$(this).trigger(topJUI.eventType.initUI.importExcelForm);
	});
</script>