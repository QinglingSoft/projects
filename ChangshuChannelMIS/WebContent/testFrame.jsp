<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/functions" prefix="wfn" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>

<spring:useBean id="mapLayerHelper" beanName="mapLayerHelper" scope="request" />
<c:set var="mapLayerList" value="${mapLayerHelper.all}"  scope="request" />
<c:set var="tableNameIdsJson">
	{
	<c:forEach items="${mapLayerList}" var="mapLayer" varStatus="status">
			<c:if test="${not status.first}">, </c:if>
			"${mapLayer.tableName}": "${mapLayer.idName.trim()}"
	</c:forEach>
	}
</c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 
<title>地图分割框架</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/mapFrame.js"></script>
<script type="text/javascript">
	var idNamesJson =${tableNameIdsJson};
	//调用地图
	function showMap(tablename,id){
		var dingweiUrl = "mapTest.jsp?tablename=" + tablename + "&id=" + id;
		$("#map").attr("src", dingweiUrl);
	}
	//地图调用显示属性
	function showAttribute(tablename,id){
		var params = {};
		params.dataTableName = tablename;
		var idNames = eval(idNamesJson);
		for (var nameT in idNames) {
			if(tablename==nameT){
				params["primaryKeys." + idNames[nameT]]= id;
			}
		}
		params["flag"] = "0";  // 属性页面单双列标示，0双列
		var url = "tableDetail.jsp?" + $.param(params);
		$("#tableDetail").attr("src", url);
		$("#rootFrameset").attr("cols", "*, 30%");
	}
</script>
</head>
<frameset id="rootFrameset" cols="*, 0">
    <frame id="map" name="map" src="mapTest.jsp" />
    <frame id="tableDetail" name="tableDetail" src="" />
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
    </body>
    </noframes>
</frameset>
</html>