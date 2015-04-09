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
</style>
<script type="text/javascript">
$(function(){
	$("#queryBt").click(function(){
		window.location.href = "xhjh.jsp?dateStr="+$("input.Wdate").val();
	});
	
	$("#exportBt").click(function(){
		window.location.href = "xhjh2.jsp?dateStr="+$("input.Wdate").val();
	});
});
</script>
</head>
<body>
	<c:set var="reportList" value="${report.cruisePlanReports}"/>
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
			<th>航政巡航计划（<fmt:formatDate value="${report.reportDate}" pattern="yyyy年MM月"/>）</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th rowspan="2">日期</th>
				<th colspan="${report.hangDaoNum}">巡航航道</th>
			</tr>
			<tr>
				<c:forEach items="${reportList}" var ="xhccList" varStatus="status"> 
					<c:if test="${status.first}">
						<c:forEach items="${xhccList}" var ="code" varStatus="status">
							<th>${code.meaning}</th>
						</c:forEach>
					</c:if> 
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reportList}" var ="xhhdList" begin="1"> 
					<c:forEach items="${xhhdList}" var ="datas">
						<tr>
							<c:forEach items="${datas}" var ="xhhd">
								<c:if test="${empty xhhd}">
									<td>&nbsp;</td>
								</c:if>
								<c:if test="${not empty xhhd}">
									<td>${xhhd}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach> 
				</c:forEach>
		</tbody>
		
	</table>
</body>
</html>