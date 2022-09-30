<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>

<rapid:override name="body">
    <div data-toggle="topjui-layout" data-options="fit:true">
        <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:false">
            <table data-toggle="topjui-datagrid"
                   data-options="id:'sysCsBaseDg',
               singleSelect:true,
               selectOnCheck:false,
               checkOnSelect:false,
               url:'${ctx}/system/sysCsBase/getPageSet'">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'标识',sortable:true,checkbox:true"></th>
                    <th data-options="field:'creatorId',title:'创建人标识',sortable:true"></th>
                    <th data-options="field:'creator',title:'创建人',sortable:true"></th>
                    <th data-options="field:'createTime',title:'创建时间',sortable:true"></th>
                    <th data-options="field:'modifierId',title:'修改人标识',sortable:true"></th>
                    <th data-options="field:'modifier',title:'修改人',sortable:true"></th>
                    <th data-options="field:'modifyTime',title:'修改时间',sortable:true"></th>
                    <th data-options="field:'creatorOrgId',title:'orgId',sortable:true"></th>
                    <th data-options="field:'userName',title:'姓名',sortable:true"></th>
                    <th data-options="field:'sex',title:'性别',sortable:true"></th>
                    <th data-options="field:'age',title:'年龄',sortable:true"></th>
                    <th data-options="field:'address',title:'地址',sortable:true"></th>
                    <th data-options="field:'phone',title:'手机号',sortable:true"></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <!-- 表格工具栏 -->
    <div id="sysCsBaseDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'datagrid',
           id:'sysCsBaseDg'
       }" style="display:none">

            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#sysCsBaseDg-toolbar',
           btnCls:'topjui-btn-green',
           dialog:{
               id:'sysCsBaseAddDg',
               href:'${ctx}/system/sysCsBase/edit',
               height:450,
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/sysCsBase/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>

            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#sysCsBaseDg-toolbar',
           btnCls:'topjui-btn-blue',
           component:'edit',
           iconCls:'fa fa-pencil',
           dialog:{
               id:'sysCsBaseEditDg',
               href:'${ctx}/system/sysCsBase/edit',
               url:'${ctx}/system/sysCsBase/getDetailByUuid?uuid={uuid}',
               height:450,
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/sysCsBase/update',iconCls:'fa fa-save',handler:'ajaxForm',refresh:[{type:'datagrid',id:'sysCsBaseDg'}]}
               ]
           }">编辑</a>

            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#sysCsBaseDg-toolbar',
           btnCls:'topjui-btn-red',
	       iconCls:'fa fa-trash',
	       grid:{uncheckedMsg:'请先勾选要删除的用户',param:'uuid:uuid'},
	       url:'${ctx}/system/sysCsBase/deleteBatch'">删除</a>

        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#sysCsBaseDg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#sysCsBaseDg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>