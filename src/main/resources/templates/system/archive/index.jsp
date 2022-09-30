<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>

<rapid:override name="head">
    <link type="text/css" rel="stylesheet" href="${staticServer}/static/plugins/webuploader/css/webuploader.css">
</rapid:override>

<rapid:override name="body">
    <div data-toggle="topjui-layout" data-options="fit:true">
        <div data-options="region:'west',title:'',fit:false,split:true,border:false,width:'20%',iconCls:'fa fa-sitemap',headerCls:'border_right',bodyCls:'border_right'">
            <!-- treegrid表格 -->
            <table data-toggle="topjui-treegrid"
                   data-options="id:'categoryTreegrid',
			   idField:'id',
			   treeField:'text',
			   fitColumns:true,
			   singleSelect:true,
			   url:'${ctx}/system/channel/getChannelsById?id=12',
			   expandUrl:'${ctx}/system/channel/getChannelsByPid?pid={id}',
			   childGrid:{
                   param:'categoryId:id',
                   grid:[
                       {type:'datagrid',id:'archiveDg'}
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

        <div data-options="region:'center',iconCls:'icon-reload',title:'',fit:false,split:true,border:false">
            <div id="layout2" data-toggle="topjui-layout" data-options="fit:true">
                <div data-options="region:'center',fit:false,split:true,border:true,bodyCls:'border_top_none'">
                    <div data-toggle="topjui-tabs" data-options="id:'centerTabs',fit:true,border:false">
                        <div title="文档列表" data-options="iconCls:'fa fa-table'">
                            <!-- datagrid表格 -->
                            <table data-toggle="topjui-datagrid"
                                   data-options="id:'archiveDg',
                               fitColumns:true,
                               url:'${ctx}/system/archive/getPageSetData',
                               childTab: [
                                    {id:'eastTabs'},
                                    {id:'southTabs'}
                               ]">
                                <thead>
                                <tr>
                                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                                    <th data-options="field:'title',title:'资源名称',sortable:true,width:100"></th>
                                    <th data-options="field:'creator',title:'创建人',sortable:true"></th>
                                    <th data-options="field:'createTime',title:'创建时间',sortable:true"></th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
                <div data-options="region:'south',fit:false,split:true,border:true,height:'40%',bodyCls:'border_bottom_none'">
                    <div data-toggle="topjui-tabs"
                         data-options="id:'southTabs',
                     fit:true,
                     border:false,
                     parentGrid:{
                         type:'datagrid',
                         id:'archiveDg',
                         param:'puuid:uuid'
                     }">
                        <div title="附件信息" data-options="id:'southTab0',iconCls:'fa fa-table'">
                            <!-- datagrid表格 -->
                            <table data-toggle="topjui-datagrid"
                                   data-options="id:'attachmentDg',
                               initCreate: false,
                               fitColumns:true,
						       url:'${ctx}/system/attachment/getListByPuuid'">
                                <thead>
                                <tr>
                                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                                    <th data-options="field:'fileName',title:'文件名称',width:100,
                                formatter: function(value,row,index){
                                return '<a href=\'/system/attachment/download?uuid='+row.uuid+'\' target=\'_blank\'>'+value+'</a>';
                                }"></th>
                                    <th data-options="field:'fileSize',title:'文件大小',
                                formatter: function(value,row,index){
                                    return bytesToSize(value);
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

        <div data-options="region:'east',title:'',split:true,border:false,width:'40%',bodyCls:'border_left'">
            <div data-toggle="topjui-tabs"
                 data-options="id:'eastTabs',
             fit:true,
             border:false,
             parentGrid:{
                 type:'datagrid',
                 id:'archiveDg',
                 param:'uuid'
             }">
                <div title="详细信息"
                     data-options="id:'eastTab0',
                 iconCls:'fa fa-table',
			     href:'${ctx}/system/archive/detail?uuid={uuid}'"></div>
            </div>
        </div>
    </div>

    <!-- 表格工具栏 -->
    <div id="archiveDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'datagrid',
           id:'archiveDg'
       }" style="display:none">
        <rbac:hasPermission name="archiveAddDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#archiveDg-toolbar',
           btnCls:'topjui-btn-green',
           parentGrid:{
               type:'treegrid',
               id:'categoryTreegrid',
               param:'categoryId:id',
               unselectedMsg:'请先选中左边要添加的资源类别！'
           },
           dialog:{
               id:'archiveAddDialog',
               href:'${ctx}/system/archive/add',
               editor:[
                   {id:'descriptionAddEditor',field:'description'}
               ],
               width:950,
               height:500,
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/archive/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="archiveEditDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#archiveDg-toolbar',
           iconCls:'fa fa-pencil',
           btnCls:'topjui-btn-blue',
           dialog:{
               id:'archiveEditDialog',
               href:'${ctx}/system/archive/edit?uuid={uuid}',
               url:'${ctx}/system/archive/getDetailByUuid?uuid={uuid}',
               editor:[
                   {id:'descriptionEditEditor',field:'description'}
               ],
               width:950,
               height:500,
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/archive/update',iconCls:'fa fa-save',handler:'ajaxForm'},
               ]
           }">编辑</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="/system/archive/delete">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#archiveDg-toolbar',
           btnCls:'topjui-btn-red',
           url:'${ctx}/system/archive/delete?source=welder'">删除</a>
        </rbac:hasPermission>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#archiveDg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#archiveDg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
    </div>

    <!-- 附件 表格工具栏 -->
    <div id="attachmentDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'datagrid',
           id:'attachmentDg'
       }" style="display:none">
        <a href="javascript:void(0)"
           data-toggle="topjui-uploader"
           data-options="iconCls:'fa fa-plus',
       btnCls:'topjui-btn-green',
       parentGrid:{
           type:'datagrid',
           id:'archiveDg',
           unselectedMsg:'请先选中上表中要添加附件的数据！'
       }">添加附件</a>
        <rbac:hasPermission name="attachmentEditDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#attachmentDg-toolbar',
           iconCls:'fa fa-pencil',
           btnCls:'topjui-btn-blue',
           dialog:{
               id:'attachmentEditDialog',
               href:'${ctx}/system/attachment/edit',
               url:'${ctx}/system/attachment/getDetailByUuid?uuid={uuid}',
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/attachment/update',iconCls:'fa fa-save',handler:'ajaxForm'}
               ]
           }">编辑</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="/system/attachment/delete">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#attachmentDg-toolbar',
	       url:'${ctx}/system/attachment/delete',
	       btnCls:'topjui-btn-red'">删除</a>
        </rbac:hasPermission>
    </div>
</rapid:override>

<rapid:override name="script">
    <script type="text/javascript" src="${staticServer}/static/plugins/webuploader/js/webuploader.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.all.min.js"></script>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>