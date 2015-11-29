<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>

<spring:useBean id="departmentHelper" beanName="departmentHelper" />

<spring:useBean id="userPage" beanName="userPageHelper"/>
<jsp:setProperty property="*" name="userPage" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<link href="../css/listTable.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="../css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<title>用户管理</title>
</head>
<body>
<div class="title ui-widget-header">用户列表</div>
<div class="errorMessage">${errorMessage}</div>
<center>
<list:morePage pageHelper="${userPage}" label="用户" functionUrl="newUser.jsp" functionLabel="新增">
	<jsp:attribute name="tableHead">
		<th>登录名</th>
		<th>姓名</th>
		<th>角色</th>
		<th>所属</th>
		<th>操作</th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<td>${entry.loginName}</td>
		<td>${entry.name}</td>
		<td>${entry.role.text}</td>
		<td>
		<c:if test="${not entry.superAdmin and not entry.guest}">
			<c:forEach items="${departmentHelper.all}" var="department">
				<c:if test="${entry.deptCode == department.gljg_ksid}">${department.gljg_qhdm}</c:if>
			</c:forEach>
		</c:if>
		</td>
		<td>
			<c:if test="${not entry.superAdmin and not entry.guest}">
				<c:url var="editUrl" value="editUser.jsp">
					<c:param name="loginName" value="${entry.loginName}" />
				</c:url>
				<a href="${editUrl}">编辑</a>
				|
				<c:url var="deleteUrl" value="deleteUser.action">
					<c:param name="loginName" value="${entry.loginName}" />
				</c:url>
				<a href="${deleteUrl}" onclick="return confirm('确定要删除“${entry.loginName}”？');">删除</a>
				|
				<c:url var="initPasswordUrl" value="initUserPassword.action">
					<c:param name="loginName" value="${entry.loginName}" />
				</c:url>
				<a href="${initPasswordUrl}" onclick="return confirm('确定要初始化“${entry.loginName}”的密码？');">初始化密码</a>
			</c:if>
		</td>
	</jsp:attribute>
</list:morePage>
</center>
</body>
</html>