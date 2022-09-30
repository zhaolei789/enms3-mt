$(function () {
	
	var params = {		
		treegridId      : '#treegrid',
		addDialogId     : '#editDialog',
		editDialogId    : '#editDialog',
		
		datagridUrl      : ctx + '/System/Menu/getAllList',
		getDetailUrl     : ctx + '/System/Menu/getDetailByUuid',
		addDialogHref    : ctx + '/System/Menu/add',
		saveUrl          : ctx + '/System/Menu/save',
		editDialogHref   : ctx + '/System/Menu/edit',
		updateUrl        : ctx + '/System/Menu/update',
		deleteUrl        : ctx + '/System/Menu/delete',
		
		addDialogTitle   : '新增',
		addDialogWidth   : 300,
		addDialogHeight  : 350,
		
		editDialogTitle  : '编辑',
		editDialogWidth  : 300,
		editDialogHeight : 350,
		
		filterOption     : [],
		columns:[[
		            {field:'text',title:'名称'},
		            {field:'codeSetId',title:'体系代码',width:100},
		            {field:'id',title:'编号'},
		            {field:'pid',title:'父级编号'},
		            {field:'levelId',title:'层级',width:100},
		            {field:'sort',title:'排序',width:100},
		            {field:'code',title:'代码',width:100},
		            {field:'status',title:'状态',width:100}
		        ]]
	};
	   
	$('#treegrid').iTreegrid({
        url:ctx + '/System/Menu/getListByCodeSetIdAndLevelId',
        queryParams:{"codeSetId":"1","levelId":"0"},
        onBeforeExpandUrl:ctx + "/System/Menu/getListByPid",
        columns:params.columns
    });
	   
	$('#editDialog').iDialog({
		datagridId      : params.treegridId,
		currentDialogId : params.editDialogId,
		width           : params.editDialogWidth,
		height          : params.editDialogHeight,
		title           : params.editDialogTitle,
		saveUrl         : params.saveUrl,
		updateUrl       : params.updateUrl,
		editFields      : params.editFields,
		combotreeFields : params.combotreeFields,
		href            : params.editDialogHref
	});
	
	$("#treegridContext").menu({
        onClick : function (item) {
        	treegridContextOprate(this, item, '#treegrid', '#editDialog');
        }
    });
	
    function treegridContextOprate(menu, item, treegridId, editDialogId) {
    	var selectedRow = $(treegridId).treegrid('getSelected');
        switch (item.name) {
            case "add":
            	
            	clearDialogHrefKeyValue(editDialogId, "action");
                $(editDialogId).dialog("open");
                var jsonData = {
        			"codeSetId":selectedRow.codeSetId,
        			"pid":selectedRow.id
                };
                setTimeout(function() {
                	$(editDialogId).form('load', jsonData);
                }, 100);
                
                $(params.addDialogId).dialog('setTitle', params.addDialogTitle);
				$('#addBtn').show();
				$('#saveBtn').hide();
				
                return false;
                break;
            case "edit":
            	clearDialogHrefKeyValue(editDialogId, "action");
            	setDialogHrefKeyValue(editDialogId, "action", "edit");
            	
                $(editDialogId).dialog("open");
                
                $(params.editDialogId).dialog('setTitle', params.editDialogTitle);
				$('#addBtn').hide();
				$('#saveBtn').show();
                
                setTimeout(function() {
                	$(editDialogId).form('load', params.getDetailUrl + "?uuid=" + selectedRow.uuid);
                }, 100);
                return false;
                break;
            case "refresh":
                $(treegridId).treegrid('reload', selectedRow.id);
                break;
            case "remove":
            	$.messager.confirm('提示','确定要删除吗？',function(r){
            		if (r){
            			$.ajax({
        					url : ctx + '/System/Menu/delete',
        					type : 'post',
        					data : {"uuids":"'" + selectedRow.uuid + "'"},
        					beforeSend : function () {
        						$.messager.progress({
        							text : '正在操作...'
        						});
        					},
        					success : function (data, response, status) {
        						$.messager.progress('close');
        						if (data > 0) {
        							$.messager.show({
        								title : '提示',
        								msg : '操作成功'
        							});
        							$(treegridId).treegrid('reload', selectedRow.pid);
        						} else {
        							$.messager.alert('操作失败！', '未知错误或没有任何修改，请重试！', 'warning');
        						}
        					}
        				});
            		}
            	});
                break;
        }
    }
	
});