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
		function briefSelected(primaryKeyValues) {
			var params = {dataTableName: "${dataTable.name}"};
			for (var pkName in primaryKeyValues) {
				params["primaryKeys." + pkName] = primaryKeyValues[pkName];
			}
			$("#detail").attr("src", "tableDetail.jsp?" + $.param(params));
		}
		
		function deleteSelected(primaryKeyValues) {
			var params = {dataTableName: "${dataTable.name}"};
			for (var pkName in primaryKeyValues) {
				params["primaryKeys." + pkName] = primaryKeyValues[pkName];
			}
			
			$.ajax({
				url:"deleteData.action",
				data: params,
				type: "POST",
				dataType: "json",
				success: function(jsonResult, textStatus) {
					if (!jsonResult.success) {
						alert(jsonResult.errorMessage);
						return;
					}
					window.setTimeout(function() {
						window.frames["briefList"].location.reload();
					}, 1000);
				}
			});
			
		}
		
		function newRootDataAdded(primaryKeyValues) {
			var params = {dataTableName: "${dataTable.name}"};
			for (var pkName in primaryKeyValues) {
				params["primaryKeys." + pkName] = primaryKeyValues[pkName];
			}
			$("#detail").attr("src", "tableDetail.jsp?" + $.param(params));
			
			window.frames["briefList"].location.reload();
		}
		
		function addRootData() {
			$("#detail").attr("src", "newRootData.jsp?dataTableName=${dataTable.name}");
		}
	</script>
</head>
<frameset cols="75%, 25%">
	<c:url var="briefListUrl" value="briefList.jsp">
		<c:param name="dataTableName" value="${dataTable.name}"/>
	</c:url>
    <frame name="briefList" src="${briefListUrl}" />
    <frame id="detail" name="detail" src="" />
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
    </body>
    </noframes>
</frameset>
</html>