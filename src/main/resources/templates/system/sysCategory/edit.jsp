<%@ page language="java" pageEncoding="UTF-8" %>
<input type="hidden" id="uuid" name="uuid">
<input type="hidden" name="id">
<input type="hidden" name="type" value="${param.type}">
<input type="hidden" name="levelId" value="1">
<div class="topjui-fluid">
    <div class="topjui-row">
        <div class="topjui-col-sm12"><label class="topjui-form-label">父级资源</label>
            <div class="topjui-input-block">
                <input type="text" name="pid" data-toggle="topjui-combotreegrid"
                       data-options="required:true,
                       valueField:'id',
                       fitColumns:true,
                       columns:[[
                           {field:'text',title:'名称',width:100}
                       ]],
                       url:'${ctx}/system/sysCategory/getCategorysByTypeAndLevelId?type=${param.type}&levelId=0',
                       expandUrl:'${ctx}/system/sysCategory/getListByPid?pid={id}&type=${param.type}',
                       getFatherIdsUrl:'${ctx}/system/sysCategory/getFatherIds?id={id}&type=${param.type}'">
            </div>
        </div>
    </div>
    <div class="topjui-row">
        <div class="topjui-col-sm6"><label class="topjui-form-label">名称</label>
            <div class="topjui-input-block">
                <input type="text" name="text" data-toggle="topjui-textbox"
                       data-options="prompt:'名称',required:true,prompt:'必填'">
            </div>
        </div>
        <div class="topjui-col-sm6"><label class="topjui-form-label">排序</label>
            <div class="topjui-input-block">
                <input type="text" name="sort" data-toggle="topjui-textbox"
                       data-options="prompt:'排序'">
            </div>
        </div>
    </div>

    <div class="topjui-row">
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">是否首页显示</label>
            <div class="topjui-input-block">
                <input type="text" name="portalDisplay" value="" data-toggle="topjui-combobox"
                       data-options="
                        panelHeight:63,
                           url:'/system/dicSet/getDicItemByCode?code=yesOrNo',
                           param:'portalDisplay:text'">
            </div>
        </div>
        <div class="topjui-col-sm6"><label class="topjui-form-label">有无子节点</label>
            <div class="topjui-input-block">
                <input type="text" name="state" value="" data-toggle="topjui-combobox"
                       data-options="
                           panelHeight:63,
                           url:'/system/dicSet/getDicItemByCode?code=nodeState',
                           param:'state:text'">
            </div>
        </div>
    </div>
    <div class="topjui-row">
        <div class="topjui-col-sm12">
            <label class="topjui-form-label">资源图标</label>
            <div class="topjui-input-block">
                <input type="text" name="iconCls" data-toggle="topjui-textbox" data-options="">
            </div>
        </div>
    </div>
</div>