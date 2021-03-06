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
            + new String( "航 道 工 程 完 成 情 况 月（年）表".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
%> 
<spring:useBean id="report" beanName="reportHelper" />
<jsp:setProperty name="report" property="*" />
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name></x:Name><x:WorksheetOptions><x:Selected/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
<style type="text/css">
	table.briefList {
		margin-bottom:1em;
	}
	table.briefList th {
		text-align: center;
	}
	table.headerInfo, table.footerInfo {
		min-width: 100%;
		border-collapse: collapse;
		border: none;
		margin-top: 1em;
		margin-left:1em;
	}
	table.headerInfo th,table.footerInfo th {
		text-align: left;
		padding:2px;
	}
	table.headerInfo th.center {
		text-align: center;
	}
	table.footerInfo {
		margin-bottom: 4em;
	}
</style>
</head>
<body>
	<c:set var="reportList" value="${report.projectProcessReports}"/>
	<table  class="headerInfo">
		<tr>
			<th colspan="10" style="text-align: center;">航 道 工 程 完 成 情 况 月（年）表</th>
		</tr>
		<tr>
			<th></th><th></th>
			<th colspan="5"></th>
			<th class="center">表&nbsp;&nbsp;号：</th>
		</tr>
		<tr>
			<th></th><th></th>
			<th colspan="5"></th>
			<th class="center">报送期限：</th>
		</tr>
		<tr>
			<th></th><th></th>
			<th colspan="5"></th>
			<th class="center">制表机关：</th>
		</tr>
		<tr>
			<th>填报单位（盖章）：</th><th></th>
			<th colspan="5"><fmt:formatDate value="${report.reportDate}" pattern="yyyy年MM月"/></th>
			<th class="center">备案机关：</th>
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
				<th>自年初累计</th>
				<th>本月完成</th>
				<th>自年初累计</th>
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
	<table  class="footerInfo">
		<tr>
			<th>单位负责人（盖章）：</th><th></th>
			<th>统计部门负责人（盖章）：</th><th></th>
			<th>制表人（盖章）：</th><th></th>
			<th>实际报出日期：</th>
			<th>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</th>
		</tr>
	</table>
</body>
</html>