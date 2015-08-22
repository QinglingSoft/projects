<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link type="text/css" href="css/tableDetail.css" rel="stylesheet" />	
	<link type="text/css" href="css/record.css" rel="stylesheet" />	
	<title>${dataTable.label} 详情</title>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript">
	$(function(){
		if("${param.tablename}"!=""){
			showMap("${param.tablename}","${param.id}");
		}
	});
	//调用地图定义方法
	function showMap(tablename,id){
		//alert(tablename+"----------"+id);
	}
	//调用属性
	function showAttribute() {
		if($("#tableName").val()==""||$("#tableId").val()==""){
			alert("不能为空");
			return false;
		}
		window.parent.showAttribute($("#tableName").val(),$("#tableId").val());  
    }
	</script>
</head>
<body>
	<!-- <center>
	TableName:<input type="text" id="tableName"/>
	ID:<input type="text" id="tableId"/>
	<button type="submit" onclick="showAttribute()" >查看属性</button></center> -->
</body>
</html>