<%@ page language="java" pageEncoding="UTF-8" %>

<input type="hidden" name="uuid">
<input type="hidden" name="categoryId">
<table class="editTable">
    <tr>
        <td class="label">资源名称</td>
        <td><input type="text" name="title" data-toggle="topjui-textbox" data-options="width:700"></td>
    </tr>
    <tr>
        <td class="label">资源描述</td>
        <td>
            <textarea name="description" data-toggle="topjui-ueditor" data-options="id:'descriptionEditEditor'"></textarea>
        </td>
    </tr>
</table>