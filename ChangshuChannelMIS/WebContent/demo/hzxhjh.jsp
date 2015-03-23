<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<link href="../css/briefList.css" rel="stylesheet" type="text/css" />
<link href="../css/generalSearch.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	table.briefList th {
		text-align: center;
	}
	table.headerInfo {
		min-width: 100%;
		border-collapse: collapse;
		border: none;
		margin-top: 1em;
	}
	table.headerInfo th {
		text-align: center;
		padding:2px;
	}
	table.headerInfo input {
		width:50px;
	}
	select {
		width: 146px;
	}
</style>
</head>
<body>
	<div class="normalSearch content">
		<ul>
			<li>
				<label>时间:</label>
				<input />
			</li>
			<li>
				<button>查询</button>
			</li>
			<li>
				<button>导出</button>
			</li>
		</ul>
	</div>
	<table  class="headerInfo">
		<tr>
			<th>航政巡航计划（2015年4月份）</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th rowspan="2">日期</th>
				<th colspan="5">巡航航道</th>
			</tr>
			<tr>
				<th>苏ECD385标致车</th>
				<th>苏航政E207</th>
				<th>苏航政E101</th>
				<th>江苏航政144</th>
				<th>江苏航政404</th>
			</tr>
		</thead>
		<tbody>
		<% for(int i=1;i<31;i++){ %>
			<tr>
				<td><%=i%></td>
				<td>常浒线 </td>
				<td>申张线</td>
				<td>望虞河</td>
				<td>苏虞线</td>
				<td>锡虞线</td>
			</tr>
			<% } %>
		</tbody>
	</table>
</body>
</html>