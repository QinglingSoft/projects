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
<title>统计汇总</title>
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
<c:set var="loginUser" value="${sessionScope.loginUser}"/>
<c:set var="xzqhPrefix"><c:choose>
	<c:when test="${loginUser.admin}"></c:when>
	<c:when test="${loginUser.guest}">XXXXXXXXXXXX</c:when>
	<c:otherwise>${loginUser.deptCodeActivePart}</c:otherwise>
</c:choose></c:set>
<sql:query var="result">
	EXECUTE ${param.procedureName} '${xzqhPrefix}'
</sql:query>
<center>
<table>
	<caption>${param.label}</caption>
	<thead class="ui-widget-header">
		<tr>
			<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
				<c:if test="${not status.first}">
					<th>${columnName}</th>
				</c:if>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${result.rows}" var="row" varStatus="status">
			<c:if test="${result.rowCount < 100 || status.index < 40}">
				<tr>
					<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
						<c:if test="${not status.first}">
							<td>${row[columnName]}</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:if>
		</c:forEach>
		<c:if test="${result.rowCount >= 100}">
			<tr title="尚有${result.rowCount - 40}条未能显示，请导出Excel查看">
				<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
					<c:if test="${not status.first}">
						<td>……</td>
					</c:if>
				</c:forEach>
			</tr>
		</c:if>
	</tbody>
</table>
<c:url var="exportUrl" value="statisticExportExcel.action">
	<c:param name="procedureName" value="${param.procedureName}" />
	<c:param name="label" value="${param.label}" />
</c:url>
<button id="exportButton" onclick="window.location='${exportUrl}'">导出Excel</button>
</center>
</body>
</html>