<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>桥梁病害记录树框架</title>
 	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		function treeItemSelected(params) {
			var url = "tableDetail.jsp?" + $.param(params);
			$("#tableDetail").attr("src", url);
		}
	</script>
</head>
<frameset rows="40%, 60%">
	<c:url var="binghaiTreeUrl" value="qlBinghaiTree.jsp">
		<c:param name="QLBM" value="${param.QLBM}" />
	</c:url>
    <frame name="binghaiTree" src="${binghaiTreeUrl}" />
    
    <frame id="tableDetail" name="tableDetail" />
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
    </body>
    </noframes>
</frameset>
</html>