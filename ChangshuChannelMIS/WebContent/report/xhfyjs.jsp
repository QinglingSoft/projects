<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:useBean id="report" beanName="reportHelper" />
<jsp:setProperty name="report" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<link href="../css/briefList.css" rel="stylesheet" type="text/css" />
<link href="../css/generalSearch.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<style type="text/css">
	table.briefList th {
		text-align: center;
	}
	table.headerInfo {
		min-width: 100%;
		border-collapse: collapse;
		border: none;
		margin-top: 1em;
	}
	table.headerInfo th {
		text-align: center;
		padding:2px;
	}
	table.headerInfo input {
		width:50px;
	}
	select {
		width: 146px;
	}
</style>
<script type="text/javascript">
$(function(){
	$("#queryBt").click(function(){
		window.location.href = "xhfyjs.jsp?dateStr="+$("input.Wdate").val();
	});
	
	$("#exportBt").click(function(){
		window.location.href = "xhfyjs2.jsp?dateStr="+$("input.Wdate").val();
	});
});
</script>
</head>
<body>
	<c:set var="reportList" value="${report.cruiseCostReports}"/>
	<div class="normalSearch content">
		<ul>
			<li>
				<label>时间:</label>
				<fmt:formatDate var="reportDate" value="${report.reportDate}" pattern="yyyy"/>
				<input class="Wdate" type="text" name="reportDate" value="${reportDate}" onfocus="WdatePicker({dateFmt:'yyyy'})" readonly="readonly"/>
			</li>
			<li>
				<button id="queryBt">查询</button>
			</li>
			<li>
				<button id="exportBt">导出</button>
			</li>
		</ul>
	</div>
	<table  class="headerInfo">
		<tr>
			<th>常熟处<fmt:formatDate value="${report.reportDate}" pattern="yyyy"/>年度巡航燃油耗用、主辅机维保费用计算表</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th>巡航艇名称</th>
				<th>主机功率（KW）</th>
				<th>油耗率Kg/h.MV</th>
				<th>油耗Kg/h</th>
				<th>计划巡航里程Km</th>
				<th>实际巡航系数</th>
				<th>实际巡航里程Km</th>
				<th>航速Km/h</th>
				<th>实际巡航时间h</th>
				<th>耗用柴油数量Kg</th>
				<th>柴油费用</th>
				<th>主机维保费用 元/h</th>
				<th>主机维保费用 元</th>
				<th>发电机组维保费用 元</th>
				<th>全年巡航船艇总费用 元</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reportList}" var="data">
			<tr>
				<td>${data.chmc}</td>
				<td>${data.zjgl}</td>
				<td>${data.yhl}</td>
				<td>${data.yh}</td>
				<td>${data.jhxhlc}</td>
				<td>${data.sjxhsx}</td>
				<td>${data.sjshlc}</td>
				<td>${data.hs}</td>
				<td>${data.sjxhsj}</td>
				<td>${data.hycysl}</td>
				<td>${data.cyh}</td>
				<td>${data.zjwbfysx}</td>
				<td>${data.zjwbfy}</td>
				<td>${data.fdzjwbfy}</td>
				<td>${data.qyhxctzhy}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>