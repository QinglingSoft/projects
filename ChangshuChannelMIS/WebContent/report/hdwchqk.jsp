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
		text-align: left;
		padding:2px;
	}
	table.headerInfo input {
		width:50px;
	}
</style>
<script type="text/javascript">
$(function(){
	$("#queryBt").click(function(){
		window.location.href = "hdwchqk.jsp?dateStr="+$("input.Wdate").val();
	});
	
	$("#exportBt").click(function(){
		window.location.href = "hdwchqk2.jsp?dateStr="+$("input.Wdate").val();
	});
});
</script>
</head>
<body>
	<c:set var="reportList" value="${report.projectProcessReports}"/>
	<div class="normalSearch content">
		<ul>
			<li>
				<label>时间:</label>
				<fmt:formatDate var="reportDate" value="${report.reportDate}" pattern="yyyy-MM"/>
				<input class="Wdate" type="text" name="reportDate" value="${reportDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly"/>
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
			<th>填报单位（盖章）：</th>
			<th><fmt:formatDate value="${report.reportDate}" pattern="yyyy年MM月"/></th>
			<th>备案机关：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省</th>
			<th>统计</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th rowspan="3">工程项目名称</th>
				<th>工程地点</th>
				<th>工程内容</th>
				<th colspan="4">工程量</th>
				<th colspan="3">工程量(千元)</th>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th rowspan="2">计量单位</th>
				<th rowspan="2">本年计划</th>
				<th colspan="2">实际完成</th>
				<th rowspan="2">本年计划</th>
				<th colspan="2">实际完成</th>
			</tr>
			<tr>
				<th>线&nbsp;别</th>
				<th>概&nbsp;要</th>
				<th>本月完成</th>
				<th>自年初累</th>
				<th>本月完成</th>
				<th>自年初累</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>甲</td>
				<td>乙</td>
				<td>丙</td>
				<td>丁</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
				<td>5</td>
				<td>6</td>
			</tr>
			<c:forEach items="${reportList}" var="data">
			<tr>
				<td>${data.mc}</td>
				<td>${data.qcxmddlb}</td>
				<td>${data.gcnrky}</td>
				<td>${data.dw}</td>
				<td>${data.byjhgzl}</td>
				<td>${data.bysjwcl}</td>
				<td>${data.zncljwcgzl}</td>
				<td>${data.bnjhgzlhy}</td>
				<td>${data.bnsjwclhy}</td>
				<td>${data.zncljwcgzlhy}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>