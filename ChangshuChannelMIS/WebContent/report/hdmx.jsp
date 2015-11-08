<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<title>航道明细</title>
<style type="text/css">
	table {
		border-collapse: collapse;
		margin: 2em;
		border: solid black 1px;
	}
	table > caption {
		font-size: larger;
		font-weight: bolder;
	}
	td, th {
		border: solid black 1px;
		padding: .2em 1em;
	}

	td {
		text-align: right;
		vertical-align: middle;
		height: 1.5em;
	}
	th {
		white-space: nowrap;
	}
	tbody tr:hover {
		background-color: #e8f2fe;
	}
	#exportButton {
		position: absolute;
		top: .5em;
		left: .5em;
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
	<caption>航道明细</caption>
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