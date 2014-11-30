<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 
<link href="css/default.css" rel="stylesheet" type="text/css" />
<title>常熟市航道管理信息系统</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<style>
	body {
		background-image: url("images/loginBackground.jpg");
		background-position: center;
		background-repeat: no-repeat;
		min-height: 347px;
		min-width: 794px;
	}
	#loginForm {
		position: absolute;
		display: block;
		border: solid sliver 1px;
		background-color: white;
	}
	#loginForm>table {
		margin: 4px;
		border: solid sliver 2px;
	}
	th {
		text-align: right;
	}
	input {
		width: 12em;
	}
</style>
<script type="text/javascript">
	function fillGuestInfo() {
		$("#loginName").val("!guest");
		$("#password").val("123456");
	}
	function locateLoginForm() {
		var loginFormTop = (document.documentElement.clientHeight - $("#loginForm").height()) / 2;
		var loginFormRight = (document.documentElement.clientWidth - 794) / 2 + 30;
		$("#loginForm").css("top", loginFormTop);
		$("#loginForm").css("right", loginFormRight);
	}
	$(function() {
		locateLoginForm();
		$(window).resize(locateLoginForm);
	});
</script>
</head>
<body>
<form id="loginForm" action="login.action" method="post">
	<div class="errorMessage">${errorMessage}</div>
	<table>
		<tr>
			<th>登录名：</th>
			<td><input id="loginName" name="loginName" /></td>
		</tr>
		<tr>
			<th>密码：</th>
			<td><input type="password" id="password" name="password" /></td>
		</tr>
		<tr>
			<th></th>
			<td>
				<button type="submit">登录</button>
				<button type="submit" onclick="fillGuestInfo()">访客登录</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>