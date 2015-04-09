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
            + String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", Calendar.getInstance()) + ".xls\"");     
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
</head>
<body>
	<c:set var="reportList" value="${report.cruiseMileageReports}"/>
	<table  class="headerInfo">
		<tr>
			<th colspan="7">常熟市航道处<fmt:formatDate value="${report.reportDate}" pattern="yyyy年"/>巡航里程计算</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th>航道名称</th>
				<th>巡航车船</th>
				<th>&nbsp;</th>
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