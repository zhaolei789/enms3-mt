<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../common/common/head.jsp" %>

<div data-toggle="topjui-layout" data-options="fit:true">
    <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:true,bodyCls:'border_top_none'">
            <table data-toggle="topjui-datagrid"
                   data-options="id:'potentialStudentDatagrid',
                   url:'${ctx}/kindergarten/potentialStudent/getPageSetData',
                   childGrid:{
                       param:'potentialStudentUuid:uuid', <!-- 点击传递ID-->
                       grid:[
                           {type:'datagrid',id:'attachmentDatagrid'},
                       ]
                   }">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>
                    <th data-options="field:'name',title:'家长姓名'"></th>
                    <th data-options="field:'relationship',title:'关系'"></th>
                    <th data-options="field:'msgSource',title:'消息来源'"></th>
                    <th data-options="field:'opinion',title:'家长意向'"></th>
                    <th data-options="field:'phone',title:'联系电话'"></th>
                    <th data-options="field:'creator',title:'创建人'"></th>
                    <th data-options="field:'createTime',title:'创建时间',
                    formatter(value,row,index){
                        return timestamp2Datetime(value,'Y-m-d H:i');
                    }"></th>
                </tr>
                </thead>
            </table>
    </div>
</div>

<!-- 表格工具栏 -->
<div id="potentialStudentDatagrid-toolbar" class="topjui-toolbar">
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       },
       dialog:{
           id:'potentialStudentAddDialog',
           href:'${ctx}/kindergarten/potentialStudent/edit',
           height:200,
           buttonsGroup:[
               {text:'保存',url:'${ctx}/kindergarten/potentialStudent/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
           ]
       }">新增</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'openDialog',
       iconCls:'fa fa-pencil',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       },
       dialog:{
           id:'potentialStudentEditDialog',
           href:'${ctx}/kindergarten/potentialStudent/edit?uuid={uuid}',
           url:'${ctx}/kindergarten/potentialStudent/getDetailByUuid?uuid={uuid}',
           height:200,
           buttonsGroup:[
               {text:'更新',url:'${ctx}/kindergarten/potentialStudent/update',iconCls:'fa fa-save',handler:'ajaxForm'},
           ]
       }">编辑</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'doAjax',
       url:'${ctx}/kindergarten/potentialStudent/delete',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       }">删除</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'filter',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       }">过滤</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'search',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       }">查询</a>
    <a href="javascript:void(0)"
       data-toggle="topjui-menubutton"
       data-options="method:'export',
       grid:{
           type:'datagrid',
           id:'potentialStudentDatagrid'
       }">导出</a>
</div>

<%@ include file="../../common/common/foot.jsp" %>