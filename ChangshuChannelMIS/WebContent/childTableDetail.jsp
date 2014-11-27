<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/functions" prefix="wfn" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/childTable" prefix="childTable" %>
<%@ taglib tagdir="/WEB-INF/tags/auth" prefix="auth" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />

<spring:useBean id="childTableDataHelper" beanName="childTableDataHelper" scope="request" />
<jsp:setProperty name="childTableDataHelper" property="dataTableName" />
<c:set var="parentTable" value="${dataTable.parent}"/>

<c:forEach items="${parentTable.primaryKeys}" var="parentPk">
	<c:set var="paramName" value="primaryKeys.${parentPk.name}" />
	<c:set target="${childTableDataHelper.parentPrimaryKeys}" property="${parentPk.name}" value="${param[paramName]}" />
</c:forEach>

<auth:loadAuthorizationHelper data="${childTableDataHelper.parentPrimaryKeys}" dataTable="${parentTable}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link type="text/css" href="css/formDialog.css" rel="stylesheet" />	
	<link type="text/css" href="css/childTableDetail.css" rel="stylesheet" />	
	<link type="text/css" href="css/record.css" rel="stylesheet" />	
	<link type="text/css" href="css/briefTree-addDialog.css" rel="stylesheet" />	
	<title>${dataTable.label}</title>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript" src="js/childTableDetail.js"></script>
	<script type="text/javascript" src="js/childTableDetail-addData.js"></script>
	<script type="text/javascript" src="js/childTableDetail-deleteData.js"></script>
</head>
<body>
	<%-- 父表摘要 --%>
	<fieldset class="parentBrief ui-widget-header">
		<legend>${parentTable.label}摘要</legend>
		<c:set var="parentBrief" value="${childTableDataHelper.parentBrief}" />
		<c:forEach items="${parentTable.briefFields}" var="briefField">
			<span class="entry"><span class="label">${briefField.label}：</span><span class="value">${parentBrief[briefField.name]}</span></span>
		</c:forEach>
	</fieldset>
	<%-- 父表摘要结束 --%>
	
	<ul class="childTableData">
		<c:forEach items="${childTableDataHelper.dataList}" var="data">
			<c:set var="recordExist" value="true" />
			<c:set var="pkJson"><data:pkJson data="${data}" dataTable="${dataTable}" /></c:set>
			<li dataTableName="${dataTable.name}" primaryKeyValues="${pkJson}">
					<c:set var="brief"><data:briefTitle data="${data}" dataTable="${dataTable}" /></c:set>
				<%-- 有权限的情况下提供删除按钮 --%>
				<c:if test="${authorizationHelper.hasPermission}">
					<img class="deleteButton" src="images/delete.gif" title="删除" brief="${brief}" />
				</c:if>
				<h1 class="ui-widget-header">&nbsp;${brief}&nbsp;</h1>
				<data:record data="${data}" dataTable="${dataTable}" />
			</li>
		</c:forEach>
	</ul>
	<c:set var="parentPkJson"><data:mapJson mapObject="${childTableDataHelper.parentPrimaryKeys}" /></c:set>
	
	<%-- 对父表有权限的情况下，视子表多重性提供新增功能 --%>
	<c:if test="${authorizationHelper.hasPermission}">
		<c:if test="${dataTable.multi or empty recordExist}">
			<div id="addButton" dataTableIsMulti="${dataTable.multi}" dataTableName="${dataTable.name}" parentPrimaryKeyValues="${parentPkJson}">新增</div>
		</c:if>
		<div id="addDialog" title="新增${dataTable.label}">
			<childTable:newDataDialog dataTable="${dataTable}" parentPrimaryKeys="${childTableDataHelper.parentPrimaryKeys}" />
		</div>
	</c:if>
</body>
</html>