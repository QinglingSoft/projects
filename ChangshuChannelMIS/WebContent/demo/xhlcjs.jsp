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
			<th>常熟市航道处2015年巡航里程计算</th>
		</tr>
	</table>
	<table class="briefList">
		<thead class="ui-widget-header">
			<tr>
				<th>航道名称</th>
				<th>巡航车船</th>
				<th>&nbsp;</th>
				<th>次数</th>
				<th>里程</th>
				<th>计划巡航里程</th>
				<th>实际往返巡航里程</th>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>Km</th>
				<th>&nbsp;</th>
				<th>Km</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>常浒线</td>
				<td>苏ECD385标致车</td>
				<td>4/4</td>
				<td>8</td>
				<td>26.38</td>
				<td>211.04</td>
				<td>422.08</td>
			</tr>
			<tr>
				<td>申张线</td>
				<td>苏航政E207</td>
				<td>4/4</td>
				<td>8</td>
				<td>26.38</td>
				<td>211.04</td>
				<td>422.08</td>
			</tr>
			<tr>
				<td>望虞河</td>
				<td>苏航政E101</td>
				<td>4/4</td>
				<td>8</td>
				<td>26.38</td>
				<td>211.04</td>
				<td>422.08</td>
			</tr>
			<tr>
				<td>苏虞线</td>
				<td>江苏航政144</td>
				<td>4/4</td>
				<td>8</td>
				<td>26.38</td>
				<td>211.04</td>
				<td>422.08</td>
			</tr>
			<tr>
				<td>锡虞线</td>
				<td>江苏航政404</td>
				<td>4/4</td>
				<td>8</td>
				<td>26.38</td>
				<td>211.04</td>
				<td>422.08</td>
			</tr>
			<tr>
				<td align="center" colspan="5">每月巡航里程</td>
				<td>1384.83</td>
				<td>42769.66</td>
			</tr>
			<tr>
				<td align="center" colspan="5">巡航总里程</td>
				<td>216617.96</td>
				<td>33235.92</td>
			</tr>
		</tbody>
	</table>
</body>
</html>