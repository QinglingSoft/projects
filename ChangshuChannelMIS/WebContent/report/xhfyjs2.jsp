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
    		+ new String( "年度巡航燃油耗用、主辅机维保费用计算表".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");      
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
	<c:set var="reportList" value="${report.cruiseCostReports}"/>
	<table  class="headerInfo">
		<tr>
			<th colspan="15">常熟处<fmt:formatDate value="${report.reportDate}" pattern="yyyy"/>年度巡航燃油耗用、主辅机维保费用计算表</th>
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