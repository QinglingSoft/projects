<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@page import="java.util.Calendar"%>  
<%  
    response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/vnd.ms-excel");  
    response.setHeader("Content-Disposition", "attachment;filename=\""  
            + new String( "航 道 明细表".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
%> 
<spring:useBean id="report" beanName="reportHelper" />
<jsp:setProperty name="report" property="*" />
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name></x:Name><x:WorksheetOptions><x:Selected/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
<style type="text/css">
	td{
		mso-number-format:'\@'; 
	}
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
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select t1.hddm as 航道代码,hdmc as 航道名称,qdmc as 起点名称,zdmc as 终点名称,qdlc as "起点里程(公里)",zdlc as "终点里程(公里)",hdlc as "航段里程(公里)",航段现状技术等级含义,航段定级技术等级含义 from (
select hddm,qdmc,zdmc,qdlc,zdlc,hdlc,
case when hdxzdj='1' then '一级航道'
     when hdxzdj='2' then '二级航道'
     when hdxzdj='3' then '三级航道'
     when hdxzdj='4' then '四级航道'
     when hdxzdj='5' then '五级航道'
     when hdxzdj='6' then '六级航道'
     when hdxzdj='7' then '七级航道'
     when hdxzdj='8' then '等外级航道' else null end as 航段现状技术等级含义,
case when HDGHDJ='1' then '一级航道'
     when HDGHDJ='2' then '二级航道'
     when HDGHDJ='3' then '三级航道'
     when HDGHDJ='4' then '四级航道'
     when HDGHDJ='5' then '五级航道'
     when HDGHDJ='6' then '六级航道'
     when HDGHDJ='7' then '七级航道'
     when HDGHDJ='8' then '等外级航道' else null end as 航段定级技术等级含义
from t_hdhd) as t1 left join (select hddm,hdmc from t_hd) as t2 on t1.hddm=t2.hddm
order by t1.hddm,qdlc
</sql:query>
<center>
<table>
	<thead class="ui-widget-header">
		<tr>
			<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
				<th>${columnName}</th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${result.rows}" var="row" varStatus="status">
			<tr>
					<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
						<td>${row[columnName]}</td>
					</c:forEach>
				</tr>
		</c:forEach>
	</tbody>
</table>
<button id="exportButton" onclick="window.location.href = 'hdmx2.jsp'">导出Excel</button>
</center>
</body>
</html>