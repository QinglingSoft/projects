<?xml version="1.0" encoding="UTF-8" ?>
<%-- 
  - @(#)
  - Description: 桥梁评定树，以单个桥梁为根，
  - Params: QLBM 桥梁编码
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/briefTree" prefix="briefTree" %>
<%@ taglib tagdir="/WEB-INF/tags/auth" prefix="auth" %>

<%-- 获取桥梁描述表对象 --%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<c:set target="${dataTableHelper}" property="dataTableName" value="T_QL" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<%-- 获取指定记录 --%>
<spring:useBean id="tableDataHelper" beanName="tableDataHelper" scope="request" />
<c:set target="${tableDataHelper}" property="dataTableName" value="T_QL" />
<c:set target="${tableDataHelper.primaryKeys}" property="QLBM" value="${param.QLBM}" />
<c:set var="data" value="${tableDataHelper.data}" />

<%-- 授权 --%>
<jsp:useBean id="pkValues" class="java.util.HashMap" />
<c:set target="${pkValues}" property="QLBM" value="${param.QLBM}" />
<auth:loadAuthorizationHelper data="${pkValues}" dataTable="${dataTable}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${dataTable.label}</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link href="css/briefTree.css" rel="stylesheet" type="text/css" />
	<link href="css/briefTree-tableIcon.css.jsp" rel="stylesheet" type="text/css" />
	<link href="css/briefTree-addDialog.css" rel="stylesheet" type="text/css" />
	<link href="css/treeRightClickMenu.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript" src="js/briefTree.js"></script>
	<%-- 视授权情况提供右键功能 --%>
	<c:if test="${authorizationHelper.hasPermission}">
		<script type="text/javascript" src="js/briefTree-rightClick.js"></script>
	</c:if>
	<script type="text/javascript" src="js/briefTree-addData.js"></script>
	<script type="text/javascript" src="js/briefTree-deleteData.js"></script>
</head>
<body>

<ul class="briefTree">
	<briefTree:item catalog="综合评定" brief="${data}" dataTable="${dataTable}"/>
</ul>
<div id="addDialog"></div>
<div id="rightClickMenu"></div>
</body>
</html>