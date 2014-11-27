<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/form.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<title>修改密码</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
<!--
	$(function() {
		$("#changePasswordForm").submit(function() {
			var $newPassword = $("input[name=newPassword]", this);
			var $newPasswordConfirm = $("input[name=newPasswordConfirm]", this);
			if ($newPassword.val() == "") {
				alert("请填写新密码");
				$newPassword.focus();
				return false;
			} else if ($newPasswordConfirm.val() != $newPassword.val()) {
				alert("两次输入的新密码不一致，请检查");
				$newPasswordConfirm.focus();
				return false;
			}
			return true;
		});
		if ("${errorMessage}") {
			alert("${errorMessage}");
		}
	});
//-->
</script>
</head>
<body>
<div class="title ui-widget-header">修改密码</div>
<center>
<form id="changePasswordForm" action="changePassword.action" method="post">
<table class="form">
	<tr>
		<th>原密码：</th>
		<td><input type="password" name="oldPassword" /></td>
	</tr>
	<tr>
		<th>新密码：</th>
		<td><input type="password" name="newPassword" /></td>
	</tr>
	<tr>
		<th>新密码重复：</th>
		<td><input type="password" name="newPasswordConfirm" /></td>
	</tr>
	<tr>
		<th></th>
		<td>
			<button type="submit">确定</button>
		</td>
	</tr>
</table>
</form>
</center>
</body>
</html>