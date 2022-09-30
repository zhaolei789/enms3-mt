var ctx = "";
var getCodeitemListByPid = ctx + "/system/codeItem/getListByPid";

var LODOP; //声明为全局变量
function printPreview(printAreaId) {
    createOneFormPage(printAreaId);
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    LODOP.PREVIEW();
}

function createOneFormPage(printAreaId) {
    LODOP = getLodop();
    var strStyleCSS = "<link href='/static/topjui/ext/css/print.css' type='text/css' rel='stylesheet'>";
    var strFormHtml = strStyleCSS + document.getElementById(printAreaId).innerHTML;
    LODOP.PRINT_INIT(printAreaId);
    LODOP.ADD_PRINT_HTM(40, 40, 350, 600, strFormHtml);
}

/**
 * 获得打印时额外的CSS样式
 * @returns {string}
 */
function getSinglePrintCss() {
    var printCSS = "<link href='/static/topjui/ext/css/print.css?version=" + Math.random() + "' type='text/css' rel='stylesheet'>";
    console.log(printCSS);
    return printCSS;
}

function getPrintCss(elementId) {
    var strBodyStyle = "<style>" + document.getElementById("style").innerHTML + "</style>";
    return strBodyStyle + "<body>" + document.getElementById(elementId).innerHTML + "</body>";
}

function addMainTab(url, title, iconCls) {
    var iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
    var t = $('#index_tabs');
    var opts = {
        title: title,
        closable: true,
        iconCls: iconCls,
        content: iframe,
        border: false,
        fit: true,
        cls: 'leftBottomBorder'
    };
    if (t.tabs('exists', opts.title)) {
        t.tabs('select', opts.title);
    } else {
        t.tabs('add', opts);
    }
}

/**
 * 自动完成下拉框中格式化显示数据
 * @param row
 * @returns {string}
 */
function formatUserAutoCompleteItem(row) {
    var s = '<i class="icon-user"></i><span style="font-weight:bold">' + row.userName + '</span><br/>' +
        '<span style="color:#888">帐号：' + row.userNameId + '</span><br/>' +
        '<span style="color:#888">机构：' + row.orgName + '</span>';
    return s;
}
// 加两个方法
(function ($) {
    var rootNodeId = "1";
    function appendNodes(dataList, target) {
        // 如果不存在则是根节点
        if (target.length == 0) {
            var arr = findChildByPid(dataList,rootNodeId);
            for(var i = 0;i<arr.length;i++){
                target.push(arr[i]);
            }
        }
        // 如果不是根节点插入children
        $.each(target,function (i) {
            var resArr = findChildByPid(dataList,target[i].id);
            if(resArr.length > 0){
                target[i].children = resArr;
                appendNodes(dataList,target[i].children);
            }
        })
    };
    function findChildByPid(data, pid) {
        var resultArr = []; // 保存查询结果
        $.each(data, function (index) {
            if (data[index]["pid"] == pid) {
                resultArr.push(data[index]);
            }
        });
        return resultArr;
    };
    $.extend({
        getTreeList:function (dataList,root) {
            rootNodeId = root||rootNodeId;
            var target = [];
            appendNodes(dataList,target);
            return target;
        },
        formateTreeData:function (oldData, textField, idFiled,pidFiled) {
            var newData = [];
            idFiled = idFiled || 'id';
            textField = textField || "text";
            pidFiled = pidFiled || 'gPid';
            $.each(oldData, function (i) {
                newData.push($.extend(oldData[i], {id: oldData[i][idFiled], text: oldData[i][textField],pid:oldData[i][pidFiled]}))
            });
            return newData;
        }
    });
})(jQuery)