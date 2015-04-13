<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<spring:useBean id="tableDataHelper" beanName="tableDataHelper" scope="request" />
<jsp:setProperty name="tableDataHelper" property="dataTableName" />
<c:forEach items="${dataTable.primaryKeys}" var="pk">
	<c:set var="paramName" value="primaryKeys.${pk.name}" />
	<c:set target="${tableDataHelper.primaryKeys}" property="${pk.name}" value="${param[paramName]}" />
</c:forEach>
<c:set var="data" value="${tableDataHelper.data}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
	<link type="text/css" href="css/tableDetail.css" rel="stylesheet" />	
	<link type="text/css" href="css/record.css" rel="stylesheet" />	
	<link rel="stylesheet" href="js/highslide/highslide.css" type="text/css" media="screen" />
	<title>${dataTable.label} 详情</title>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript"  src="js/highslide/highslide-full.js"></script>
	<c:set var="primaryKeysJson">
		{
		<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
			<c:if test="${not status.first}">, </c:if>
			"${pk.name}": "${data[pk.name]}"
		</c:forEach>
		}
	</c:set>
	<script type="text/javascript">
		var primaryKeys = ${primaryKeysJson};
		var dataTableName = "${dataTable.name}";
		var dataTableLabel = "${dataTable.label}";
		function updateFieldValue() {
			if ($(this).val() == $(this).siblings("input[type=hidden]").val()) {
				$(this).removeClass("changed");
				return;
			}
			var params = {dataTableName: dataTableName};
			params.fieldName = $(this).attr("name");
			params.fieldValue = $(this).val();
			for (var pkName in primaryKeys) {
				params["primaryKeys." + pkName] = primaryKeys[pkName];
			}
			$(this).attr("disabled", "disabled").addClass("updating");
			var $sourceElement = $(this);
			$.ajax({
				url:"updateFieldValue.action",
				data: params,
				type: "POST",
				dataType: "json",
				success: function(jsonResult, textStatus) {
					$sourceElement.removeClass("updating").removeAttr("disabled");
					if (!jsonResult.success) {
						alert(jsonResult.errorMessage);
						$sourceElement.focus();
						$sourceElement.select();
						return;
					}
					
					var updateResult = jsonResult.object;
					if (updateResult == null) {
						updateResult = "";
					}
					$sourceElement
						.val(updateResult)
						.removeClass("changed")
						.siblings("input[type=hidden]")
							.val(updateResult);
					$sourceElement.addClass("updated");
					window.setTimeout(function() {
						$sourceElement.removeClass("updated");
					}, 1000);
				}
			});
		}
		function valueChanged() {
			$(this).addClass("changed");
		}
		
		function loadFile(){
			var params = {dataTableName: "T_MEDIA", fieldName: "MEDIACONTEXT"};
			for (var pkName in primaryKeys) {
				params["OBJECTID"] = primaryKeys[pkName];
			}
			params["OBJECTTABLENAME"] = dataTableName;
			
			$(".preview").load("ajax/filePreview.jsp", params);
		}
		
		function fileTypeFieldFormSubmit() {
			var $sourceForm = $(this);
			$sourceForm.ajaxSubmit({
				dataType: "json",
				complete: function(jsonResult, textStatus) {
					if (jsonResult.status==200) {
						loadFile();
						$(".fileTypeFieldForm input[type=file]").val("");
					} else {
						alert(jsonResult.errorMessage);
					}
				}
			});
			return false;
		}
		
		function deleteSelected(pkJson) {
			var primaryKeyValues = eval(pkJson);
			var params = {dataTableName: "T_MEDIA"};
			for (var pkName in primaryKeyValues) {
				params["primaryKeys." + pkName] = primaryKeyValues[pkName];
			}
			$.ajax({
				url:"deleteData.action",
				data: params,
				type: "POST",
				dataType: "json",
				success: function(jsonResult, textStatus) {
					loadFile();
				}
			});
			
		}
		$(function(){
			$(".parentBrief").resize(function(){
				$(".childTableData").css("margin-top", ($(this).outerHeight() + 2) + 'px');
			}).resize();
			$(".tableData input[type!=file][class!=Wdate]").live("keypress", valueChanged).live("blur", updateFieldValue);
			$(".tableData select").live("keypress", valueChanged).live("change", valueChanged).live("blur", updateFieldValue);
			$(".tableData textarea").live("keypress", valueChanged).live("blur", updateFieldValue);
			$(".tableData input.Wdate").live("change", valueChanged);
			$(".tableData input.Wdate + button").live("click", function() {
				updateFieldValue.call($(this).prev());
			});
			$(".fileTypeFieldForm input[type=file]").live("change", valueChanged);
			$("form.fileTypeFieldForm").submit(fileTypeFieldFormSubmit);
			loadFile();
		});
		
		hs.graphicsDir = 'js/highslide/graphics/';
		hs.align = 'center';
		hs.transitions = ['expand', 'crossfade'];
		hs.outlineType = 'rounded-white';
		hs.fadeInOut = true;
		hs.numberPosition = 'caption';
		hs.dimmingOpacity = 0.75;
		hs.wrapperClassName = 'draggable-header';
		
		// Add the controlbar
		if (hs.addSlideshow) {
		    hs.addSlideshow({
				//slideshowGroup: 'group1',
				interval: 5000,
				repeat: false,
				useControls: true,
				fixedControls: 'fit',
				overlayOptions: {
				opacity: .75,
				position: 'bottom center',
				hideOnMouseOut: true
			}
		});
		}
	</script>	
</head>
<body>
	<div class="title ui-widget-header">${dataTable.label}</div>
	<c:choose>
		<c:when test="${param.flag == '0'}">
			<data:recordEven data="${data}" dataTable="${dataTable}" />
		</c:when>
		<c:otherwise>
			<data:record data="${data}" dataTable="${dataTable}" />
		</c:otherwise>
	</c:choose>
	<c:if test="${dataTable.name != 'T_HD'}"><!--T_HD航道主键id为string非int类型不能保存-->
		<form class="fileTypeFieldForm" action="addFileTypeField.action" method="post" enctype="multipart/form-data">
	        <input type="hidden" name="dataTableName" value="${dataTable.name}" />
			<c:forEach items="${dataTable.primaryKeys}" var="pk">
				<input type="hidden" name="primaryKeys.${pk.name}" value="${data[pk.name]}" />
			</c:forEach>
			<input name="upload" type="file" />
			<button type="submit">√</button>
	    </form>
	    <div class="preview" style="margin-top:5px;">
		</div>
	</c:if>
</body>
</html>