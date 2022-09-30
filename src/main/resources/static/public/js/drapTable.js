var dragElems = ["textbox", "passwordbox", "datetimespinner","timespinner", "numberbox", "textarea","maskedbox","numberspinner", "combobox", "datebox", "combotree","combotreegrid","combogrid", "radiobutton", "checkbox", "datetimebox"];
var newElems = [];// 定义组件元素数组
var properties = [];// 定义属性数组
var index = 1;// 元素id = ‘id’ + index
var dropPanelId = "dd";
var propertygridId = "pg";
var menuId = "mm";
var pgSelectedElemId = undefined; // 属性表格对应的组件
var menuResourceId = undefined;//右键点击时候的元素
var formId = GetRequest()['formId'];// 拿到formId
var compDgId = 'compDg' // 组件的id

var SCOPE = { //服务器
    compLoadUrl: "/form/formProperty/getFormPropertyByCompFormId", // 加载组件地址
    deleteCompUrl: "/form/formField/deleteBatch", // 删除组件
    compChangeSortUrl: "/form/formProperty/updateCmopSort", // 更改组件排序
    compPropertyTplUrl: "/form/formPropertyTpl/getformPropertyTplByType", // 获取组件属性模板
    compAddUrl: "/form/formField/insertFormProperty", // 添加组件
    compUpdateUrl: "/form/formField/update",// 组件更新
    compPreview: "/form/formDefine/preview?tableUuid=",//预览url
    compPreviewHtml:"/form/formDefine/html?tableUuid=",// 预览html代码
    compListGetUrl: "/form/formField/getField" // 获取组件列表信息
}


// ========================组件拖动
$('#' + dropPanelId).iDroppable({
    accept: getDragElemStr(),
    onDrop: function (e, source) {
        $(this).removeClass('over');
        var comp_type = $(source).attr('id');
        var newElemId = 'id' + index++;// 自增长生成id
        var parentTag = addElem(this, comp_type, newElemId);
        addListenerInDropBox(parentTag);
    },
    onDragEnter: function (e, source) {
        if ($(source).hasClass("comp_layout")) { // 防止拖动区域内部组件，拖动时触发下面函数
            return;
        }
        $(this).addClass('over');
    },
    onDragLeave: function (e, source) {
        $(this).removeClass('over');
    }
});

/**
 * 获取可拖动元素的字符串
 * @returns {string}
 */
function getDragElemStr() {
    var str = "";
    for (var i = 0; i < dragElems.length; i++) {
        str += (i == 0 ? "#" : ",#") + dragElems [i];
    }
    return str;
}

/**
 * 添加元素
 * @param targetElem
 * @param comp_type
 * @param newElemId
 */
