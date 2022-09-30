<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>

<rapid:override name="body">
    <div data-toggle="topjui-layout" data-options="fit:true">
        <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:false">
            <table data-toggle="topjui-datagrid"
                   data-options="id:'erpSampleHibernateDg',
               singleSelect:true,
               selectOnCheck:false,
               checkOnSelect:false,
               url:'${ctx}/system/erpSampleHibernate/getPageSet'">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'',sortable:true,checkbox:true"></th>
                    <th data-options="field:'title',title:'标题',sortable:true"></th>
                    <th data-options="field:'remark',title:'备注',sortable:true"></th>
                    <th data-options="field:'creator',title:'创建人',sortable:true"></th>
                    <th data-options="field:'createTime',title:'创建时间',sortable:true"></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <!-- 表格工具栏 -->
    <div id="erpSampleHibernateDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'datagrid',
           id:'erpSampleHibernateDg'
       }" style="display:none">
        <%--<rbac:hasPermission name="erpSampleHibernateAddDialog">--%>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#erpSampleHibernateDg-toolbar',
           btnCls:'topjui-btn-green',
           dialog:{
               id:'erpSampleHibernateAddDg',
               href:'${ctx}/system/erpSampleHibernate/edit',
               height:450,
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/erpSampleHibernate/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>
        <%--</rbac:hasPermission>--%>
        <%--<rbac:hasPermission name="erpSampleHibernateEditDialog">--%>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#erpSampleHibernateDg-toolbar',
           btnCls:'topjui-btn-blue',
           component:'edit',
           iconCls:'fa fa-pencil',
           dialog:{
               id:'erpSampleHibernateEditDg',
               href:'${ctx}/system/erpSampleHibernate/edit',
               url:'${ctx}/system/erpSampleHibernate/getDetailByUuid?uuid={uuid}',
               height:450,
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/erpSampleHibernate/update',iconCls:'fa fa-save',handler:'ajaxForm',refresh:[{type:'datagrid',id:'erpSampleHibernateDg'}]}
               ]
           }">编辑</a>
        <%--</rbac:hasPermission>--%>
        <%--<rbac:hasPermission name="/system/erpSampleHibernate/deleteBatch">--%>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#erpSampleHibernateDg-toolbar',
           btnCls:'topjui-btn-red',
	       iconCls:'fa fa-trash',
	       grid:{uncheckedMsg:'请先勾选要删除的用户',param:'uuid:uuid'},
	       url:'${ctx}/system/erpSampleHibernate/delete'">删除</a>
        <%--</rbac:hasPermission>--%>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#erpSampleHibernateDg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#erpSampleHibernateDg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>