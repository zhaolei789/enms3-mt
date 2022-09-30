<%@ page language="java" pageEncoding="UTF-8"%>

<input type="hidden" id="uuid" name="uuid" >
<table class="editTable">
	<tr>
		<td class="label">标题</td>
		<td colspan="3">
			<input type="text" name="title"
				   data-toggle="topjui-textbox"
				   data-options="required:true,width:700">
		</td>
	</tr>
	<tr>
		<td class="label">所属栏目</td>
		<td><input type="text" id="cid" name="cid"
				   data-toggle="topjui-combotree"
				   data-options="width:278,
				   panelHeight:250,
				   expandAll:false,
                   url:'${ctx}/system/channel/getChannelsById?id=1',
                   expandUrl:'${ctx}/system/channel/getChannelsByPid?pid={id}',
                   getFatherIdsUrl:'${ctx}/system/channel/getFatherIds?id={id}'">
		</td>
		<td class="label">发布状态</td>
		<td width="278"><input name="status" data-toggle="topjui-switchbutton" data-options="onText:'已发布',offText:'未发布'"></td>
	</tr>
	<tr>
		<td class="label">关键词</td>
		<td><input type="text" name="keywords" data-toggle="topjui-textbox" data-options="width:278"></td>
		<td class="label">头条推荐</td>
		<td><input name="recommend" data-toggle="topjui-switchbutton" data-options="onText:'是',offText:'否'"></td>
	</tr>
	<tr>
		<td class="label">文章描述</td>
		<td colspan="3"><input type="text" name="description" data-toggle="topjui-textarea" data-options="required:true,width:700" ></td>
	</tr>
	<tr>
		<td class="label">封面缩略图</td>
		<td colspan="3">
			<input type="text" name="thumbnail" data-toggle="topjui-uploadbox"  data-options="id:'thumbnailAddEditor',width:700">
		</td>
	</tr>
	<tr>
		<td class="label">文章内容</td>
		<td colspan="3">
			<textarea name="content" style="height:300px;" data-toggle="topjui-ueditor" data-options="id:'contentAddEditor'"></textarea>
		</td>
	</tr>
	<!--tr>
		<td class="label">文章内容</td>
		<td colspan="3">
			<input type="text" name="content"
				   data-toggle="topjui-kindeditor"
				   data-options="module:'sys_article',category:'default'">
		</td>
	</tr-->
</table>