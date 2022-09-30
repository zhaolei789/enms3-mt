<%@ page language="java" pageEncoding="UTF-8" %>
<style>
    .topjui-col-sm4, .topjui-col-sm6, .topjui-col-sm12 {
        margin-bottom: 10px;
    }
</style>
<input type="hidden" id="uuid" name="uuid">
<div class="topjui-fluid">
    <div class="topjui-row">
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">姓名</label>
            <div class="topjui-input-block">
                <input type="text" name="userName"
                       data-toggle="topjui-textbox"
                       data-options="prompt:'姓名'">
            </div>
        </div>
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">性别</label>
            <div class="topjui-input-block">
                <input type="text" name="sex"
                       data-toggle="topjui-textbox"
                       data-options="prompt:'性别'">
            </div>
        </div>
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">年龄</label>
            <div class="topjui-input-block">
                <input type="text" name="age"
                       data-toggle="topjui-textbox"
                       data-options="prompt:'年龄'">
            </div>
        </div>
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">地址</label>
            <div class="topjui-input-block">
                <input type="text" name="address"
                       data-toggle="topjui-textbox"
                       data-options="prompt:'地址'">
            </div>
        </div>
        <div class="topjui-col-sm6">
            <label class="topjui-form-label">手机号</label>
            <div class="topjui-input-block">
                <input type="text" name="phone"
                       data-toggle="topjui-textbox"
                       data-options="prompt:'手机号'">
            </div>
        </div>
    </div>
</div>