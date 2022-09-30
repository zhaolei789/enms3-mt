<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<style>
    .topjui-col-sm4, .topjui-col-sm6, .topjui-col-sm12 {
        margin-bottom: 10px;
    }
</style>
<input type="hidden" id="uuid" name="uuid" value="${param.uuid}">
<c:if test="${sysFdTable.tableType == 'treegrid'}">
    <input type="hidden"  name="levelId" value="1">
    <input type="hidden"  name="pid" value="1">
</c:if>

<input type="hidden"  name="id">
<input type="hidden" name="tableUuid" value="${tableUuid}">
<div class="topjui-fluid">

    <div class="topjui-row">
        <c:forEach items="${sysFdFields}" var="sysFdField">
            <c:choose>
                <c:when test="${sysFdField.componentIsShow=='2'}">
                    <input type="hidden" name="${sysFdField.fieldName}">
                </c:when>
                <c:otherwise>
                    <div class="${sysFdField.componentLayout}">
                        <label class="topjui-form-label">${sysFdField.fieldDesc}</label>
                        <div class="topjui-input-block">
                            <c:choose>
                                <c:when test="${sysFdField.componentType == 'ueditor'}">
                                    <textarea name="${sysFdField.fieldName}" style="height:300px;" data-toggle="topjui-${sysFdField.componentType}"
                                              data-options="id:'${sysFdField.fieldName}EditContainer'"></textarea>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${sysFdField.componentType == 'combotree'}">
                                        <input type="hidden" name="${sysFdField.fieldName}Text">
                                    </c:if>
                                    <c:if test="${sysFdField.componentType == 'personnel'}">
                                        <input type="hidden" name="${sysFdField.fieldName}Text">
                                    </c:if>
                                    <c:if test="${sysFdField.componentType == 'org'}">
                                        <input type="hidden" name="${sysFdField.fieldName}Text">
                                    </c:if>
                                    <input type="text" name="${sysFdField.fieldName}"
                                           data-toggle="<c:choose><c:when test="${sysFdField.componentType == 'org'}">topjui-combotree</c:when><c:when test="${sysFdField.componentType == 'personnel'}">topjui-combogrid</c:when><c:otherwise>topjui-${sysFdField.componentType}</c:otherwise></c:choose>"
                                           data-options="required:${sysFdField.componentRequired}
                                    ,validType:'${sysFdField.componentValidate}'
                                   <c:if test="${sysFdField.componentReadonly == true}">
                                   ,readonly:'${sysFdField.componentReadonly}'
                                   </c:if>
                                   ,prompt:'${sysFdField.componentPrompt}'
                                   <c:if test="${sysFdField.componentType == 'combobox' && sysFdField.dataDinding != ''}">
                                   ,url:'/system/dicSet/getDicItemByCode?code=${sysFdField.dataDinding}'
                                   </c:if>
                                    <c:if test="${sysFdField.componentType == 'combotree' && sysFdField.dataDinding != ''}">
                                   ,url:'/system/codeItem/getListByCodeSetId?codeSetId=${sysFdField.dataDinding}'
                                   ,expandUrl:'/system/codeItem/getListByPid?pid={id}'
                                   </c:if>
                                   <c:if test="${sysFdField.componentType == 'ueditor'}">
                                     ,id:'${sysFdField.fieldName}EditContainer'
                                   </c:if>
                                   <c:if test="${sysFdField.componentType == 'org' }">
                                   ,url:'${ctx}/ucenter/organization/getListByLevelId?levelId=1'
                                   ,expandUrl:'${ctx}/ucenter/organization/getListByPid?pid={id}'
                                   ,getFatherIdsUrl:'${ctx}/ucenter/organization/getFatherIds?id={id}'
                                   </c:if>
                                   <c:if test="${sysFdField.componentType == 'personnel'}">
                                           ,idField:'userNameId'
                                           ,textField:'userName'
                                           ,editable:true
                                           ,url:'${ctx}/ucenter/user/getListByKeywords'
                                           ,columns:[[
                                               {field:'userName',title:'姓名',width:100},
                                               {field:'userNameId',title:'员工号',width:100}
                                           ]]
                                   </c:if>
                                    <c:if test="${sysFdField.componentType == 'combotree'}">
                                        ,expandAll:true
                                        ,param:'${sysFdField.fieldName}Text:text'
                                    </c:if>
                                    <c:if test="${sysFdField.componentType == 'personnel'}">
                                        ,param:'${sysFdField.fieldName}Text:userName'
                                    </c:if>
                                    <c:if test="${sysFdField.componentType == 'org'}">
                                         ,param:'${sysFdField.fieldName}Text:text'
                                    </c:if>
                                 ">
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>

    </div>

</div>
