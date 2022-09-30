<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>
<rapid:override name="body">
    <div data-toggle="topjui-layout" data-options="fit:true">

        <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:false">

            <!-- treegrid表格 -->
            <table data-toggle="topjui-treegrid"
                   data-options="id:'channelTreegrid',
               idField:'id',
			   treeField:'text',
               url:'${ctx}/system/channel/getChannelsById?id=<%=request.getParameter("channelId")%>',
               expandUrl:'${ctx}/system/channel/getChannelsByPid?pid={id}',
               fitColumns:true">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                    <th data-options="field:'text',title:'栏目名称'"></th>
                    <th data-options="field:'code',title:'代码'"></th>
                    <th data-options="field:'url',title:'URL地址'"></th>
                    <th data-options="field:'target',title:'新窗口打开',
					formatter: function(value,row,index){
						if (value == '1'){
							return '<span style=\'color:red\'>是</span>';
						} else {
							return '<span style=\'color:green\'>否</span>';
						}
                    }"></th>
                    <th data-options="field:'id',title:'编号'"></th>
                    <th data-options="field:'pid',title:'父级编号'"></th>
                    <th data-options="field:'levelId',title:'层级',
                    formatter: function(value,row,index){
						if (value == 0){
							return '顶级';
						} else if (value == 1){
							return '子级一层';
						} else if (value == 2){
							return '子级二层';
						} else if (value == 3){
							return '子级三层';
						} else if (value == 4){
							return '子级四层';
						} else if (value == 5){
							return '子级五层';
						} else {
							return '子级N层';
						}
                    }"></th>
                    <th data-options="field:'sort',title:'排序'"></th>
                    <th data-options="field:'status',title:'状态',
                    formatter: function(value,row,index){
						if (value == 1){
							return '<span style=\'color:green\'>启用</span>';
						} else if (value == 0) {
							return '<span style=\'color:red\'>禁用</span>';
						} else {
						    return '';
						}
                    }"></th>
                </tr>
                </thead>
            </table>

        </div>
    </div>

    <!-- 菜单 表格工具栏 -->
    <div id="channelTreegrid-toolbar" class="topjui-toolbar">
        <rbac:hasPermission name="menuAddDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           btnCls:'topjui-btn-green',
           grid:{
               type:'treegrid',
               id:'channelTreegrid',
               parentIdField:'pid'
           },
           parentGrid:{
               type:'treegrid',
               id:'channelTreegrid',
               param:'codeSetId,pid:id',
               unselectedMsg:'请先选中要添加菜单的上级菜单！'
           },
           dialog:{
               id:'menuAddDialog',
               href:'${ctx}/system/channel/add?type=add',
               width:950,
               height:500,
               editor:[
                   {id:'addContainer',field:'content'}
               ],
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/channel/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="menuEditDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           iconCls:'fa fa-pencil',
           btnCls:'topjui-btn-blue',
           grid:{
               type:'treegrid',
               id:'channelTreegrid',
               parentIdField:'pid'
           },
           dialog:{
               id:'menuEditDialog',
               href:'${ctx}/system/channel/edit?type=edit',
               url:'${ctx}/system/channel/getDetailByUuid?uuid={uuid}',
               width:950,
               height:500,
               editor:[
                   {id:'editContainer',field:'content'}
               ],
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/channel/update',iconCls:'fa fa-save',handler:'ajaxForm'}
               ]
           }">编辑</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="/system/channel/delete">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           iconCls:'fa fa-trash',
           btnCls:'topjui-btn-red',
           grid:{
               type:'treegrid',
               id:'channelTreegrid'
           }">删除</a>
        </rbac:hasPermission>
    </div>
</rapid:override>

<rapid:override name="script">
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.all.min.js"></script>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>