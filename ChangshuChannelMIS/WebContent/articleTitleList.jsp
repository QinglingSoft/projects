<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:useBean id="publishedArticleHelper" beanName="publishedArticleHelper" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="article" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新闻通知标题列表</title>
<style type="text/css">
	html, body {
		width: 100%;
		height: 100%;
		margin: 0;
		padding: 0;
	}
	table {
		width: 95%;
		border-collapse: collapse;
		margin: 0;
	}
	td {
		width: 50%;
	}
	fieldset {
		min-height: 10em;
		border: solid #4190cd 2px;
	}
	legend {
		font-weight: bold;
		font-style: italic;
		margin-left: 1em;
	}
</style>
</head>
<body>
<center>
<table>
	<tr>
		<td>
			<fieldset>
				<legend>新闻</legend>
				<ul>
					<c:forEach items="${publishedArticleHelper.news}" var="news">
						<c:url var="viewUrl" value="viewArticle.jsp">
							<c:param name="articleId" value="${news.id}"/>
						</c:url>
						<li><a href="${viewUrl}">${news.title}</a>(<fmt:formatDate value="${news.publishTime}" type="DATE"/>)</li>
					</c:forEach>
				</ul>
			</fieldset>
		</td>
		<td>
			<fieldset>
				<legend>通知</legend>
				<ul>
					<c:forEach items="${publishedArticleHelper.notices}" var="notice">
						<c:url var="viewUrl" value="viewArticle.jsp">
							<c:param name="articleId" value="${notice.id}"/>
						</c:url>
						<li><a href="${viewUrl}">${notice.title}</a>(<fmt:formatDate value="${notice.publishTime}" type="BOTH"/>)</li>
					</c:forEach>
				</ul>
			</fieldset>
		</td>
	</tr>
</table>
</center>
</body>
</html>