<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>

<rapid:override name="body">
    <!-- layout布局 开始 -->
    <div data-toggle="topjui-layout" data-options="fit:true">
        <div data-options="region:'west',title:'',fit:false,split:true,border:false,width:'20%',iconCls:'fa fa-sitemap',headerCls:'border_right',bodyCls:'border_right'">
            <table data-toggle="topjui-treegrid"
                   data-options="id:'channelDg',
			   idField:'id',treeField:'text',
			   fitColumns:true,
			   singleSelect:true,
			   url:'${ctx}/system/channel/getChannelsById?id=<%=request.getParameter("channelId")%>',
               expandUrl:'${ctx}/system/channel/getChannelsByPid?pid={id}',
			   childGrid:{
                   param:'id:id',
                   grid:[
                       {type:'datagrid',id:'articleDg'},
                   ]
               }">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                    <th data-options="field:'text',title:'栏目名称',width:100"></th>
                </tr>
                </thead>
            </table>
        </div>

        <div data-options="region:'center',iconCls:'icon-reload',title:'',fit:false,split:true,border:false,bodyCls:'border_left_right'">
            <!-- datagrid表格 -->
            <table data-toggle="topjui-datagrid"
                   data-options="id:'articleDg',
               fitColumns:true,
               singleSelect:true,
               url:'${ctx}/system/article/getPageSetData',
               childTab: [{id:'eastTabs'}]">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                        <%--<th data-options="field:'title',title:'标题',width:100,formatter:openNewWindow"></th>--%>
                    <th data-options="field:'title',title:'标题',width:100,sortable:true,formatter:openNewTab"></th>
                    <th data-options="field:'createTime',title:'创建时间',sortable:true"></th>
                </tr>
                </thead>
            </table>
        </div>

        <div data-options="region:'east',iconCls:'icon-chart_pie',title:'',split:true,border:false,width:'40%'">
            <div data-toggle="topjui-tabs"
                 data-options="id:'eastTabs',
             fit:true,
             border:true,
             bodyCls:'border_right_none',
             parentGrid:{
                 type:'datagrid',
                 id:'articleDg',
                 param:'uuid'
             }">
                <div title="文章详情"
                     data-options="id:'detail1Panel',iconCls:'fa fa-table',
					 href:'${ctx}/system/article/detail?uuid={uuid}'"></div>
                <div title="打印页面"
                     data-options="id:'detail2Panel',iconCls:'fa fa-table',
					 href:'${ctx}/system/article/print?uuid={uuid}'"></div>
            </div>
        </div>
    </div>
    <!-- layout布局 结束 -->

    <!-- 表格工具栏 -->
    <div id="articleDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
        type:'datagrid',
        id:'articleDg'
     }" style="display:none">
        <rbac:hasPermission name="articleAddDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#articleDg-toolbar',
           btnCls:'topjui-btn-green',
           parentGrid:{
               type:'treegrid',
               id:'channelDg',
               param:'cid:id',
               unselectedMsg:'请先选择文章要发布的左侧树形导航中的栏目'
           },
           dialog:{
               id:'articleAddDialog',
               href:'${ctx}/system/article/add',
               width:950,
               height:500,
               editor:[
                   {id:'contentAddEditor',field:'content'}
               ],
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/article/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="articleEditDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#articleDg-toolbar',
           btnCls:'topjui-btn-blue',
           iconCls:'fa fa-pencil',
           dialog:{
               id:'articleEditDialog',
               href:'${ctx}/system/article/edit?uuid={uuid}',
               url:'${ctx}/system/article/getDetailByUuid?uuid={uuid}',
               width:950,
               height:500,
               editor:[
                   {id:'contentEditEditor',field:'content'}
               ],
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/article/update',iconCls:'fa fa-save',handler:'ajaxForm'}
               ]
           }">编辑</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="/system/article/delete">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#articleDg-toolbar',
           btnCls:'topjui-btn-red',
           grid:{uncheckedMsg:'请先勾选要删除的指标',param:'uuid:uuid'},
           url:'${ctx}/system/article/delete'">删除</a>
        </rbac:hasPermission>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#articleDg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#articleDg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'export',
       extend:'#articleDg-toolbar',
       btnCls:'topjui-btn-brown'">导出</a>
    </div>
</rapid:override>

<rapid:override name="script">
    <script>
        function openNewWindow(value, row, index) {
            return '<a href=\"/system/article/detail?uuid=' + row.uuid + '\" target=\"_blank\">' + value + '</a>'
        }
        function openNewTab(value, row, index) {
            return '<a href="javascript:window.parent.addParentTab({href:\'/system/article/detail?uuid=' + row.uuid + '\',title:\'' + row.title + '\'})\">' + value + '</a>'
        }
    </script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.all.min.js"></script>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>