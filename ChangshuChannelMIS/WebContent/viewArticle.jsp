<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/list" prefix="list" %>
<spring:useBean id="articleHelper" beanName="articleHelper" />
<jsp:setProperty name="articleHelper" property="articleId" />
<c:set var="article" value="${articleHelper.article}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${article.title}</title>
	<style type="text/css">
		body {
			margin: 10px;
		}
	</style>
</head>
<body>
${article.content}
</body>
</html>