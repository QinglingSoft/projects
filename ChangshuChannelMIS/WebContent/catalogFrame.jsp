<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="x-ua-compatible" content="ie=8" /> 
	<base target="catalogDetail" />
	<title>按栏目分</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/catalogFrame.css" rel="stylesheet" type="text/css" />
 	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("div.tab-pane")
				.css("left", $("div.tab-label").outerWidth() + 2)
				.resize(function() {
					$("#catalogDetail").height(($(this).innerHeight() - 2) + 'px');
				}).resize();
			$("ul.tab-label>li").click(function(){
				$(this)
					.addClass("selected")
					.siblings()
						.removeClass("selected");
			});
		});
	</script>
</head>
<body>
<div class="tab-label">
	<ul class="tab-label">
		<c:url var="detailUrl" value="tableDetail.jsp">
			<c:param name="dataTableName" value="${dataTable.name}"/>
			<c:forEach items="${dataTable.primaryKeys}" var="pk">
				<c:set var="paramName" value="primaryKeys.${pk.name}" />
				<c:param name="primaryKeys.${pk.name}" value="${param[paramName]}"/>
			</c:forEach>
		</c:url>
		<li class="selected"><a href="${detailUrl}">${dataTable.label}</a></li>
		<!-- catalog: ${param.catalog} -->
		<c:forEach items="${dataTable.catalogChildrenMap[param.catalog]}" var="childTable">
			<c:url var="childDetailUrl" value="childTableDetail.jsp">
				<c:param name="dataTableName" value="${childTable.name}"/>
				<c:forEach items="${dataTable.primaryKeys}" var="pk">
					<c:set var="paramName" value="primaryKeys.${pk.name}" />
					<c:param name="primaryKeys.${pk.name}" value="${param[paramName]}"/>
				</c:forEach>
			</c:url>
			<li><a href="${childDetailUrl}">${childTable.label}</a></li>
		</c:forEach>
	</ul>
</div>
<div class="tab-pane"><iframe id="catalogDetail" name="catalogDetail" src="${detailUrl}" frameborder="0" style="width: 100%"></iframe></div>
</body>
</html>