<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/briefTree" prefix="briefTree" %>
<%@ taglib tagdir="/WEB-INF/tags/search" prefix="search" %>
<%@ taglib tagdir="/WEB-INF/tags/auth" prefix="auth" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<auth:loadAuthorizationHelper dataTable="${dataTable}" data="${data}" />
<spring:useBean id="briefPage" beanName="briefPageHelper" />
<jsp:setProperty name="briefPage" property="*" />
<c:set target="${briefPage}" property="conditions" value="${conditionList}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${dataTable.label}</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link href="css/pageTurner.css" rel="stylesheet" type="text/css" />
	<link href="css/briefTree.css" rel="stylesheet" type="text/css" />
	<link href="css/briefTree-addDialog.css" rel="stylesheet" type="text/css" />
	<link href="css/briefTree-tableIcon.css.jsp" rel="stylesheet" type="text/css" />
	<link href="css/briefTreeList.css" rel="stylesheet" type="text/css" />
	<link href="css/treeRightClickMenu.css" rel="stylesheet" type="text/css" />
	<link href="css/generalSearch.css" rel="stylesheet" type="text/css" />
	<link href="css/briefList.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript" src="js/briefTree.js"></script>
	<script type="text/javascript" src="js/briefTree-rightClick.js"></script>
	<script type="text/javascript" src="js/briefTree-addData.js"></script>
	<script type="text/javascript" src="js/briefTree-deleteData.js"></script>
	<script type="text/javascript" src="js/dataTableSearch.js"></script>
	<script type="text/javascript">
		$(function(){
			$("table.briefList > tbody > tr").click(function(){
				var parentPks = $(this).attr("parentPks");
				window.parent.briefSelected(parentPks);
				$(this).addClass("selected").siblings().removeClass("selected");
			});
		});
	</script>
</head>
<body>
<c:set var="briefList" value="${briefPage.list}"/>
<c:set value="${authorizationHelper.hasPermission}" var="permission" />
<jsp:useBean id="linkParams" class="java.util.HashMap" scope="page"/>
<c:set target="${linkParams}" property="dataTableName" value="${dataTable.name}"/>
<c:set target="${linkParams}" property="catalog" value="${param.catalog}"/>
<list:pageTurner styleClass="pageTurner ui-widget-header" pageHelper="${briefPage}" linkParams="${linkParams}" />

<div id="searchDiv">
	<c:set var="displayValue">
		<c:if test="${not empty param.catalog}">
			display: none
		</c:if>
	</c:set>
	<div style="${displayValue}" id="searchConditions">
		<search:tableName dataTableName="${dataTable.name}" catalog="${dataTable.catalog}"/>
	</div>
	<c:if test="${not empty param.catalog}"><img src="images/down.gif" /></c:if><c:if test="${empty param.catalog}"><img src="images/up.gif" /></c:if>
	<c:if test="${empty param.catalog}">
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<c:forEach items="${dataTable.briefFields}" var="field">
					<th>
						${field.label}
						<c:if test="${not empty field.unit}">(${field.unit})</c:if>
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${briefList}" var="brief">
				<c:set var="primaryKeyValues">
					({
					<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
						<c:if test="${not status.first}">,</c:if>${pk.name}: '${brief[pk.name]}'
					</c:forEach>
					})
				</c:set>
				<spring:useBean id="childTableDataHelper" beanName="childTableDataHelper" scope="page" />
				<c:set target="${childTableDataHelper}" property="dataTableName" value="${dataTable.name}" />
				<c:forEach items="${dataTable.primaryKeys}" var="pk">
					<c:set var="paramName" value="${pk.name}"/>
					<c:set target="${childTableDataHelper.params}" property="${pk.name}" value="${brief[pk.name]}"/>
				</c:forEach>
		
				<tr primaryKeyValues="${primaryKeyValues}" parentPks="${childTableDataHelper.parentPrimaryKeyStr}">
					<c:forEach items="${dataTable.briefFields}" var="field">
						<c:set var="unitTitle"><c:if test="${not empty field.unit}">title="（${field.unit}）"</c:if></c:set>
						<c:choose>
							<c:when test="${field.type == 'STRING'}">
								<td ${unitTitle}>${brief[field.name]}</td>
							</c:when>
							<c:when test="${field.type == 'NUMBER'}">
								<td ${unitTitle}><fmt:formatNumber maxFractionDigits="${field.fractionDigits}" value="${brief[field.name]}"/></td>
							</c:when>
							<c:when test="${field.type == 'DATE'}">
								<td><fmt:formatDate type="DATE" value="${brief[field.name]}" /></td>
							</c:when>
							<c:when test="${field.type == 'DATETIME'}">
								<td><fmt:formatDate type="BOTH" value="${brief[field.name]}" /></td>
							</c:when>
							<c:when test="${field.type == 'CODE'}">
								<td ${unitTitle}>${field.codeTable.codes[brief[field.name]].meaning}</td>
							</c:when>
							<c:otherwise>
								<td>${field.type}型</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
</div>
<c:if test="${not empty param.catalog}">
<input type="hidden" id="keyIds" value="${param.keyIds}"/>
<ul class="briefTree clear">
	<c:forEach items="${briefList}" var="brief">
		<briefTree:item catalog="${param.catalog}" brief="${brief}" dataTable="${dataTable}"/>
	</c:forEach>
</ul>
<div id="addDialog"></div>
<div id="rightClickMenu" permission="${permission}"></div>
</c:if>
</body>
</html>