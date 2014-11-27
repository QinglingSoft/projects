<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>权限不足</title>
<script type="text/javascript">
<!--
	function jumpToLogin() {
		window.top.location.href = '<c:url value="/login.jsp"/>';
	}
//-->
</script>
</head>
<body style="text-align: center">
对于该操作，您的权限不足。请使用拥有该权限的帐号登录。<br/>
<button type="button" onclick="jumpToLogin()">变更登录</button>
</body>
</html>