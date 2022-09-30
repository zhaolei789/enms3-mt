$(function() {

	$('#userName').iAutoComplete({
        fieldId:'#userNameId',
        required:true
    });
	
	$("#theParty").iCombobox({
		codeSetId : 'BB',
		levelId : '1',
		panelHeight : 132,
        required:true
	});
	
	$("#theParty2").iCombobox({
		codeSetId : 'BB',
		levelId : '1',
		panelHeight : 132,
        required:true
	});
	
	$("#localUnitId").iCombotree({
		initUrl : ctx + "/System/Organization/getListByPid?codeSetId=@k&pid=2",
		url : ctx + "/System/Organization/getListByPid",
		width : 450,
        required:true
	});
	
	$("#firstDeptId").iCombotree({
		initUrl : ctx + "/System/Organization/getListByPid?codeSetId=@k&pid=2",
		url : ctx + "/System/Organization/getListByPid",
		width : 450,
        required:true
	});
	
	$("#secondDeptId").iCombotree({
		initUrl : ctx + "/System/Organization/getListByPid?codeSetId=@k&pid=2",
		url : ctx + "/System/Organization/getListByPid",
		width : 450,
        required:true
	});
	
	$("#partyCommitteeId1").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450
	});

	$("#partyBranchId1").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450
	});
	
	$("#partyGroupId1").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450
	});
	
	$("#partyCommitteeId").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450,
        required:true
	});

	$("#partyBranchId").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450,
        required:true
	});
	
	$("#partyGroupId").iCombotree({
		codeSetId : 'DK',
		levelId : '1',
		width : 450,
        required:true
	});

	$("#userSex").iCombobox({
		codeSetId : 'AAAA',
		levelId : '1',
		panelHeight : 50,
        required:true
	});

	$("#nativeplace").iCombobox({
		codeSetId : 'AAAC',
		levelId : '1',
		panelHeight : 220
	});

	$("#nation").iCombobox({
		codeSetId : 'AAAB',
		levelId : '2',
		panelHeight : 220
	});

	$("#degree").iCombobox({
		codeSetId : 'AABB',
		levelId : '1',
		panelHeight : 220
	});

	$("#education").iCombobox({
		codeSetId : 'AABA',
		levelId : '1',
		panelHeight : 220
	});

	$("#userGroup").combotree({
		url : ctx + '/system/authGroup/getListByPid?pid=0',
		multiple : true,
		panelWidth : 300,
		width : 450,
		onBeforeExpand : function(node, param) {
			$(this).tree('options').url = ctx + '/system/authGroup/getListByPid?pid=' + node.id;
		},
		onLoadSuccess : function() {
			$("#userGroup").combotree('tree').tree("expandAll");
		}
	});

	$(".topjuiyesOrNo").iCombobox({
		codeSetId : 'ACA',
		levelId : '1'
	});
	
	$("#nativePlace").iCombobox({
		codeSetId : 'AAAC',
		levelId : '1',
		panelHeight : 220
	});

	$("#userstate").iCombobox({
		codeSetId : 'AAAD',
		levelId : '1',
		panelHeight : 66
	});

	$('.topjuiNumberspinner').iNumberspinner({
		defaultValueType : 'currentSeason'
	});

	$('#partyDues').iNumberspinner({
	// defaultValue : '100'
	});

});
