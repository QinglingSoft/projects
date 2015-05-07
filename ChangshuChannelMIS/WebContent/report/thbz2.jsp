<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.Calendar"%>  
<%  
    response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/vnd.ms-excel");    
    response.setHeader("Content-Disposition", "attachment;filename=\""  
    		+ new String( "航道通航保证情况报表".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");    
%> 
<spring:useBean id="report" beanName="reportHelper" />
<jsp:setProperty name="report" property="*" />
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name></x:Name><x:WorksheetOptions><x:Selected/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
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
</head>
<body>
	<c:set var="reportList" value="${report.channelStateReports}"/>
	<table  class="headerInfo">
		<tr>
			<th colspan="4" style="text-align: center;">航道通航保证情况报表</th>
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