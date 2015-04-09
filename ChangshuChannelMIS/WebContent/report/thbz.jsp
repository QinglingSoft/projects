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
	table.briefList {
		margin-bottom: 0em; 
	}
</style>
<script type="text/javascript">
$(function(){
	$("#queryBt").click(function(){
		window.location.href = "thbz.jsp?dateStr="+$("input[name=dateStr]").val();
	});
	
	$("#exportBt").click(function(){
		window.location.href = "thbz2.jsp?dateStr="+$("input[name=dateStr]").val();
	});
});
</script>
</head>
<body>
	<c:set var="reportList" value="${report.channelStateReports}"/>
	<div class="normalSearch content">
		<ul>
			<li>
				<label>时间:</label>
				<input class="Wdate" type="text" name="dateStr" value="${report.dateStr}" onfocus="WdatePicker({dateFmt:'yyyy年M季度', isQuarter:true, isShowOK:false,disabledDates:['....-0[5-9]-..','....-1[0-2]-..'], startDate:'%y-01-01' })" readonly="readonly" />
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
			<th colspan="2" style="text-align: center;">航道通航保证情况报表</th>
		</tr>
		<tr>
			<th>单位：${report.unitName}</th>
			<th>统计时间：${report.dateStr}</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th>航道名称</th>
				<th>断航时间</th>
				<th>断航原因</th>
				<th>通航保证率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reportList}" var="data">
			<tr>
				<td>${data.hdmcStr}</td>
				<td>${data.dhsj}</td>
				<td>${data.dhyy}</td>
				<td>${data.thbzl}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<table  class="headerInfo">
		<tr>
			<th>填报人：${report.personName}</th>
			<th>填报时间：<fmt:formatDate value="${report.otherDate}" pattern="yyyy年MM月dd日"/></th>
		</tr>
	</table>
</body>
</html>