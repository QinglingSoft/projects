<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<spring:useBean id="articlePage" beanName="articlePageHelper" />
<jsp:setProperty name="articlePage" property="currentPage" />
<c:set target="${articlePage}" property="articleType" value="NOTICE"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>通知管理</title>
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<link href="../css/listTable.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="../css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<style type="text/css">
	body {
		text-align: center;
	}
</style>
<script type="text/javascript">
	$(function(){
		$(".listTable thead>tr").addClass("ui-widget-header");
	});
</script>
</head>
<body>
<center>
<list:morePage pageHelper="${articlePage}" label="通知" functionUrl="newNotice.jsp?type=NEWS" functionLabel="撰写">
	<jsp:attribute name="tableHead">
		<th>标题</th>
		<th>最后修改时间</th>
		<th>状态</th>
		<th>操作</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<td>${entry.title}</td>
		<td><fmt:formatDate value="${entry.latestModified}" type="BOTH"/></td>
		<td>
			<c:choose>
				<c:when test="${entry.published}">已发布</c:when>
				<c:otherwise>草稿</c:otherwise>
			</c:choose>
		</td>
		<td>
			<c:choose>
				<c:when test="${entry.published}">
					<c:url var="unpublishUrl" value="unpublishNotice.action">
						<c:param name="articleId" value="${entry.id}" />
					</c:url>
					<a href="${unpublishUrl}">撤稿</a>
				</c:when>
				<c:otherwise>
					<c:url var="publishUrl" value="publishNotice.action">
						<c:param name="articleId" value="${entry.id}" />
					</c:url>
					<a href="${publishUrl}">发布</a>
					<c:url var="editUrl" value="editNotice.jsp">
						<c:param name="articleId" value="${entry.id}" />
					</c:url>
					<a href="${editUrl}">编辑</a>
					<c:url var="deleteUrl" value="deleteNotice.action">
						<c:param name="articleId" value="${entry.id}" />
					</c:url>
					<a href="${deleteUrl}" onclick="return confirm('确定删除？')">删除</a>
				</c:otherwise>
			</c:choose>
		</td>
	</jsp:attribute>
</list:morePage>
</center>
</body>
</html>