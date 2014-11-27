<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="x-ua-compatible" content="ie=8" /> 
	<base target="qlDetail" />
	<title>桥梁信息框架</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/qlFrame.css" rel="stylesheet" type="text/css" />
 	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("div.tab-pane")
				.css("top", $("div.tab-label").outerHeight() + 2)
				.resize(function() {
					$("#qlDetail").height(($(this).innerHeight() - 2) + 'px');
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
		<c:url var="jibenUrl" value="catalogFrame.jsp">
			<c:param name="dataTableName" value="T_QL" />
			<c:param name="catalog" value="桥梁概况" />
			<c:param name="primaryKeys.QLBM" value="${param.QLBM}"/>
		</c:url>
		<li class="selected"><a href="${jibenUrl}">桥梁概况</a></li>
		
		<c:url var="binghaiUrl" value="qlBinghaiTreeFrame.jsp">
			<c:param name="QLBM" value="${param.QLBM}"/>
		</c:url>
		<li><a href="${binghaiUrl}">病害记录</a></li>
		
		<c:url var="pingdingUrl" value="qlPingdingTreeFrame.jsp">
			<c:param name="QLBM" value="${param.QLBM}"/>
		</c:url>
		<li><a href="${pingdingUrl}">综合评定</a></li>
		
	</ul>
</div>
<div class="tab-pane"><iframe id="qlDetail" name="qlDetail" src="${jibenUrl}" frameborder="0" style="width: 100%"></iframe></div>
</body>
</html>