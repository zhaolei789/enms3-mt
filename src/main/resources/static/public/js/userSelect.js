

//初始化两个表格
$(".userDataTable").iDatagrid({
    onDblClickRow:function (index,row) {
        var arr = new Array();
        arr.push(row)
        Operate.addRows(Operate.userDatagrid,Operate.receiverDatagrid,arr);
        Operate.removeRows(Operate.userDatagrid, arr);
    }
});
$(".receiverDataTable").iDatagrid({
    onDblClickRow:function (index,row) {
        var arr = new Array();
        arr.push(row);
        Operate.addRows(Operate.receiverDatagrid,Operate.userDatagrid,arr);
        Operate.removeRows(Operate.receiverDatagrid, arr);
    },
});
//相关操作的js脚本
var Operate = {
    key:"uuid",
    userDatagrid: $('.userDataTable'),//用户表
    receiverDatagrid: $('.receiverDataTable'),//接受传阅的用户
    operateData: null,//被选中的数据

    addChoose: function () {
        this.commonHandle(this.userDatagrid,this.receiverDatagrid,"请选中需要操作的数据！",false);
    },
    removeChoose: function () {
        this.commonHandle(this.receiverDatagrid,this.userDatagrid,"请选中需要操作的数据！",false);
    },
    addAll: function () {
        this.commonHandle(this.userDatagrid,this.receiverDatagrid,"左侧列表，无用户信息！",true);
    },
    removeAll: function () {
        this.commonHandle(this.receiverDatagrid,this.userDatagrid,"右侧列表，无用户信息！",true);
    },
    commonHandle:function (fromDagrid,toDagrigd,prompt,isAll) {
        if(isAll){
            this.operateData = fromDagrid.datagrid('getRows');
        }else{
            this.operateData = fromDagrid.iDatagrid('getSelections');
        }
        if (this.operateData == false) {
            return $.iMessager.alert('提示', prompt);
        }
        this.addRows(fromDagrid,toDagrigd, this.operateData);
        this.removeRows(fromDagrid, this.operateData);
    },
    addRows: function (fromDagrid,toDagrigd, data) {
        var toDagrigdRows = toDagrigd.datagrid('getRows');
        var length = toDagrigdRows.length;
        if(length ==0){
            for (var i = 0; i < data.length; i++) {
                toDagrigd.iDatagrid('appendRow', data[i]);
            }
        }else{
            var flag = true;
            for(var j = 0 ; j<data.length ; j++ ){
                for(var k = 0 ; k < length ; k++){
                    if(data[j][this.key] == toDagrigdRows[k][this.key] && data[j][this.key]){
                        flag = false;
                        break;
                    }else{
                        flag = true;
                    }
                }
                if(flag){
                    toDagrigd.iDatagrid('appendRow', data[j]);
                }
            }
        }
    },
    removeRows: function (dataGrid, dataArr) {
        var length = dataArr.length;
        for (var i = length-1; i >= 0; i--) {
            var rowIndex = dataGrid.datagrid('getRowIndex', dataArr[i]);
            dataGrid.iDatagrid('deleteRow', rowIndex);
        }
    },
    searchByDepartment:function () {
        var orgId = $('#org').iCombotree('getValue');
        Operate.userDatagrid.iDatagrid('load',{
            id:orgId,
            page:1,
            rows:100,
        });
    },
    searchByKeyWords:function () {
        var keywords = $('#keywords').iTextbox('getValue');
        debugger;
        console.log(keywords+"======keywords======")
        Operate.userDatagrid.iDatagrid('load',{
            id:keywords,
            page:1,
            rows:100,
        });
    },
    formSubmit:function () {
            var data = $(".dataForm").serializeObject();
            $.post(SCOPE.searchUrl,data,function (res) {
                $.iMessager.show({
                    title:'我的消息',
                    msg:'查询成功',
                    timeout:3000,
                    showType:'slide'
                });
                Operate.userDatagrid.iDatagrid('loadData',res);
            })
    },

    selectFormSubmit:function () {
        var data = $(".dataForm").serializeObject();
        debugger
        console.log(data);
        $.post('/mdata/user/getListByKeywords',data,function (res) {
            $.iMessager.show({
                title:'我的消息',
                msg:'查询成功',
                timeout:3000,
                showType:'slide'
            });
            Operate.userDatagrid.iDatagrid('loadData',res);
        })
    }
}
