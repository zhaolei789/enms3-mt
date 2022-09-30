<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>

<rapid:override name="body">
    <div data-toggle="topjui-layout" data-options="fit:true">
        <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:false">
            <table data-toggle="topjui-treegrid"
                   data-options="id:'oaCategoryDg',
               singleSelect:true,
               selectOnCheck:false,
               checkOnSelect:false,
                url:'${ctx}/system/sysCategory/getCategorysByTypeAndLevelId?levelId=1&type=${param.type}',
                expandUrl:'${ctx}/system/sysCategory/getListByPid?pid={id}&type=${param.type}',

               ">
                <thead>
                <tr>
                    <th data-options="field:'uuid',title:'',sortable:true,checkbox:true"></th>
                    <th data-options="field:'text',title:'栏目名称',sortable:true"></th>
                    <th data-options="field:'levelId',title:'层级',sortable:true,
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
                    <th data-options="field:'sort',title:'排序',sortable:true"></th>
                    <th data-options="field:'portalDisplay',title:'是否首页显示',sortable:true,
                                            formatter: function(value,row,index){
                                                if (value == 0){
                                                    return '否';
                                                } else if (value == 1){
                                                    return '是';
                                                }
                                            }"></th>
                    <th data-options="field:'creator',title:'创建人',sortable:true"></th>
                    <th data-options="field:'createTime',title:'创建时间',sortable:true"></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <!-- 表格工具栏 -->
    <div id="oaCategoryDg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'treegrid',
           id:'oaCategoryDg'
       }" style="display:none">
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="menu:'#moreSubMenu',
           btnCls:'topjui-btn-green',
           iconCls:'fa fa-plus',
           hasDownArrow:true">新增</a>
    <div id="moreSubMenu" class="topjui-toolbar" style="width:100px;">
        <rbac:hasPermission name="sysCategoryAddDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#oaCategoryDg-toolbar',
           iconCls:'fa fa-plus',
           dialog:{
               id:'sysCategoryAddDialog',
               width:700,
               height:300,
               href:'${ctx}/system/sysCategory/editRoot?type=${param.type}',
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/sysCategory/saveRoot',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增根栏目</a>
        </rbac:hasPermission>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'openDialog',
           extend:'#oaCategoryDg-toolbar',
          iconCls:'fa fa-plus',
            grid:{
	       type:'treegrid',
           id:'oaCategoryDg',
           parentIdField:'pid'
       },
           parentGrid:{
               type:'treegrid',
               id:'oaCategoryDg',
               param:'codeSetId,pid:id,levelId',
               unselectedMsg:'请先选中要添加菜单的上级菜单'
           },
           dialog:{
               id:'oaTopicCategoryAddDg',
               width:700,
               height:300,
               href:'${ctx}/system/sysCategory/edit?type=${param.type}',
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/sysCategory/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增子栏目</a>
    </div>
        <rbac:hasPermission name="sysCategoryEditDialog">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#oaCategoryDg-toolbar',
           btnCls:'topjui-btn-blue',
           component:'edit',
           iconCls:'fa fa-pencil',
           dialog:{
               width:700,
               height:300,
               id:'sysCategoryEditDialog',
               href:'${ctx}/system/sysCategory/edit?type=${param.type}',
               url:'${ctx}/system/sysCategory/getDetailByUuid?uuid={uuid}',
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/sysCategory/update',iconCls:'fa fa-save',handler:'ajaxForm',refresh:[{type:'treegrid',id:'oaCategoryDg'}]}
               ]
           }">编辑</a>
        </rbac:hasPermission>
        <rbac:hasPermission name="/system/sysCategory/deleteBatch">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#oaCategoryDg-toolbar',
           btnCls:'topjui-btn-red',
	       iconCls:'fa fa-trash',
	       grid:{uncheckedMsg:'请先勾选要删除的用户',param:'uuid:uuid'},
	       url:'${ctx}/system/sysCategory/deleteBatch'">删除</a>
        </rbac:hasPermission>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#oaCategoryDg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#oaCategoryDg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
    </div>
</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>