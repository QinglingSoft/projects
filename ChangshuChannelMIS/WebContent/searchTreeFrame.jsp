<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${dataTable.label}</title>
 	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		function briefSelected(parentPks) {
			var params = {dataTableName: "${dataTable.name}"};
			    params["catalog"] = "${dataTable.catalog}";
			    params["keyIds"] = parentPks;
			$("#bridgeTreeList").attr("src", "oneBriefList.jsp?" + $.param(params));
		}
		
		function treeItemSelected(params) {
			params["flag"] = "0";  // 属性页面单双列标示，0双列
			var url = "tableDetail.jsp?" + $.param(params);
			$("#tableDetail").attr("src", url);
		}
		function resetDetail() {
			$("#tableDetail").attr("src", "");
		}
	</script>
</head>
<frameset cols="35%, 65%">
	<c:url var="briefListUrl" value="oneBriefList.jsp">
		<c:param name="dataTableName" value="${dataTable.name}"/>
		<c:param name="catalog" value="${param.catalog}"/>
	</c:url>
    <frame id="bridgeTreeList" src="${briefListUrl}" />
    <frame id="tableDetail" name="detail" src="" />
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
    </body>
    </noframes>
</frameset>
</html>