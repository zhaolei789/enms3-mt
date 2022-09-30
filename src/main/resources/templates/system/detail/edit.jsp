<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.ewsd.system.controller.BaseController"%>

<input type="hidden" id="uuid" name="uuid" >
<input type="hidden" name="password">
<table class="editTable">
	<tr>
		<td class="label">步骤号</td>
		<td><input type="text" name="stepNumber" data-toggle="topjui-textbox" data-options="required:'true',readonly:'true'" ></td>
		<td class="label">步骤名称</td>
		<td><input type="text" name="stepName" data-toggle="topjui-textbox" data-options="required:'true',readonly:'true'" ></td>
	</tr>
	<tr>
		<td class="label">步骤状态</td>
		<td><input type="text" name="stepState" data-toggle="topjui-textbox" data-options="required:'true',readonly:'true'"></td>
		<td class="label">下一步骤</td>
		<td><input type="text" name="nextstep" data-toggle="topjui-textbox" data-options="required:'true',readonly:'true'" ></td>
	</tr>
	<tr>
		<td class="label">当前步骤责任人</td>
		<td colspan="3"><input type="text" name="zrren" data-toggle="topjui-autocomplete" data-options="required:true,url:'${ctx}/ucenter/user/getListByKeywords?keywords={userName}',textField:'userName',valueField:'userNameId',dialogId:'processGridEditDialog',formatter:formatUserAutoCompleteItem,param:'duty:userName'"></td>
		<td><input type="text" name="duty" ></td>
	</tr>
	<tr>
		<td><input type="hidden" id="puuid" name="puuid" ></td>
		<td><input type="hidden" id="stepNo" name="stepNo" ></td>
	</tr>
	<!-- <tr>
		<td class="label">意见建议及备注</td>
		<td colspan="3"><input type="text" name="remark" data-toggle="topjui-textarea"></td>
	</tr> -->
</table>