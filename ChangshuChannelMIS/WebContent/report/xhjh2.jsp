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
    		+ new String( "航政巡航计划".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
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
	<c:set var="reportList" value="${report.cruisePlanReports}"/>
	<table  class="headerInfo">
		<tr>
			<th colspan="${report.hangDaoNum+1}">航政巡航计划（<fmt:formatDate value="${report.reportDate}" pattern="yyyy年MM月"/>）</th>
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