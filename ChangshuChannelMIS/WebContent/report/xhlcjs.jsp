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
		window.location.href = "xhlcjs.jsp?dateStr="+$("input.Wdate").val();
	});
	
	$("#exportBt").click(function(){
		window.location.href = "xhlcjs2.jsp?dateStr="+$("input.Wdate").val();
	});
});
</script>
</head>
<body>
	<c:set var="reportList" value="${report.cruiseMileageReports}"/>
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
			<th>常熟市航道处<fmt:formatDate value="${report.reportDate}" pattern="yyyy年"/>巡航里程计算</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th>航道名称</th>
				<th>巡航车船</th>
				<th>趟次</th>
				<th>次数</th>
				<th>里程</th>
				<th>计划巡航里程</th>
				<th>实际往返巡航里程</th>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>Km</th>
				<th>&nbsp;</th>
				<th>Km</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reportList}" var="data">
				<c:if test="${not empty data.sgcz && not empty data.hdmc}">
					<tr>
						<td>${data.hdStr}</td>
						<td>${data.chStr}</td>
						<td>${data.wflcStr}</td>
						<td>${data.xhcs}</td>
						<td>${data.hdlc}</td>
						<td>${data.jhxhlc}</td>
						<td>${data.sjwfxjlc}</td>
					</tr>
				</c:if>
				<c:if test="${empty data.sgcz && empty data.hdmc}">
					<tr>
						<td colspan="5" style="text-align:center">${data.hdStr}</td>
						<td>${data.jhxhlc}</td>
						<td>${data.sjwfxjlc}</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>