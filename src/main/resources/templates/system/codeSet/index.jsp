<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common/head.jsp" %>
<script type="text/javascript" src="${ctx}/app/${systemName}/Public/Js/${controllerName}.js"></script>

<div data-toggle="topjui-layout" data-options="fit:true">
	<div data-options="region:'west',title:'代码集',split:true,border:false" style="width:45%;">
		<table id="datagrid"></table>
	</div>
	<div data-options="region:'center',iconCls:'icon-reload',title:'代码项',split:true,border:false" style="overflow:hidden">
		<table id="datagrid2"></table>
	</div>
</div>

<form id="editDialog"></form>