$(function () {
	
	var params = {
		datagridId       : "#datagrid",
		addDialogId      : "#addDialog",
		editDialogId     : "#editDialog",
		
		datagridUrl      : ctx + "/system/log/getPageSetData",
		getDetailUrl     : ctx + "/system/log/getDetailByUuid",
		addDialogHref    : ctx + "/system/log/add",
		saveUrl          : ctx + "/system/log/save",
		editDialogHref   : ctx + "/system/log/edit",
		updateUrl        : ctx + "/system/log/update",
		deleteUrl        : ctx + "/system/log/delete",
		
		addDialogTitle   : "新增",
		addDialogWidth   : 600,
		addDialogHeight  : 400,
		
		editDialogTitle  : "编辑",
		editDialogWidth  : 600,
		editDialogHeight : 400,

		filterOption     : [],
	
		columns: [[
					{
						field    : "uuid",
						title    : "UUID",
						hidden   : true
					},
					{
						field : "description",
						title : "操作描述"
					},
					{
						field : "requestUrl",
						title : "请求URL"
					},
					{
						field : "params",
						title : "传递参数"
					},
					{
						field : "takeTime",
						title : "耗时（ms）"
					},
					{
						field : "ipAddress",
						title : "IP地址"
					},
					{
						field : "creator",
						title : "操作人"
					},
					{
						field : "createTime",
						title : "操作时间"
					}
				]]
	};
			
	$(params.datagridId).iDatagrid({
		datagridUrl  : params.datagridUrl,
		getDetailUrl : params.getDetailUrl,
		addDialogId  : params.editDialogId,
		saveUrl      : params.saveUrl,
		editDialogId : params.editDialogId,
		updateUrl    : params.updateUrl,
		deleteUrl    : params.deleteUrl,
		editFields   : params.editFields,
		filterOption : params.filterOption,
		columns      : params.columns
	});
	
	$(params.editDialogId).iDialog({
		datagridId      : params.datagridId,
		currentDialogId : params.editDialogId,
		width           : params.editDialogWidth,
		height          : params.editDialogHeight,
		title           : params.editDialogTitle,
		saveUrl         : params.saveUrl,
		updateUrl       : params.updateUrl,
		editFields      : params.editFields,
		href            : params.editDialogHref
	});
	
});
