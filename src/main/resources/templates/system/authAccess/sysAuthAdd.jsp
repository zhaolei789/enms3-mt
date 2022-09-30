<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<input type="hidden" name="roleId">
<table class="editTable">
    <tr>
        <td class="label">选择系统</td>
        <td>
            <input type="text" name="sysName"
                   data-toggle="topjui-combobox"
                   data-options="required:true,
                   textField:'text',
                   valueField:'id',
                   width:450,
                   multiple:true,
                   url:'${ctx}/system/menu/getListByCodeSetIdAndLevelId?codeSetId=menu&levelId=2'">
        </td>
    </tr>
</table>