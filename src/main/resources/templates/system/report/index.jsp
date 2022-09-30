<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../common/common/head.jsp" %>
<%@ include file="../../common/common/head-bootstrap.jsp" %>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    				<div id="barChartData" data-toggle="topjui-echarts" data-options="{'type':'bar','url':'/party/report/barChartData'}" style="width:500px;height:334px;border:0"></div>
				</div>
			</div>
		</div>
		
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    				<div id="lineChartData" data-toggle="topjui-echarts" data-options="{'type':'line','url':'/party/report/lineChartData'}" style="width:500px;height:334px;border:0"></div>
				</div>
			</div>
		</div>
	</div>
	
		<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    				<div id="pieChartData" data-toggle="topjui-echarts" data-options="{'type':'pie','url':'/party/report/pieChartData'}" style="width:500px;height:334px;border:0"></div>
				</div>
			</div>
		</div>
		
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    				<div id="scatterChartData" data-toggle="topjui-echarts" data-options="{'type':'gauge','url':'/party/report/pieChartData'}" style="width:500px;height:334px;border:0"></div>
				</div>
			</div>
		</div>
	</div>
</div>