function addElem(targetElem, comp_type, newElemId) {
    var $parentTag = $("<div class='topjui-col-sm6 comp_layout' style='position: unset' id='" + newElemId + "' data-type='" + comp_type + "'></div>");// 父标签
    var tmp_elem = $('#tpl_' + comp_type).html(); // 获取模板
    $(tmp_elem).appendTo($parentTag); // 模板和父标签结合

    $parentTag.appendTo(targetElem);// 结合后添加到页面
    newElems.push($parentTag);// 添加到数组保存

    // 1.发送请求给后台
    var url = SCOPE.compPropertyTplUrl;
    // var url = "/public/js/textbox.json";
    var postData = {
        type: comp_type
    };
    $.ajax({
        type: "get",
        data: postData,
        async: false,
        url: url,
        dataType: 'json',
        success: function (res) {
            $.each(res['rows'], function (i) {
                if (res['rows'][i]['key'] == 'comp_sort') {
                    res['rows'][i]['value'] = parseInt(newElemId.substring(2));
                }
                if (res['rows'][i]['key'] == 'field_name') { // 更换组件name
                    var filedName = res['rows'][i]['value'];
                    $parentTag.find('input').attr("name", filedName);
                    $parentTag.find('input').attr("id", "cmp_"+newElemId);
                }
                res['rows'][i]['value'] = HtmlUtil.htmlDecode(res['rows'][i]['value']).replace(/\"/g, "'")
            });
            res['html'] = getJqDomHtmlStr($parentTag);
            res['id'] = newElemId;
            res["compFormId"] = formId;
            properties.push(res);

            // 再次将数据发送给后台保存
            $.post(SCOPE.compAddUrl, {jsonstr: JSON.stringify(res)}, function (response) {
                // console.log(response);
                // 刷新组件列表表格
                $("#" + compDgId).iDatagrid("reload");
            });

            $.parser.parse($parentTag);// 重新实例化组件
        },
        error: function () {
            alert("加载失败！请稍后重试！");
        }
    });
    return $parentTag;
}

// 组件拖动 结束======================================

/**
 *  表单预览界面
 */
function preview() {
    var url = SCOPE.compPreview + formId;
    var iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>'
    var $myDialog = $("<div style='overflow: hidden'></div>")
    var opt = {
        id: 'myDialog',// 唯一标识id
        title: '表单预览',// 标题
        closed: false, // 关闭状态
        iconCls: "fa fa-eye",
        // href: SCOPE.compPreview + formId,
        content:iframe,
        buttons: [
            {text: '关闭', iconCls: 'fa fa-times', btnCls: 'topjui-btn-red', handler: closeMyDialog}//'自定义关闭'调用下面closeMyDialog方法
        ]
    };
    $myDialog.iDialog(opt);
}
function previewHtml() {
    var url = SCOPE.compPreviewHtml + formId;
    var iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>'
    var $myDialog = $("<div style='overflow: hidden'></div>")

    var opt = {
        id: 'myDialog2',// 唯一标识id
        title: '代码预览',// 标题
        closed: false, // 关闭状态
        iconCls: "fa fa-html5",
        content:iframe,
        buttons: [
            {text: '关闭', iconCls: 'fa fa-times', btnCls: 'topjui-btn-red', handler: closeMyDialog2}//'自定义关闭'调用下面closeMyDialog方法
        ]
    };
    $myDialog.iDialog(opt);
}
/**
 * 关闭弹框
 */
function closeMyDialog(e) {
    $('#myDialog').iDialog('destroy');// 销毁dialog面板
}
function closeMyDialog2(e) {
    $('#myDialog2').iDialog('destroy');// 销毁dialog面板
}

// ==============================================组件初始化
$(function () {
    addDraggable(); // 给元素添加可拖动的事件
    innit(); //  初始化元素

});

/**
 * 给元素添加拖动属性
 */
function addDraggable() {
    for (var i = 0; i < dragElems.length; i++) {
        $("#" + dragElems[i]).iDraggable({
            proxy: 'clone',
            revert: true,
            onStartDrag: function () {
                $(this).draggable('proxy').addClass('dp');
            }
        });
    }
}

/**
 * 创建时候初始话元素
 */
function innit() {
    $.post(SCOPE.compLoadUrl, {compFormId: formId}, function (res) {
        if (res && res.statusCode != -1 && res.statusCode != 300) {
            properties = JSON.parse(res);
            resetProperties();
            for (var i = 0; i < properties.length; i++) {
                var newElemId = properties[i]["id"];// id;
                index = index > parseInt(newElemId.substring(2)) ? index : parseInt(newElemId.substring(2)) + 1;

                var property = getPropertyById(newElemId)
                var comp_type = property['type'];
                var htmlStr = HtmlUtil.htmlDecode(property['html']);

                // 转义下表格属性
                $.each(properties[i]['rows'], function (j) {
                    properties[i]['rows'][j]['value'] = HtmlUtil.htmlDecode(properties[i]['rows'][j]['value']).replace(/\"/g, "'");
                });

                var $comp = $(htmlStr);
                $comp.appendTo($('#' + dropPanelId));// 结合后添加到页面
                newElems.push($comp);// 添加到数组保存
                addListenerInDropBox($comp);

                $.parser.parse($comp);// 重新实例化组件
            }
        }
    })

}


var HtmlUtil = {
    /*1.用浏览器内部转换器实现html转码*/
    htmlEncode:function (html){
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement ("div");
        //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
        (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
        var output = temp.innerHTML;
        temp = null;
        return output;
    },
    /*2.用浏览器内部转换器实现html解码*/
    htmlDecode:function (text){
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement("div");
        //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
        temp.innerHTML = text;
        //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
        var output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    }
};

/**
 * 通过id获取属性数组中的元素
 * @param id
 * @returns {*}
 */
function getPropertyById(id) {
    var property = properties.filter(function (item, index) {
        return item.id == id ? true : false;
    });
    return property[0];
}

/**
 * 通过id删除属性数组中的元素
 * @param id
 */
function deletePropertyById(id) {
    for (var i = 0; i < properties.length; i++) {
        if (id == properties[i]["id"]) {
            properties.splice(i, 1);
            break;
        }
    }
}

/**
 * 通过id获取属性属性数组的下标
 * @param id
 * @returns {number}
 */
function getPropertyIndexById(id) {
    for (var i = 0; i < properties.length; i++) {
        if (id == properties[i]["id"]) {
            return i;
        }
    }
}

/**
 * 属性数组重新排序
 */
function resetProperties() {
    var index = 1, elemId, elemSort, elemId2, elemSort2;
    var newArr = [];
    // 冒泡排序
    for (var i = 0; i < properties.length - 1; i++) {
        for (var j = 0; j < properties.length - i - 1; j++) {
            elemId = properties[j]["id"];
            elemSort = getSort(elemId);
            elemId2 = properties[j + 1]["id"];
            elemSort2 = getSort(elemId2);
            if (parseInt(elemSort) > parseInt(elemSort2)) {
                var rp = properties[j];
                properties[j] = properties[j + 1];
                properties[j + 1] = rp;
            }
        }
    }
    // for (var i = 0 ; i < properties.length ; i ++){
    //     elemId = properties[i]["id"];
    //     elemSort = getSort(elemId);
    //     var left = i;
    //     for (var j = i + 1 ; j < properties.length; j++){
    //         elemId2 = properties[j]["id"];
    //         elemSort2 = getSort(elemId2);
    //         if(parseInt(elemSort) > parseInt(elemSort2)){
    //             var rp = properties[left];
    //             properties[left] = properties[j];
    //             properties[j] = rp;
    //             left = j;
    //         }
    //     }
    // }
}

/**
 * 给放置的盒子添加事件监听
 * @param $target
 */
function addListenerInDropBox($target) {
    var proxy = "<div class='topjui-col-sm6 proxy' ><i class='fa fa-exchange'></i></div>";
    var $proxy = null;
    var $proxy_left = false; // 如果占位元素存在，并且在左侧，就不需要再次插入（true 代表左 false代表右侧
    $target.iDraggable({
        proxy: 'clone',
        revert: true,
        onStartDrag: function (e) {
            $(this).iDraggable('proxy').addClass('comp_hover');
            $(this).addClass('comp-over');
        },
        onStopDrag: function (e) {
            $(this).removeClass('comp-over')
        }
    });
    $target.iDroppable({
        onDragEnter: function (e, source) {
            if ($(source).hasClass('comp_layout')) {
                $proxy = $(proxy).insertAfter(this);
            }
        },
        onDragLeave: function (e, source) {
            if ($(source).hasClass('comp_layout')) {
                $proxy ? $proxy.remove() : null;
            }
        },
        onDragOver: function (e, source) {
            if ($(source).hasClass('comp_layout')) {

                $proxy ? $proxy.remove() : null;

                $proxy = $(proxy).insertAfter(this);
            }
        },
        onDrop: function (e, source) {
            if ($(source).hasClass('comp_layout')) {
                $proxy ? $proxy.remove() : null;
                $(source).insertAfter(this);
                changeSort($(this), $(source))
            }
        }
    });
}

/**
 * 改变排序
 * @param $drop
 * @param $drag
 */
function changeSort($drop, $drag) {
    var drop_id = $drop.attr('id'); // 放置区域id
    var drag_id = $drag.attr('id'); // 拖动元素id
    var index = 1; //  初始化sort

    // 1.获取现有的排序
    var sortArr = [];

    resetProperties();
    for (var i = 0; i < properties.length; i++) {
        var comp_id = properties[i]['id'];
        if (comp_id != drag_id) { // 如果元素为拖动元素，不设置sort
            setSort(comp_id, index++);
            if (comp_id == drop_id) { //  如果元素为放置区域，拖动元素sort比它大1 ；
                setSort(drag_id, index++);
            }
        }
    }
    resetProperties();
    // 发送信息给后台
    var postData = {
        rows: [],
        compFormId: undefined
    };
    for (var i = 0; i < properties.length; i++) {
        var comp_id = properties[i]['id'];
        postData.rows.push({"comp_id": comp_id, "sort": getSort(comp_id)})
    }


    postData.compFormId = formId;
    // console.log(JSON.stringify(postData));
    $.post(SCOPE.compChangeSortUrl, {jsonStr: JSON.stringify(postData)}, function (res) {
        $("#" + compDgId).iDatagrid("reload");// 刷新组件列表表格
        // console.log(res);
    }, "JSON")

}

/**
 * 根据组件id查询排序值
 */
function getSort(elemId) {
    for (var i = 0; i < properties.length; i++) {
        if (elemId == properties[i]['id']) {
            for (var j in  properties[i]['rows']) {
                if (properties[i]['rows'][j]['key'] == 'comp_sort')
                    return properties[i]['rows'][j]["value"]
            }
        }
    }
    return null;
}

/**
 * 根据组件id设置排序值
 * @param elemId
 * @param sortNum
 * @returns {null}
 */
function setSort(elemId, sortNum) {
    for (var i = 0; i < properties.length; i++) {
        if (elemId == properties[i]['id']) {
            for (var j in  properties[i]['rows']) {
                if (properties[i]['rows'][j]['key'] == 'comp_sort')
                    properties[i]['rows'][j]["value"] = sortNum;
            }
        }
    }
    return null;
}

//组件初始化 结束=============================================


// =======================  拖动区域
/**
 * 右键菜单
 */
$("#" + dropPanelId).on('contextmenu', function (e) {
    e.preventDefault();
    var $resource = findParentHasClass(e, "comp_layout"); //找父节点
    if (!$resource) {
        return;
    }
    menuResourceId = $resource.attr('id');
    $('#mm').iMenu('show', {
        left: e.pageX,
        top: e.pageY
    });
});

/**
 * 菜单按钮事件
 * @param item
 */
function handlerMenuClick(item) {
    switch (item.text) {
        case '编辑':
            menuEdit();
            break;
        case '删除':
            menuDelete();
            break;
    }
}

/**
 * 加载数据表格数据
 * @param $resource
 * @returns {*|jQuery}
 */
function loadPgGridData($resource) {
    if (!$resource) {
        return $('#' + propertygridId).iPropertygrid('loadData', []);
    }
    var $targetElem = $resource;
    var postData = $targetElem.data();
    pgSelectedElemId = $targetElem.attr('id');

    postData['id'] = pgSelectedElemId;

    if (!jQuery.isEmptyObject(getPropertyById(pgSelectedElemId))) {
        var data = getPropertyById(pgSelectedElemId);
        $('#' + propertygridId).iPropertygrid('loadData', data);
        // 判断有无component_id 有的话直接不插入
        for(var i = 0; i < data.rows.length; i++){
            if(data.rows[i]["key"] == "component_id" ){
                return;
            }
        }
        $('#' + propertygridId).iPropertygrid('insertRow',{
            index: 0,   // 索引从0开始
            row: {
                name: '组件ID',
                key:'component_id',
                value: "cmp_" + pgSelectedElemId,
                group:"组件属性"
            }
        });
        $("#" + propertygridId).datagrid('acceptChanges');

        return;

        // var properties = getPropertyById(pgSelectedElemId);
        // var data = [];
        // var index = 0 ;
        // $.each(properties.rows,function (i) {
        //     if(index++ == 1){
        //         data.push({name:"组件ID",key:"id",value:pgSelectedElemId,group:"组件属性"});// 放在组件属性最前面
        //     }
        //     data.push(properties.rows[i]);
        // });
        // $('#' + propertygridId).iPropertygrid('loadData', data);
        // return;
    }
}

/**
 * 通过class找到某个元素的指定父元素
 * @param e
 * @param cls
 * @returns {*|jQuery|HTMLElement}
 */
function findParentHasClass(e, cls) {
    var $resource = $(e.target || e.srcElement);
    if ($resource.attr('id') == dropPanelId) {
        return;
    }
    var count = 0;// 防止进入死循环
    while (!$resource.hasClass(cls) && count < 10) {
        $resource = $resource.parent();
        count++;
    }
    return $resource
}

/**
 * 右键编辑
 */
function menuEdit() {
    //  1.判断有无更改的属性
    var rows = $("#" + propertygridId).datagrid('getChanges');
    if (rows.length > 1) {
        $.iMessager.confirm('操作提示', '您还未保存，确认放弃修改？', function (r) {
            if (r) {
                $("#" + propertygridId).datagrid('rejectChanges');
                // 1.1修改表边框颜色
                $(".comp-active").removeClass('comp-active');
                $("#" + menuResourceId).addClass('comp-active');

                // 1.2加载属性表格
                loadPgGridData($("#" + menuResourceId));
            } else {
                return;
            }
        })
    } else {
        // 2.1.修改表边框颜色
        $(".comp-active").removeClass('comp-active');
        $("#" + menuResourceId).addClass('comp-active');

        // 2.2.加载属性表格
        loadPgGridData($("#" + menuResourceId));
    }
}

/**
 * 删除
 */
function menuDelete() {
    $.iMessager.confirm('确认对话框', '您确认删除该组件？', function (r) {
        if (r) {
            // 1.获取组件对象
            var $comp = $("#" + menuResourceId);
            var comp_id = $comp.attr('id');

            // 2.后台删除
            var postData = {compId: comp_id, compFormId: formId}
            $.post(SCOPE.deleteCompUrl, postData, function (res) {
                if (res.statusCode == 200) {
                    // 2.1.从拖放区域删除
                    var elem = undefined;
                    $.each(newElems, function (i) {
                        if (newElems[i].attr('id') == comp_id) {
                            elem = newElems[i];
                            return;
                        }
                    });
                    elem.remove(); // 移除元素
                    deletePropertyById(comp_id); // 删除属性数据
                    // 2.2.刷新数据表格
                    $("#" + compDgId).iDatagrid("reload");

                    // 2.3.刷新属性表格
                    if (pgSelectedElemId == comp_id) {
                        loadPgGridData();
                        pgSelectedElemId == undefined;
                    }
                    if (menuResourceId == comp_id) {
                        menuResourceId == undefined;
                    }
                } else {
                    $.iMessager.alert('操作提示', '删除失败！', 'messager-error');
                }
            });
        }
    });
}

//   拖动区域 结束=======================


// =============================属性表格区域
/**
 * 保存修改数据
 * @returns {*}
 */
function saveRow() {
    // 1. 获取修改;
    var changeRows = $("#" + propertygridId).datagrid('getChanges');
    if (!pgSelectedElemId) {
        return $.iMessager.alert('操作提示', '请选择操作元素！', 'messager-info');
    }
    if (changeRows.length == 0) {
        return $.iMessager.alert('操作提示', '属性没有变动！', 'messager-info');
    }
    // // 2.去除换行
    // for(var i = 0 ; i < changeRows.length;i++){
    //     if(changeRows[i]['key'] == "component_other"){
    //         changeRows[i]['value'] == HtmlUtil.htmlEncode(changeRows[i]['value']).replace(/[\r\n]/g,"").replace(/\s+/g,"");// 去掉换行
    //     }
    // }

    // 3.获取属性值并改变对应的元素
    var totalAndRows = $("#" + propertygridId).datagrid("getData");
    var totalAndRowsClone = JSON.stringify(totalAndRows);
    totalAndRows = JSON.parse(totalAndRowsClone);
    // 4.去除字段component_id
    for(var  i = 0; i < totalAndRows.rows.length; i++){
        if(totalAndRows.rows[i]['key'] && totalAndRows.rows[i]['key'] == "component_id"){
            totalAndRows.rows.splice(i,1); continue;
        }
        // 去除换行
        if(totalAndRows.rows[i]['key'] == "component_other"){
            // totalAndRows.rows[i]['value'] = HtmlUtil.htmlEncode(totalAndRows.rows[i]['value']).replace(/[\r\n]/g,"").replace(/\s+/g,"");// 去掉换行 去空格
            totalAndRows.rows[i]['value'] = HtmlUtil.htmlEncode(totalAndRows.rows[i]['value']).replace(/[\r\n]/g,"");// 去掉换行
        }
    }


    // 5.重新加载组件
    var attrs = getAttrsFromPgData(totalAndRows.rows, 'comp_');
    var htmlStr = reloadCompent(attrs, pgSelectedElemId);
    if(!htmlStr) {
        return ;
    }
    // 6属性表格接受修改
    $("#" + propertygridId).datagrid('acceptChanges');

    // 7.修改数组对象中的值
    totalAndRows['id'] = pgSelectedElemId;
    properties[getPropertyIndexById(pgSelectedElemId)] = totalAndRows;
    properties[getPropertyIndexById(pgSelectedElemId)]['html'] = htmlStr;

    // 8.保存后台  将改变的数据发送给后台和html代码发送到后台
    var postData = getPropertyById(pgSelectedElemId);
    // postData.compFormId = formId;
    $.post(SCOPE.compUpdateUrl, {jsonstr: JSON.stringify(postData)}, function (res) {
        if (res.statusCode == 200) {
            // console.log("修改成功") // TODO
            // 修改成功提示
            $.iMessager.show({
                title:'我的消息',
                msg:'修改成功!',
                timeout:5000,
                showType:'slide'
            });
            // 刷新组件列表表格
            $("#" + compDgId).iDatagrid("reload");
            //刷新属性表格
            loadPgGridData($("#" + menuResourceId));
        } else {
            $.iMessager.alert('操作提示','操作失败，请刷新页面重试！')
        }
    }, "JSON");
    // console.log(changeRows, htmlStr);
}

/**
 * 保存后 重新渲染组件
 * @param comp_attrs
 * @param comp_id
 * @returns {*}
 */
function reloadCompent(comp_attrs, comp_id) {

    var elem = undefined;
    // 1.newElems
    $.each(newElems, function (i) {
        if (newElems[i].attr('id') == comp_id) {
            elem = newElems[i];
            return;
        }
    });
    var tpl_type = elem.data('type');
    var comp_tmpl = $('#tpl_' + tpl_type).get(0).content;
    $comp_tmpl = $(comp_tmpl).clone();

    // 1.1 修改主键的lable
    $comp_tmpl.find('label').html(comp_attrs['name']);
    delete  comp_attrs['name'];

    // 1.2 拿到布局
    var layout = comp_attrs['layout'];
    delete  comp_attrs['layout'];

    //1.3 修改组件name
    var filedName = getValueFromProperties("field_name");
    $comp_tmpl.find('input').attr("name", filedName);
    $comp_tmpl.find('input').attr("id", "cmp_" + comp_id);

    // 1.4.修改data-options
    var opt = '';
    for (var index in comp_attrs) {
        if (Boolean(comp_attrs[index]) && comp_attrs[index] != "''" && comp_attrs[index] != "'null'") { // 如果存在值 再添加
            opt += index + ":" + comp_attrs[index] + ','
        }
        if (index == "groupSeparator" || index == "separator") {
            opt += index + ":" + comp_attrs[index] + ','
        }
    }
    // 添加data-options
    var otherOptions = getValueFromProperties("component_other");
    opt = otherOptions ? opt + otherOptions : opt;
    $comp_tmpl.find('input').attr('data-options', opt);

    try {
        // 先验证下组件是否能正常渲染
        var $comp_tmpl_clone = $comp_tmpl.clone();
        var $box = $("<div></div>")
        $box.html($comp_tmpl_clone.get(0));
        $.parser.parse( $box);
        // 清空数据
        $comp_tmpl_clone = null;
        $box = null;
    }catch (e) {
        $.iMessager.alert('操作提示','组件创建失败，请检查属性！')
        return ;
    }

    // 修改布局
    elem.removeClass("topjui-col-sm6 topjui-col-sm12 topjui-col-sm4");
    elem.addClass(layout);

    //添加进组件中
    elem.html($comp_tmpl.get(0));

    // var htmlStr = elem.html();
    var htmlStr = getJqDomHtmlStr(elem);//
    $.parser.parse(elem);
    addListenerInDropBox(elem);

    return htmlStr;
}

/**
 * 克隆元素后拿到html字符串
 * @param jqDom
 * @returns {*}
 */
function getJqDomHtmlStr(jqDom) {
    var $box = $("<div></div>");
    var jqDomClone = jqDom.clone();
    jqDomClone.removeClass("droppable comp-active");
    $box.html(jqDomClone);
    return $box.html();
}

/**
 * 通过key获取value
 * @param name
 * @returns {*}
 */
function getValueFromProperties(name) {
    var rows = getPropertyById(pgSelectedElemId).rows;
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        if (row['key'] == name) {
            return row["value"];
        }
    }
}

/**
 * 撤回修改
 */
function rejectChanges() {
    // 1. 获取修改;
    var rows = $("#" + propertygridId).datagrid('getChanges');
    // console.log(rows);
    if (rows.length > 0) {
        $.iMessager.confirm('操作提示', '您确认撤回修改记录？', function (r) {
            if (r) {
                $("#" + propertygridId).datagrid('rejectChanges');
                editIndex = undefined;
            }
        });
    } else {
        $.iMessager.alert('操作提示', '您没有修改记录！', 'messager-info');
    }
}

/**
 * 获取属性 通过前缀
 * @param atrr
 * @param pre
 */
function getAttrsFromPgData(atrr, pre) {
    var dataArr = {};
    for (var i = 0; i < atrr.length; i++) {
        var row = atrr[i];
        if (row['key'] && row['key'].indexOf(pre) != -1) {
            if (JSON.parse(row.quotation)) {
                dataArr[row.key.split('_')[1]] = "'" + row.value + "'";
            } else {
                dataArr[row.key.split('_')[1]] = row.value;
            }
        }
    }
    return dataArr;
}


var mycolumns = [[
    {field: 'name', title: '属性名', width: 50},
    {
        field: 'value', title: '属性值', width: 100, formatter: function (value, row, index) {
            // 1.格式化——列表显示
            var text = '';
            if (row.key == 'field_status') {
                switch (parseInt(value)) {
                    case 1 :
                        text = "显示";
                        break;
                    case 2 :
                        text = "隐藏";
                        break;
                    case 3 :
                        text = "不出现";
                        break;
                }
                return text;
            } else if (row.key == 'field_primary' || row.key == 'comp_required' || row.key == "comp_readonly") {
                return JSON.parse(value) ? "是" : "否";
            } else if (row.key == 'field_isnull') {
                return value == "NULL" ? "是" : "否";
            } else if (row.key == 'comp_layout') {
                switch (value) {
                    case "topjui-col-sm12" :
                        text = "独占一行";
                        break;
                    case "topjui-col-sm6" :
                        text = "两列布局";
                        break;
                    case "topjui-col-sm4" :
                        text = "三列布局";
                        break;
                }
                return text;
            } else if (row.key == 'field_type') {
                switch (value) {
                    case "date":
                        text = "时间日期类型";
                        break;
                    case "float":
                        text = "数字类型";
                        break;
                    case "datetime":
                        text = "日期类型";
                        break;
                    case "varchar":
                        text = "字符串类型";
                        break;
                }
                return text;
            }
            else if (row.key == 'comp_validType') {
                if (value == "empty") {
                    return "无需验证";
                }
            }
            return value;
        }
    }
]];

//=======================组件数据表格
var columns = [
    {field: 'fieldDesc', title: '字段描述'},
    {field: 'fieldLength', title: '字段长度'},
    {field: 'fieldType', title: '字段类型'},
    {field: 'componentType', title: '组件类型'},
    {field: 'componentSort', title: '组件排序'}
];

$("#" + compDgId).iDatagrid({
    fit: true,
    fitColumns: true,
    pagination: false,
    url: SCOPE.compListGetUrl + "?TableUuid=" + formId,
    columns: [columns],
    loadFilter: compDgLoadFilter
});

function compDgLoadFilter(data) {
    var newData = {
        total: data.length,
        rows: data
    };
    return newData;
}


//组件数据表格=======================


/**
 * 获取连接地址url中的参数
 * @returns {Object}
 * @constructor
 */
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}