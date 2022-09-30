<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../common/common/head.jsp" %>

<div data-toggle="topjui-layout" data-options="fit:true">
    <div data-options="region:'west',title:'树形导航',split:true,border:true,width:'20%',iconCls:'fa fa-sitemap',headerCls:'border_top_none'">
        <!-- treegrid表格 -->
        <table data-toggle="topjui-treegrid"
               data-options="id:'categoryTreegrid',
			   idField:'id',
			   treeField:'text',
			   url:'${ctx}/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=ABBA&levelId=4',
			   expandUrl:'${ctx}/system/codeItem/getListByPid?pid={id}',
			   childGrid:{
                   param:'codeSetId:codeSetId,categoryId:id',
                   grid:[
                       {type:'datagrid',id:'achiveDatagrid'}
                   ]
               }">
            <thead>
            <tr>
                <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                <th data-options="field:'text',title:'资源类别',width:100"></th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:true,bodyCls:'border_top_none'">
        <div data-options="region:'north',split:true,border:false" style="height:60%">
            <!-- datagrid表格 -->
            <table data-toggle="topjui-datagrid"
                   data-options="id:'achiveDatagrid',
                   url:'${ctx}/system/archive/getPageSetData',
                   childGrid:{
                       param:'puuid:uuid',
                       grid:[
                           {type:'datagrid',id:'cloudAttachmentDatagrid'}
                       ]
                   }">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                    <th data-options="field:'title',title:'资源名称'"></th>
                    <th data-options="field:'description',title:'资源描述'"></th>
                    <th data-options="field:'creator',title:'创建人'"></th>
                    <th data-options="field:'createTime',title:'创建时间'"></th>
                </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'south',split:true,border:false" style="height:40%">
            <div data-toggle="topjui-tabs" data-options="fit:true,border:false">
                <div title="附件信息" data-options="iconCls:'fa fa-table'">
                    <!-- datagrid表格 -->
                    <table data-toggle="topjui-datagrid"
                           data-options="id:'cloudAttachmentDatagrid',
						url:'${ctx}/system/attachment/getListByPid'">
                        <thead>
                        <tr>
                            <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                            <th data-options="field:'fileName',title:'文件名称',
                            formatter: function(value,row,index){
                                return '<a href=\'/system/attachment/download?uuid='+row.uuid+'\' target=\'_blank\'>'+value+'</a>';
                            }"></th>
                            <th data-options="field:'fileSize',title:'文件大小',
                            formatter: function(value,row,index){
                                return bytesToSize(value);
                            }"></th>
                            <th data-options="field:'fileVersion',title:'文件版本',
                            formatter: function(value,row,index){
                                return value == null ? '-' : value;
                            }"></th>
                            <th data-options="field:'fileStatus',title:'文件状态',
                            formatter: function(value,row,index){
                                return value == null ? '-' : value;
                            }"></th>
                            <th data-options="field:'creator',title:'上传人'"></th>
                            <th data-options="field:'createTime',title:'上传时间'"></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 表格工具栏 -->
<div id="achiveDatagrid-toolbar" class="topjui-toolbar">
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       },
       parentGrid:{
           type:'treegrid',
           id:'categoryTreegrid',
           param:'categoryId:id',
           unselectedMsg:'请先选中左边要添加的资源类别！'
       },
       dialog:{
           id:'archiveAddDialog-<%=request.getParameter("source")%>',
           href:'${ctx}/system/archive/edit',
           height:200,
           buttonsGroup:[
               {text:'保存',url:'${ctx}/system/archive/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
           ]
       }">新增</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       iconCls:'fa fa-pencil',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       },
       dialog:{
           id:'archiveEditDialog-<%=request.getParameter("source")%>',
           href:'${ctx}/system/archive/edit?uuid={uuid}',
           url:'${ctx}/system/archive/getDetailByUuid?uuid={uuid}',
           height:200,
           buttonsGroup:[
               {text:'更新',url:'${ctx}/system/archive/update',iconCls:'fa fa-save',handler:'ajaxForm'},
           ]
       }">编辑</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'doAjax',
       url:'${ctx}/system/archive/delete?source=welder',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       }">删除</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'filter',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       }">过滤</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'search',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       }">查询</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'export',
       grid:{
           type:'datagrid',
           id:'achiveDatagrid'
       }">导出</a>
</div>

<!-- 附件 表格工具栏 -->
<div id="cloudAttachmentDatagrid-toolbar" class="topjui-toolbar">
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       parentGrid:{
           type:'datagrid',
           id:'achiveDatagrid',
           unselectedMsg:'请先选中需要上传附件的资源！'
       },
       dialog:{
           id:'cloudAttachmentAddDialog',
           href:'${ctx}/system/attachment/cloudAdd',
           height:200,
           buttonsGroup:[
               {text:'上传',url:'${ctx}/system/attachment/cloudUpload',iconCls:'fa fa-save',handler:'ajaxForm'}
           ]
       }">新增</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       iconCls:'fa fa-pencil',
       grid:{
           type:'datagrid',
           id:'cloudAttachmentDatagrid'
       },
       dialog:{
           id:'cloudAttachmentEditDialog',
           height:200,
           href:'${ctx}/system/attachment/edit',
           url:'${ctx}/system/attachment/getDetailByUuid?uuid={uuid}',
           buttonsGroup:[
               {text:'更新',url:'${ctx}/system/attachment/update',iconCls:'fa fa-save',handler:'ajaxForm'}
           ]
       }">编辑</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'doAjax',
	   url:'${ctx}/system/attachment/delete',
       grid:{
           type:'datagrid',
           id:'cloudAttachmentDatagrid'
       }">删除</a>
</div>

<%@ include file="../../common/common/foot.jsp" %>