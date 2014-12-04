<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/functions" prefix="wfn" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>新增 ${dataTable.label}</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/formDialog.css" rel="stylesheet" type="text/css" />
	<style type="css">
		p {
			margin: .2em 0;
			padding: 0;
		}
		ul {
			list-style: none;
			margin: 0;
			padding: 0;
		}
		label {
			font-weight: bold;
			white-space: nowrap;
		}
		input[type=text],
		textarea {
			width: 140px;
		}
		select {
			width: 146px;
		}
	</style>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	function updateTips(t) {
		var tips = $(".validateTips");
		tips
			.text(t)
			.addClass('ui-state-highlight');
		setTimeout(function() {
			tips.removeClass('ui-state-highlight', 1500);
		}, 500);
	}
	$(function(){
		if ($("#addDialog form input[type!=hidden]").length + $("#addDialog form select").length + $("#addDialog form textarea").length == 0) {
			updateTips("新增记录");
		}
		
		$("#newRootDataForm").submit(function(){
			$(this).children().removeClass('ui-state-error');
			$(this).ajaxSubmit({
				dataType: 'json',
				success: function(jsonResult) {
					if (jsonResult.success) {
						window.parent.newRootDataAdded(jsonResult.object.primaryKeyValues);
					} else {
						updateTips("失败：" + jsonResult.errorMessage);
					}
				}
			});
			return false;
		});

	});
	</script>
</head>
<body>
<p class="validateTips">下列字段必须填写</p>
<form id="newRootDataForm" action="addData.action">
	<input type="hidden" name="dataTableName" value="${dataTable.name}"/>
	<ul>
		<c:forEach items="${dataTable.fields}" var="field">
			<c:if test="${not field.nullable and not field.generatedByDatabase}">
				<li>
					<label for="values.${field.name}">${field.label}</label>
					<c:choose>
						<c:when test="${field.type == 'FILE'}">
							不支持文件类型内容
						</c:when>
						<c:when test="${field.type == 'NUMBER'}">
							<input name="values.${field.name}" value="${value}" ${readonly} />
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:when>
						<c:when test="${field.type == 'DATE'}">
							<input class="Wdate" name="values.${field.name}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
						</c:when>
						<c:when test="${field.type == 'DATETIME'}">
							<input class="Wdate" name="values.${field.name}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						</c:when>
						<c:when test="${field.type == 'CODE'}">
							<select name="values.${field.name}">
								<c:forEach items="${field.codeTable.codes}" var="code">
									<option value="${code.key}">${code.value.meaning}</option>
								</c:forEach>
							</select>
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:when>
						<c:when test="${field.type == 'TEXT'}">
							<textarea name="values.${field.name}" rows="3"></textarea>
						</c:when>
						<c:otherwise>
							<input name="values.${field.name}" maxlength="${field.length}" value="${value}" ${readonly} />
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:otherwise>
					</c:choose>
				</li>
			</c:if>
		</c:forEach>
	</ul>
	<button type="submit">确认</button>
</form>
</body>
</html>