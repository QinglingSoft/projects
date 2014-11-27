<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<spring:useBean id="articleHelper" beanName="articleHelper" />
<jsp:setProperty name="articleHelper" property="articleId" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>撰写新闻</title>
	<link href="../css/default.css" rel="stylesheet" type="text/css" />
 	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".richEditor").ckeditor();
		});
	</script>
	<style type="text/css">
		body {
			padding: 4em;
		}
		input {
			display: block;
			margin-bottom: 1em;
		}
		label {
			margin-top: 1em;
		}
		label:after {
			content: "：";
		}
	</style>
</head>
<body>
<form action="addNews.action" method="post">
<input type="hidden" name="article.type" value="NEWS"/>
<label>新闻标题</label>
<input name="article.title" size="60" maxlength="60" />
<label>内容</label>
<textarea class="richEditor" name="article.content"></textarea>
<button type="submit">保存</button>
<button type="button" onclick="location='newsList.jsp'">取消</button>
</form>
</body>
</html>