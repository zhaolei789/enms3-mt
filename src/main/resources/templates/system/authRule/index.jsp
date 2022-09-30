<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common/head.jsp" %>
<script type="text/javascript" src="${ctx}/app/${systemName}/Public/Js/${controllerName}.js"></script>

<div data-toggle="topjui-layout" data-options="fit:true">
	<div data-options="region:'west',title:'功能树',split:false,border:false,width:'20%',cls:'rightBorder',iconCls:'fa fa-sitemap'">
		<ul id="tree"></ul>
	</div>
	<div data-options="region:'center',iconCls:'icon-reload',title:'',split:true">
			<table id="datagrid"></table>
	</div>
</div>

<form id="addDialog"></form>

<form id="editDialog"></form>