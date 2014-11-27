<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="userHelper" beanName="userHelper"></spring:useBean>
<jsp:setProperty property="loginName" name="userHelper"/>
<c:set var="user" value="${userHelper.user}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<link href="../css/form.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="../css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<title>编辑用户</title>
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/localization/messages_cn.js"></script>
<script type="text/javascript">
	$(function() {
		$("#editUserForm").validate();
	});
</script>
</head>
<body>
<div class="title ui-widget-header">编辑用户</div>
<div class="errorMessage">${errorMessage}</div>
<center>
<form id="editUserForm" action="updateUser.action">
	<input type="hidden" name="user.loginName" value="${user.loginName}" />
	<table class="form">
		<tr>
			<th>登录名：</th>
			<td>${user.loginName}</td>
		</tr>
		<tr>
			<th>姓名：</th>
			<td><input name="user.name" value="${user.name}" class="required" /></td>
		</tr>
		<tr>
			<th>角色：</th>
			<td>
				<c:set var="checked"><c:if test="${user.role == 'ADMIN'}">checked="checked"</c:if></c:set>
				<input type="radio" name="user.role" value="ADMIN" ${checked} />管理员
				<c:set var="checked"><c:if test="${user.role == 'USER'}">checked="checked"</c:if></c:set>
				<input type="radio" name="user.role" value="USER" ${checked} />普通用户
			</td>
		</tr>
		<tr>
			<th>所属：</th>
			<td>
				<spring:useBean id="codeTableHelper" beanName="codeTableHelper" />
				<c:set target="${codeTableHelper}" property="codeTableName" value="C_XZQH" />
				<select name="user.deptCode" class="required">
					<option value="">--- 请选择 ---</option>
					<c:forEach items="${codeTableHelper.codeTable.codes}" var="codeEntry">
						<c:if test="${fn:endsWith(codeEntry.key, '000000')}">
							<c:set var="selected"><c:if test="${user.deptCode == codeEntry.key}">selected="selected"</c:if></c:set>
							<option value="${codeEntry.key}" ${selected}>${codeEntry.value.meaning}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<button type="submit">确定</button>
				<button type="reset">重置</button>
				<button type="button" onclick="location='userList.jsp'">取消</button>
			</td>
		</tr>
	</table>
</form>
</center>
</body>
</html>