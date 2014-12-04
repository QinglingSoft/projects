<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/briefTree" prefix="briefTree" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<spring:useBean id="briefPage" beanName="briefPageHelper" scope="request" />
<jsp:setProperty name="briefPage" property="*" />

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
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript" src="js/briefTree.js"></script>
	<script type="text/javascript" src="js/briefTree-rightClick.js"></script>
	<script type="text/javascript" src="js/briefTree-addData.js"></script>
	<script type="text/javascript" src="js/briefTree-deleteData.js"></script>
</head>
<body>
<c:set var="briefList" value="${briefPage.list}"/>
<jsp:useBean id="linkParams" class="java.util.HashMap" scope="page"/>
<c:set target="${linkParams}" property="dataTableName" value="${dataTable.name}"/>
<c:set target="${linkParams}" property="catalog" value="${param.catalog}"/>
<list:pageTurner styleClass="pageTurner ui-widget-header" pageHelper="${briefPage}" linkParams="${linkParams}" />
<ul class="briefTree">
	<c:forEach items="${briefList}" var="brief">
		<briefTree:item catalog="${param.catalog}" brief="${brief}" dataTable="${dataTable}"/>
	</c:forEach>
</ul>
<div id="addDialog"></div>
<div id="rightClickMenu"></div>
</body>
</html>