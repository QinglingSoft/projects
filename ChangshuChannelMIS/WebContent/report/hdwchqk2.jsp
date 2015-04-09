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
</style>
</head>
<body>
	<c:set var="reportList" value="${report.projectProcessReports}"/>
	<table  class="headerInfo">
		<tr>
			<th>填报单位（盖章）：</th>
			<th colspan="4"><fmt:formatDate value="${report.reportDate}" pattern="yyyy年MM月"/></th>
			<th colspan="3">备案机关：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省</th>
			<th colspan="2">统计</th>
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