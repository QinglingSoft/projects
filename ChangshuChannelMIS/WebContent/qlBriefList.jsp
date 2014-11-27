<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

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
	<link href="css/locate_ql.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/qlBriefList.js"></script>
	<script type="text/javascript" src="js/qlBriefList_rightClick.js"></script>
</head>
<body>
<c:set var="briefList" value="${briefPage.list}"/>

<jsp:useBean id="linkParams" class="java.util.HashMap" scope="page"/>
<c:set target="${linkParams}" property="dataTableName" value="${dataTable.name}"/>
<list:pageTurner styleClass="pageTurner ui-widget-header" pageHelper="${briefPage}" linkParams="${linkParams}" />

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
						<c:when test="${field.type == 'CODE'}">
							<td ${unitTitle}>${field.codeTable.codes[brief[field.name]]}</td>
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
<div id='rightClickMenu'>定位</div>
</body>
</html>