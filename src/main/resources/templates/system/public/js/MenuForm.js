$(function () {
	
	
	setTimeout(function() {
		
		//$('#pid').combotree('tree').tree('expandTo', $('#pid').combotree('tree').tree('find', 177).target);
		
		$("#pid").combotree({
			url: ctx + "/System/Menu/getListByPid?pid=0",
			valueField : 'id',
			textField : 'text',
			required : false,
			lines : true,
			multiple : false,
			checkbox : true,
			onlyLeafCheck : false,
			editable : false,
			readonly : false,
			animate  : true,
			expandAll : true,
			panelWidth : 250,
			onBeforeExpand:function(node,param) {
                $("#pid").combotree('tree').tree('options').url = ctx + "/System/Menu/getListByPid?pid=" + node.id;
            },
            onLoadSuccess : function() {
            	$("#pid").combotree('tree').tree("expandAll");
            	//$(options.treeId).combotree('tree').tree("expand", $(options.treeId).combotree('tree').tree('getRoot').target);
            }

	    });
		
		$('#topMenu').combobox({
			data:[
			    {
					label: '子级菜单',
					value: 2
				},
				{
					label: '顶级菜单',
					value: 1
				}
			],
			valueField:'value',
			textField:'label',
			value:2,
			editable:false,
			panelHeight:50,
			onChange:function(newValue, oldValue){
				if(newValue == 1) {
					$('#pid').combotree('setValue', 0);
					$('#pid').combotree('readonly');
				} else {
					$('#pid').combotree('readonly', false);
				}
			}
	    });
		
		$('#status').combobox({
			data:[
			    {
					text: '启用',
					value: 1
				},
				{
					text: '禁用',
					value: 2
				}
			],
			valueField:'value',
			textField:'label',
			value:2,
			editable:false,
			panelHeight:50,
			onChange:function(newValue, oldValue){
				if(newValue == 1) {
					$('#pid').combotree('setValue', 0);
					$('#pid').combotree('readonly');
				} else {
					$('#pid').combotree('readonly', false);
				}
			}
	    });
		
		$('#state').combobox({
			data:[
			    {
					label: '没有子项',
					value: 'open'
				},
				{
					label: '存在子项',
					value: 'closed'
				}
			],
			valueField:'value',
			textField:'label',
			editable:false,
			panelHeight:50
	    });
		
    }, 500);
	
	
});