<%@ page language="java" pageEncoding="UTF-8" %>
<input type="hidden" id="uuid" name="uuid">
<div class="topjui-fluid">
    <fieldset>
        <legend>基本信息</legend>
    </fieldset>
    <div class="topjui-row">
        <div class="topjui-col-sm12"><label class="topjui-form-label">标题</label>
            <div class="topjui-input-block">
                <input type="text" name="title" data-toggle="topjui-textbox"
                       data-options="prompt:'标题'">
            </div>
        </div>
    </div>
    <div class="topjui-row">
        <div class="topjui-col-sm12"><label class="topjui-form-label">备注</label>
            <div class="topjui-input-block">
                <input type="text" name="remark" data-toggle="topjui-textarea"
                       data-options="prompt:'备注'">
            </div>
        </div>
    </div>
</div>