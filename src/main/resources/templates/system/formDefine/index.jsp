<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<rapid:override name="body">
    <c:choose>
        <c:when test="${count == 0}">

            <div data-toggle="topjui-layout" data-options="fit:true">
                <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,border:false">
                    <table data-toggle="<c:choose><c:when test="${sysFdTable.tableType == 'datagrid'}">topjui-datagrid</c:when><c:otherwise>topjui-treegrid</c:otherwise></c:choose>"
                           data-options="id:'${TableName}Dg',
                            sortOrder:'asc',
                           <c:choose>
                            <c:when test="${sysFdTable.tableType == 'datagrid'}">
                             url:'${ctx}/system/formDefine/getPageSet?tableUuid=${sysFdTable.uuid}'
                            </c:when>
                            <c:otherwise>
                                url:'${ctx}/system/formDefine/getListByLevelId?levelId=1&tableUuid=${sysFdTable.uuid}',
                                expandUrl:'${ctx}/system/formDefine/getListByPid?pid={id}&tableUuid=${sysFdTable.uuid}'
                            </c:otherwise>
                            </c:choose>
                                ">
                        <thead>

                        <tr>
                            <th data-options="field:'${sysFdField.uuid}',title:'UUID',checkbox:true"></th>
                            <c:forEach items="${sysFdFields}" var="sysFdField">
                                <c:choose>
                                    <c:when test="${sysFdField.fieldIsShow == '2'}">

                                    </c:when>
                                    <c:otherwise>
                                        <th data-options="field:'${sysFdField.fieldName}',title:'${sysFdField.fieldDesc}',sortable:'${sysFdField.sortFields}'
                                    <c:if test="${sysFdField.fieldType == 'timestamp'}">
                                    ,formatter:function(value,row,index){
                                        return timestamp2Datetime(value,'Y-m-d H:i');
                                    }
                                    </c:if>
                                    <c:if test="${sysFdField.fieldType == 'DATE'}">
                                    ,formatter:function(value,row,index){
                                        return timestamp2Datetime(value,'Y-m-d H:i');
                                    }
                                    </c:if>"></th>

                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

        </c:when>
        <c:otherwise>
            <div data-toggle="topjui-layout" data-options="fit:true">
            <div data-options="region:'center',iconCls:'icon-reload',title:'',split:true,height:'50%',border:false,bodyCls:'border_bottom'">
                <table data-toggle="<c:choose><c:when test="${sysFdTable.tableType == 'datagrid'}">topjui-datagrid</c:when><c:otherwise>topjui-treegrid</c:otherwise></c:choose>"
                       data-options="id:'${TableName}Dg',

                           <c:choose>
                            <c:when test="${sysFdTable.tableType == 'datagrid'}">
                             url:'${ctx}/system/formDefine/getPageSet?tableUuid=${sysFdTable.uuid}',
                              childTab: [{id:'southTabs',param:'${sizeUuid}:${sizeUuid},id:id'}]
                            </c:when>
                            <c:otherwise>
                                url:'${ctx}/system/formDefine/getListByLevelId?levelId=1&tableUuid=${sysFdTable.uuid}',
                                expandUrl:'${ctx}/system/formDefine/getListByPid?pid={id}&tableUuid=${sysFdTable.uuid}',
                                 childTab: [{id:'southTabs',param:'${sizeUuid}:${sizeUuid},id:id'}]
                            </c:otherwise>
                            </c:choose>
                            ">
                    <thead>
                    <tr>
                        <th data-options="field:'${sysFdField.uuid}',title:'UUID',checkbox:true"></th>
                        <c:forEach items="${sysFdFields}" var="sysFdField">
                            <c:choose>
                                <c:when test="${sysFdField.fieldIsShow == '2'}">

                                </c:when>
                                <c:otherwise>
                                    <th data-options="field:'${sysFdField.fieldName}',title:'${sysFdField.fieldDesc}',sortable:'${sysFdField.sortFields}'
                                    <c:if test="${sysFdField.fieldType == 'timestamp'}">
                                    ,formatter:function(value,row,index){
                                        return timestamp2Datetime(value,'Y-m-d H:i');
                                    }
                                    </c:if>
                                    <c:if test="${sysFdField.fieldType == 'DATE'}">
                                    ,formatter:function(value,row,index){
                                        return timestamp2Datetime(value,'Y-m-d H:i');
                                    }
                                    </c:if>"></th>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'south',iconCls:'icon-reload',title:'',split:true,height:'50%',border:false,bodyCls:'border_top' ">
                <div data-toggle="topjui-tabs"
                     data-options="id:'southTabs',
						 fit:true,
						 border:false,
						 parentGrid:{
                         type:'datagrid',
                         id:'${TableName}Dg',
                         param:'puuid:${sizeUuid},id:id'
                     }">

                    <c:forEach items="${sysFdTables}" var="sysFdTabless" varStatus="index">
                        <div title="${sysFdTabless.tableDesc}"
                             data-options="id:'${sysFdTabless.tableName}tab',iconCls:'fa fa-th'">
                            <table data-toggle="topjui-datagrid"
                                   data-options="id:'${sysFdTabless.tableName}Dg',
                                   initCreate: false,
                                   url:'${ctx}/system/formDefine/getPageSetSon?tableUuid=${sysFdTabless.uuid}&tableName=${sysFdTable.tableName}'">
                                <thead>
                                <tr>
                                    <th data-options="field:'${sysFdFieldMaps.uuid}',title:'UUID',checkbox:true"></th>

                                    <c:forEach items="${sysFdFieldMap.get(sysFdTabless.uuid)}"
                                               var="sysFdFieldMaps">
                                        <c:choose>
                                            <c:when test="${sysFdFieldMaps.fieldIsShow == '2'}">

                                            </c:when>

                                            <c:otherwise>

                                                <th data-options="field:'${sysFdFieldMaps.fieldName}',title:'${sysFdFieldMaps.fieldDesc}',sortable:'${sysFdField.sortFields}'
                                              <c:if test="${sysFdFieldMaps.fieldType == 'timestamp'}">
                                            ,formatter:function(value,row,index){
                                                return timestamp2Datetime(value,'Y-m-d H:i');
                                            }
                                            </c:if>
                                            <c:if test="${sysFdFieldMaps
                                            .fieldType == 'DATE'}">
                                            ,formatter:function(value,row,index){
                                                return timestamp2Datetime(value,'Y-m-d H:i');
                                            }
                                            </c:if>"></th>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${Existence == 0}">

                        </c:when>
                        <c:otherwise>
                            <div title="附件"
                                 data-options="id:'tab1',iconCls:'fa fa-th'">
                                <table data-toggle="topjui-datagrid"
                                       data-options="id:'attachmentDg',
                                   initCreate: false,
                                   url:'${ctx}/system/cfromcformAttachment/getListByPuuid'">
                                    <thead>
                                    <tr>
                                    <tr>
                                        <th data-options="field:'uuid',title:'UUID',checkbox:true"></th>

                                        <th data-options="field:'fileName',title:'文件名称',
                                         formatter: function(value,row,index){
                                         var url = '/system/cfromcformAttachment/download?filePath='+row.filePath+'&fileName='+row.fileName;
                                        return '<a href='+url+'>'+value+'</a>';
                                        }"></th>
                                        <th data-options="field:'fileSize',title:'文件大小',
                                            formatter: function(value,row,index){
                                                return bytesToSize(value);
                                            }"></th>
                                        <th data-options="field:'creator',title:'上传人'"></th>
                                        <th data-options="field:'createTime',title:'上传时间'"></th>

                                    </tr>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>

        </c:otherwise>
    </c:choose>
    <!-- 表格工具栏 -->

    <div id="${TableName}Dg-toolbar" class="topjui-toolbar"
         data-options="grid:{
           type:'<c:choose><c:when test="${sysFdTable.tableType == 'datagrid'}">datagrid</c:when><c:otherwise>treegrid</c:otherwise></c:choose>',
           id:'${TableName}Dg'
       }" style="display:none">

        <c:choose>
            <c:when test="${sysFdTable.tableType == 'datagrid'}">
                <a href="javascript:void(0)"
                   data-toggle="topjui-menubutton"
                   data-options="method:'openDialog',
                           extend:'#${TableName}Dg-toolbar',
                           btnCls:'topjui-btn-green',
                           dialog:{
                               id:'${TableName}AddDialog',
                               href:'${ctx}/system/formDefine/add?tableUuid=${sysFdTable.uuid}',
                               height:450,
                               buttonsGroup:[
                                   {text:'保存',url:'${ctx}/system/formDefine/save?tableName=${sysFdTable.tableName}',iconCls:'fa fa-plus',handler:'ajaxForm'}
                               ]
                           }">新增</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:void(0)"
                   data-toggle="topjui-menubutton"
                   data-options="method:'openDialog',
                           extend:'#${TableName}Dg-toolbar',
                           btnCls:'topjui-btn-green',
                           dialog:{
                               id:'${TableName}AddDialog',
                               href:'${ctx}/system/formDefine/add?tableUuid=${sysFdTable.uuid}',
                               height:450,
                               buttonsGroup:[
                                   {text:'保存',url:'${ctx}/system/formDefine/save',iconCls:'fa fa-plus',handler:'ajaxForm'}
                               ]
                           }">新增根节点</a>
                <a href="javascript:void(0)"
                   data-toggle="topjui-menubutton"
                   data-options="method:'openDialog',
                           extend:'#${TableName}Dg-toolbar',
                           btnCls:'topjui-btn-green',
                            parentGrid:{
                           type:'treegrid',
                           id:'${TableName}Dg',
                           param:'pid:id,levelId',
                           unselectedMsg:'请先选中要添加的上级节点！'
                            },
                           dialog:{
                               id:'${TableName}SonAddDialog',
                               href:'${ctx}/system/formDefine/add?tableUuid=${sysFdTable.uuid}',
                               height:450,
                               buttonsGroup:[
                                   {text:'保存',url:'${ctx}/system/formDefine/saveSon',iconCls:'fa fa-plus',handler:'ajaxForm'}
                               ]
                           }">新增子节点</a>

            </c:otherwise>
        </c:choose>

        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'openDialog',
           extend:'#${TableName}Dg-toolbar',
           btnCls:'topjui-btn-blue',
           component:'edit',
           iconCls:'fa fa-pencil',
           dialog:{
               id:'${TableName}EditDialog',
                 <%--href:'${ctx}/system/formDefine/edit?tableUuid=${sysFdTable.uuid}',--%>
                  <%--href:'${ctx}/system/formDefine/treeEdit?tableUuid=${sysFdTable.uuid}',--%>
              <c:choose>
                 <c:when test="${sysFdTable.tableType == 'datagrid'}">
                    href:'${ctx}/system/formDefine/edit?tableUuid=${sysFdTable.uuid}',
                 </c:when>
                 <c:otherwise>
                    href:'${ctx}/system/formDefine/treeEdit?tableUuid=${sysFdTable.uuid}',
                 </c:otherwise>
              </c:choose>
               url:'${ctx}/system/formDefine/getDetailByUuid?tableUuid=${sysFdTable.uuid}&uuid={${sizeUuid}}',
               height:450,
               editor:[
              <c:forEach items="${componentTypeMap}" var="componentTypeMaps" varStatus="status">
                  {
                        type:'ueditor',id:'${componentTypeMaps.fieldName}EditContainer',field:'${componentTypeMaps.fieldName}'
                   }<c:if test="${status.last == false}">,</c:if>

              </c:forEach>
               ],
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/formDefine/update?tableName=${sysFdTable.tableName}',iconCls:'fa fa-save',handler:'ajaxForm',refresh:[{type:'datagrid',id:'formDefine'}]}
               ]
           }">编辑</a>

        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'doAjax',
           extend:'#${TableName}Dg-toolbar',
           btnCls:'topjui-btn-red',
	       iconCls:'fa fa-trash',
	       grid:{uncheckedMsg:'请先勾选要删除的用户',param:'uuid:${sizeUuid}'},
	       url:'${ctx}/system/formDefine/deleteBatch?tableUuid=${sysFdTable.uuid}'">删除</a>

        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'filter',
       extend:'#${TableName}Dg-toolbar',
       btnCls:'topjui-btn-orange'">过滤</a>
        <a href="javascript:void(0)"
           data-toggle="topjui-menubutton"
           data-options="method:'search',
       extend:'#${TableName}Dg-toolbar',
       btnCls:'topjui-btn-purple'">查询</a>
    </div>
    <c:forEach items="${sysFdTables}" var="sysFdTabless">


        <!-- 表格工具栏 -->
        <div id="${sysFdTabless.tableName}Dg-toolbar" class="topjui-toolbar"
             data-options="grid:{
                type:'datagrid',
                id:'${sysFdTabless.tableName}Dg'
                }" style="display:none">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#${sysFdTabless.tableName}Dg-toolbar',
           btnCls:'topjui-btn-green',
           parentGrid:{
		   type:'datagrid',
		   id:'${TableName}Dg',
		   param:'puuid:${sizeUuid},id:id'
			 },
           dialog:{
               id:'${sysFdTabless.tableName}AddDialog',
               href:'${ctx}/system/formDefine/add?tableUuid=${sysFdTabless.uuid}',
               height:450,
               buttonsGroup:[
                   {text:'保存',url:'${ctx}/system/formDefine/save?tableName=${sysFdTable.tableName}',iconCls:'fa fa-plus',handler:'ajaxForm'}
               ]
           }">新增</a>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#${sysFdTabless.tableName}Dg-toolbar',
           btnCls:'topjui-btn-blue',
           component:'edit',
           iconCls:'fa fa-pencil',
           dialog:{
               id:'${sysFdTabless.tableName}EditDialog',
               href:'${ctx}/system/formDefine/edit?tableUuid=${sysFdTabless.uuid}',
               url:'${ctx}/system/formDefine/getDetailByUuid?tableUuid=${sysFdTabless.uuid}&uuid={${sizeUuid}}',
               height:450,

               editor:[
        <c:forEach items="${sysFdFieldMap.get(sysFdTabless.uuid)}" var="sysFdFieldMaps">
               <c:if test="${sysFdFieldMaps.componentType == 'ueditor'}">
                   {type:'ueditor',id:'${sysFdFieldMaps.fieldName}EditContainer',field:'${sysFdFieldMaps.fieldName}'},
                </c:if>
        </c:forEach>
               ],

               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/formDefine/update',iconCls:'fa fa-save',handler:'ajaxForm',refresh:[{type:'datagrid',id:'formDefine'}]}
               ]
           }">编辑</a>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#${sysFdTabless.tableName}Dg-toolbar',
           btnCls:'topjui-btn-red',
	       iconCls:'fa fa-trash',
	       grid:{uncheckedMsg:'请先勾选要删除的用户',param:'uuid:uuid'},
	       url:'${ctx}/system/formDefine/deleteBatch?tableUuid=${sysFdTabless.uuid}'">删除</a>
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'filter',
               extend:'#${sysFdTabless.tableName}Dg-toolbar',
               btnCls:'topjui-btn-orange'">过滤</a>


        </div>

    </c:forEach>
    <!-- 附件 表格工具栏 -->
    <c:choose>
        <c:when test="${Existence == 0}">
        </c:when>
        <c:otherwise>
            <div id="attachmentDg-toolbar" class="topjui-toolbar"
            data-options="grid:{
            type:'datagrid',
            id:'attachmentDg'
            }" style="display:none">
            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'upload',
           extend:'#attachmentDg-toolbar',
           iconCls:'fa fa-cloud-upload',
           btnCls:'topjui-btn-green',
           accept:'file',
           dialog:{
               title:'附件批量上传'
           },
           parentGrid:{
		   type:'datagrid',
		   id:'${TableName}Dg',
		   param:'puuid:${sizeUuid}'
			 },
           uploadUrl:'/system/cfromcformAttachment/upload?module=exceptionHeader&category=default&puuid={${sizeUuid}}'">添加附件</a>

            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'openDialog',
           extend:'#attachmentDg-toolbar',
           iconCls:'fa fa-pencil',
           btnCls:'topjui-btn-blue',
           dialog:{
               id:'attachmentEditDialog',
               href:'${ctx}/system/attachment/editCustom',
               url:'${ctx}/system/cfromcformAttachment/getDetailByUuid?uuid={uuid}',
               buttonsGroup:[
                   {text:'更新',url:'${ctx}/system/cfromcformAttachment/update',iconCls:'fa fa-save',handler:'ajaxForm'}
               ]
           }">编辑</a>

            <a href="javascript:void(0)"
               data-toggle="topjui-menubutton"
               data-options="method:'doAjax',
           extend:'#attachmentDg-toolbar',
	       url:'${ctx}/system/cfromcformAttachment/delete',
	       btnCls:'topjui-btn-red'">删除</a>
        </c:otherwise>
    </c:choose>

    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/ueditor/ueditor.all.min.js"></script>

</rapid:override>

<jsp:include page="../../common/common/base.jsp" flush="true"></jsp:include>