<%@ page language="java" pageEncoding="UTF-8" %>
<input type="hidden" name="uuid">
<input type="hidden" name="puuid">
<div class="topjui-fluid">
    <fieldset>
        <legend>字典集信息</legend>
    </fieldset>
    <div class="topjui-row">
        <div class="topjui-col-sm12">
            <label class="topjui-form-label">字典项名称</label>
            <div class="topjui-input-block">
                <input type="text" name="text" data-toggle="topjui-textbox"
                       data-options="required:true,prompt:'必填'">
            </div>
        </div>
    </div>
    <div class="topjui-row">
        <div class="topjui-col-sm12">
            <label class="topjui-form-label">字典项值</label>
            <div class="topjui-input-block">
                <input type="text" name="value" data-toggle="topjui-textbox"
                       data-options="required:true,prompt:'必填'">
            </div>
        </div>
    </div>
    <div class="topjui-row">
        <div class="topjui-col-sm12">
            <label class="topjui-form-label">排序</label>
            <div class="topjui-input-block">
                <input type="text" name="sort" data-toggle="topjui-textbox"
                       data-options="required:true,prompt:'必填'">
            </div>
        </div>
    </div>
</div>