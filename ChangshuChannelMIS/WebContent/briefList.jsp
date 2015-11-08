<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<%@ taglib tagdir="/WEB-INF/tags/auth" prefix="auth" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<auth:loadAuthorizationHelper dataTable="${dataTable}" data="${dataTableHelper.primaryKeys}" />
<spring:useBean id="briefPage" beanName="briefPageHelper" />
<jsp:setProperty name="briefPage" property="*" />
<c:set target="${briefPage}" property="conditions" value="${sessionScope.conditions}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${dataTable.label}</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link href="css/pageTurner.css" rel="stylesheet" type="text/css" />
	<link href="css/briefList.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			var deleteFlag = false;
			$("table.briefList > tbody > tr > td.delete").click(function(){
				if (!confirm("确定删除？")) {
					return;
				}
				var pkJson = $(this).parent().attr("primaryKeyValues");
				var primaryKeyValues = eval(pkJson);
				window.parent.deleteSelected(primaryKeyValues);
				$(this).addClass("selected").siblings().removeClass("selected");
				deleteFlag = true;
			});
			
			$("table.briefList > tbody > tr").click(function(){
				if(deleteFlag){return;}
				var pkJson = $(this).attr("primaryKeyValues");
				var primaryKeyValues = eval(pkJson);
				window.parent.briefSelected(primaryKeyValues);
				$(this).addClass("selected").siblings().removeClass("selected");
			});
			
			$("#addRootDataButton").click(function(){
				window.parent.addRootData();
			});
			
			$("#exportRootDataButton").click(function(){
				window.location="exportDatas.action?dataTableName=${dataTable.name}&label=${dataTable.label}"
			});
		});
	</script>
</head>
<body>
<c:set var="briefList" value="${briefPage.list}"/>

<jsp:useBean id="linkParams" class="java.util.HashMap" scope="page"/>
<c:set target="${linkParams}" property="dataTableName" value="${dataTable.name}"/>
<list:pageTurner styleClass="pageTurner ui-widget-header" pageHelper="${briefPage}" linkParams="${linkParams}" />

<table class="briefList">
	<caption style="text-align: right;margin:1px 1px"><c:if test="${authorizationHelper.hasPermission}"> <button id="addRootDataButton">新增</button></c:if>&nbsp;<button id="exportRootDataButton">导出</button></caption>
	<thead class="ui-widget-header">
		<tr>
			<c:forEach items="${dataTable.briefFields}" var="field">
				<th>
					${field.label}
					<c:if test="${not empty field.unit}">(${field.unit})</c:if>
				</th>
			</c:forEach>
			<th>操作</th>
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
			<tr primaryKeyValues="${primaryKeyValues}">
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
						<c:when test="${field.type == 'DATEMONTH'}">
							<td ${unitTitle}>${brief[field.name]}</td>
						</c:when>
						<c:when test="${field.type == 'DATEYEAR'}">
							<td ${unitTitle}>${brief[field.name]}</td>
						</c:when>
						<c:when test="${field.type == 'DATEQUARTER'}">
							<td ${unitTitle}>${brief[field.name]}</td>
						</c:when>
						<c:when test="${field.type == 'CODE'}">
							<td ${unitTitle}>${field.codeTable.codes[brief[field.name]].meaning}</td>
						</c:when>
						<c:otherwise>
							<td>${field.type}型</td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${authorizationHelper.hasPermission}">
						<td class="delete">删除</td>
					</c:when>
					<c:otherwise><td></td></c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
	
</table>
</body>
</html>