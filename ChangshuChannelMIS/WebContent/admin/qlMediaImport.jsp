<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>导入桥梁图片</title>
</head>
<body>
<div class="help">
	<p>桥梁图片数据包应为一zip格式的压缩包文件，其中只可包含图片文件，不应有目录结构。</p>
	<p>每一个图片文件应以如下格式命名：桥梁编码_采集日期（yyyymmdd格式）_名称_说明.jpg。</p>
</div>
<fieldset>
	<legend>导入桥梁图片</legend>
	<form action="qlMediaImport.action" method="post" enctype="multipart/form-data">
		<input type="file" name="qlMediaPack" />
		<button type="submit">导入</button>
	</form>
</fieldset>
</body>
</html>