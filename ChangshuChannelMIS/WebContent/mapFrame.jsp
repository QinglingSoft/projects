<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/functions" prefix="wfn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 
<title>地图分割框架</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/mapFrame.js"></script>
</head>
<c:choose>
	<c:when test="${not empty param.QLBM}">
		<c:set var="cols" value="*, 30%"/>
		<c:set var="mapUrl" value="/YZGL/mapload.jsp?QLBM=${param.QLBM}&LayerID=${cookie.mapLayers.value}" />
		<c:url var="qlUrl" value="qlFrame.jsp">
			<c:param name="QLBM" value="${param.QLBM}" />
		</c:url>
	</c:when>
	<c:otherwise>
		<c:set var="cols" value="*, 0"/>
		<c:set var="mapUrl" value="/YZGL/mapload.jsp?LayerID=${cookie.mapLayers.value}" />
	</c:otherwise>
</c:choose>
<frameset id="rootFrameset" cols="${cols}">
    <frame id="map" name="map" src="${mapUrl}" />
    <frame id="ql" name="ql" src="${qlUrl}" />
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
    </body>
    </noframes>
</frameset>
</html>