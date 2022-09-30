$(function () {
	
	var params = {
		datagridId       : '#userDatagrid',
		addDialogId      : '#editDialog',
		editDialogId     : '#editDialog',
		
		datagrid2Id      : '#datagrid2',
		addDialog2Id     : '#editDialog',
		editDialog2Id    : '#editDialog',
		
		datagridUrl      : ctx + '/ucenter/user/getAllList',
		getDetailUrl     : ctx + '/ucenter/user/getDetailByUuid',
		addDialogHref    : ctx + '/ucenter/user/edit',
		saveUrl          : ctx + '/ucenter/user/save',
		editDialogHref   : ctx + '/ucenter/user/edit',
		updateUrl        : ctx + '/ucenter/user/update',
		deleteUrl        : ctx + '/ucenter/user/delete',
		
		addDialogTitle   : '新增',
		addDialogWidth   : 600,
		addDialogHeight  : 400,
		
		editDialogTitle  : '编辑',
		editDialogWidth  : 600,
		editDialogHeight : 400,
		
		//combotreeFields  : ["userGroup"],
		
		filterOption     : [{
			field:'name',
			type:'text',
			op:['contains','equal','notequal','beginwith','endwith','less','lessorequal','greater','greaterorequal']
		},{
			field:'sex',
			type:'combobox',
			options:{
				url:ctx + '/system/codeItem/getListByCodeSetIdAndLevelId?codeSetId=22&levelId=1',
				valueField:'id',
				textField:'text',
				editable:false,
				panelHeight:50
			},
			op:['contains','equal','notequal','beginwith','endwith','less','lessorequal','greater','greaterorequal']
		},{
			field:'orgId',
			type:'combotree',
			options:{
				url:getCodeitemListByPid + "?pid=1",
				panelWidth:200,
                onBeforeExpand:function(node,param) {
                    $(this).tree('options').url = getCodeitemListByPid + "?pid=" + node.id;
                }
			},
			op:['contains','equal','notequal','beginwith','endwith','less','lessorequal','greater','greaterorequal']
		},{
			field:'birthday',
			type:'datebox',
			op:['contains','equal','notequal','beginwith','endwith','less','lessorequal','greater','greaterorequal']
		}]
	};
	
	var partyUserParams = {
			datagridId       : '#partyUserDatagrid',
			addDialogId      : '#partyUserEditDialog',
			editDialogId     : '#partyUserEditDialog',
			
			datagridUrl      : ctx + '/ucenter/user/getPartyUserList',
			getDetailUrl     : ctx + '/ucenter/user/getDetailByUuid',
			addDialogHref    : ctx + '/ucenter/user/partyUserEdit',
			saveUrl          : ctx + '/ucenter/user/partyUserSave',
			editDialogHref   : ctx + '/ucenter/user/partyUserEdit',
			updateUrl        : ctx + '/ucenter/user/partyUserUpdate',
			deleteUrl        : ctx + '/ucenter/user/delete',
			columns          : [[
									{
										field    : 'uuid',
										title    : 'UUID',
										hidden   : true
									},
									{
										field : 'partyCommittee',
										title : '所在党委'
									},
									{
										field : 'partyBranch',
										title : '所在党支部'
									},
									{
										field : 'userNameId',
										title : '员工号'
									},
									{
										field : 'userName',
										title : '姓名'
									},
									{
										field : 'userSex',
										title : '性别'
									},
									{
										field : 'nation',
										title : '民族'
									},
									{
										field : 'birthday',
										title : '出生年月'
									},
									
									{
										field : 'theParty',
										title : '政治面貌'
									},
									{
										field : 'education',
										title : '学历'
									},
									{
										field : 'partyTimepre',
										title : '成为预备党员日期',
										formatter: function(value, row, index) {
											return subString(value, 0, 10);
					                    }
									},
									{
										field : 'partyTimereg',
										title : '预备党员转正日期',
										formatter: function(value, row, index) {
											return subString(value, 0, 10);
					                    }
									},
									{
										field : 'userKind',
										title : '岗位性质'
									},
									{
										field : 'partyState',
										title : '党员状态'
									},
									{
										field : 'handle',
										title : '操作',
										formatter: function(value, row, index) {
					                        var ctrs = ["<a href=\"#\" onclick=\"openDialog({'dialogId':'dangfeiDialog','title':'新增党费','href':'"+ctx+"/Party/Dangfei/edit?uuid="+row.uuid + "','url':'"+ctx+"/Party/Dangfei/save'})\">党费</a>丨", 
					                                    "<a href=\"#\" onclick=\"zhuanjieEventDialog('"+row.uuid+"')\">转接</a>丨",
					                                    "<a href=\"#\" onclick=\"zhuanruEventDialog('"+row.uuid+"')\">转入</a>丨",
					                                    "<a href=\"#\" onclick=\"zhuanchuEventDialog('"+row.uuid+"')\">转出</a>"];
					                        return ctrs.join(' ');
					                    }
									}
							 ]]
		};
	
	$("#organizationTree").tree({
        url: ctx + '/System/Organization/getFirstLevelUNList',
        lines: true,
        animate: true,
        onContextMenu: function(e, node){
			e.preventDefault();
			// 查找节点
			$(this).tree('select', node.target);
			// 显示快捷菜单
			$('#organizationContextOrg').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		},
        onBeforeExpand:function(node,param) {
            $(this).tree('options').url = ctx + '/System/Organization/getListByPid' + "?pid=" + node.id;
        },
        onClick : function(node) {
    		var dg = $("#userDatagrid");
     	    var queryParams = dg.datagrid('options').queryParams;
     	    queryParams2 = {};
     	    console.log(node);
     	    queryParams2.codeSetId = node.codesetid;
     	    //queryParams2.codeItemId = node.id;
     	    queryParams2.orgId = node.id;
     	    dg.datagrid('options').queryParams = $.extend({}, queryParams, queryParams2);
     	    dg.datagrid('reload');
        },
        onLoadSuccess : function() {
        	$(this).tree("expand", $(this).tree('getRoot').target);
        }
    });
	
	
		
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
		columns : [[
					{
						field    : 'uuid',
						title    : 'UUID',
						hidden   : true
					},
					{
						field : 'userNameId',
						title : '员工号'
					},
					{
						field : 'userName',
						title : '姓名'
					},
					{
						field : 'userSex',
						title : '性别'
					},
					{
						field : 'userAge',
						title : '年龄'
					},
					{
						field : 'userStation',
						title : '岗位'
					},
					{
						field : 'localUnitJc',
						title : '所在单位'
					},
					{
						field : 'firstDept',
						title : '一级部门'
					},
					{
						field : 'secondDept',
						title : '二级部门'
					},
					{
						field : 'handle',
						title : '操作',
						formatter: function(value, row, index) {
	                        var ctrs = ["<a href=\"#\" onclick=\"updatePasswordDialog('"+row.uuid+"')\">重置密码</a>"];
	                        return ctrs.join(' ');
	                    }
					}
				]]
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
		combotreeFields : params.combotreeFields,
		href            : params.editDialogHref
	});
	
	
	$("#partyUserTree").iTree({
		codeSetId : 'DK',
		levelId   : '0',
		method : 'postTreeParamsAndRefreshDatagrid',
		refreshDatagridId : partyUserParams.datagridId
	});
	
	$(partyUserParams.datagridId).iDatagrid({
		datagridUrl  : partyUserParams.datagridUrl,
		getDetailUrl : partyUserParams.getDetailUrl,
		addDialogId  : partyUserParams.addDialogId,
		//saveUrl      : partyUserParams.saveUrl,
		editDialogId : partyUserParams.editDialogId,
		//updateUrl    : partyUserParams.updateUrl,
		deleteUrl    : partyUserParams.deleteUrl,
		columns      : partyUserParams.columns 
	});
	
	$(partyUserParams.editDialogId).iDialog({
		datagridId      : partyUserParams.datagridId,
		currentDialogId : partyUserParams.editDialogId,
		width           : partyUserParams.editDialogWidth,
		height          : partyUserParams.editDialogHeight,
		title           : partyUserParams.editDialogTitle,
		saveUrl         : partyUserParams.saveUrl,
		updateUrl       : partyUserParams.updateUrl,
		href            : partyUserParams.editDialogHref
	});
	
	zhuanjieEventDialog = function(uuid) {
	    $("#zhuanjieDialog").dialog({
	        title: '内部转接',
	        href: ctx + '/Party/Zhuanjie/edit?uuid=' + uuid+"&source=DYXX",
	        width           : 600,
			height          : 400,
	        buttons: [{
					text : '新增',
					id : 'saveBtn',
					iconCls : 'fa fa-plus',
					handler : function () {
											
						if ($(this).form('validate')) {
							
							var formData = $("#zhuanjieDialog").serialize();															
							$.ajax({
								url : ctx + '/Party/Zhuanjie/save',
								type : 'post',
								data : formData,
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
										$("#zhuanjieDialog").dialog('close').form('reset');
									} else {
										$.messager.alert('操作失败！', '未知错误或没有任何修改，请重试！', 'warning');
									}
								}
							});
						}
					}
				},{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function () {
						$("#zhuanjieDialog").dialog('close').form('reset');
					}
				}],
				onLoad : function() {
					$(this).trigger(TOPJUI.eventType.initUI.form);					
				}
	    });
	};
	zhuanruEventDialog = function(uuid) {
	    $("#zhuanruDialog").dialog({
	        title: '转入易网',
	        href: ctx + '/Party/Zhuanru/edit?uuid=' + uuid+"&source=DYXX",
	        width           : 600,
			height          : 400,
	        buttons: [{
					text : '新增',
					id : 'saveBtn',
					iconCls : 'fa fa-plus',
					handler : function () {
											
						if ($(this).form('validate')) {
							
							var formData = $("#zhuanruDialog").serialize();															
							$.ajax({
								url : ctx + '/Party/Zhuanru/save',
								type : 'post',
								data : formData,
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
										$("#zhuanruDialog").dialog('close').form('reset');
									} else {
										$.messager.alert('操作失败！', '未知错误或没有任何修改，请重试！', 'warning');
									}
								}
							});
						}
					}
				},{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function () {
						$("#zhuanruDialog").dialog('close').form('reset');
					}
				}],
				onLoad : function() {
					$(this).trigger(TOPJUI.eventType.initUI.form);					
				}
	    });
	};
	zhuanchuEventDialog = function(uuid) {
	    $("#zhuanchuDialog").dialog({
	        title: '转出易网',
	        href: ctx + '/Party/Zhuanchu/edit?uuid=' + uuid+"&source=DYXX",
	        width           : 600,
			height          : 400,
	        buttons: [{
					text : '新增',
					id : 'saveBtn',
					iconCls : 'fa fa-plus',
					handler : function () {
											
						if ($(this).form('validate')) {
							
							var formData = $("#zhuanchuDialog").serialize();															
							$.ajax({
								url : ctx + '/Party/Zhuanchu/save',
								type : 'post',
								data : formData,
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
										$("#zhuanchuDialog").dialog('close').form('reset');
									} else {
										$.messager.alert('操作失败！', '未知错误或没有任何修改，请重试！', 'warning');
									}
								}
							});
						}
					}
				},{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function () {
						$("#zhuanchuDialog").dialog('close').form('reset');
					}
				}],
				onLoad : function() {
					$(this).trigger(TOPJUI.eventType.initUI.form);					
				}
	    });
	};
});
updatePasswordDialog = function(uuid) {
	$.ajax({
		url : ctx + '/ucenter/user/updatePassword',
		data : {"uuid" : uuid},
		type : 'post',
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
			} else {
				$.messager.alert('操作失败！', '未知错误或没有任何修改，请重试！', 'warning');
			}
		}
	});
};