function updateFieldValue() {
	if ($(this).val() == $(this).siblings("input[type=hidden]").val()) {
		$(this).removeClass("changed");
		return;
	}
	var $li = $(this).parents("li");
	var params = {dataTableName: $li.attr("dataTableName")};
	var pkJson = $li.attr("primaryKeyValues");
	var primaryKeyValues = eval(pkJson);
	for (var pkName in primaryKeyValues) {
		params["primaryKeys." + pkName] = primaryKeyValues[pkName];
	}
	params.fieldName = $(this).attr("name");
	params.fieldValue = $(this).val();
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
function fileTypeFieldFormSubmit() {
	var $sourceForm = $(this);
	$sourceForm.ajaxSubmit({
		dataType: "json",
		success: function(jsonResult, textStatus) {
			if (jsonResult.success) {
				var fieldIndex= jsonResult.object;
				var params = {dataTableName: fieldIndex.dataTableName, fieldName: fieldIndex.fieldName};
				for (var pkName in fieldIndex.primaryKeyValues) {
					params["primaryKeys." + pkName] = fieldIndex.primaryKeyValues[pkName];
				}
				$sourceForm
					.siblings(".preview")
						.load("ajax/fileTypeFieldPreview.jsp", params);
				$sourceForm.resetForm().children("input").removeClass("changed");
			} else {
				alert(jsonResult.errorMessage);
			}
		}
	});
	return false;
}
$(function(){
	$(".parentBrief").resize(function(){
		$(".childTableData").css("margin-top", ($(this).outerHeight() + 2) + 'px');
	}).resize();
	$(".childTableData input[type!=file][class!=Wdate]").live("keypress", valueChanged).live("blur", updateFieldValue);
	$(".childTableData select").live("keypress", valueChanged).live("change", valueChanged).live("blur", updateFieldValue);
	$(".childTableData textarea").live("keypress", valueChanged).live("blur", updateFieldValue);
	$(".tableData input.Wdate").live("change", valueChanged);
	$(".tableData input.Wdate + button").live("click", function() {
		updateFieldValue.call($(this).prev());
	});
	$(".childTableData input[type=file]").live("change",valueChanged);
	$("form.fileTypeFieldForm").submit(fileTypeFieldFormSubmit);
	$(".childTableData>li>.statusIcon").live("click", function(){$(this).parent("li").toggleClass("expanded");});
	var maxLabelWidth = 0;
	$(".parentBrief .label").each(function() {
		var thisWidth = $(this).width();
		if (maxLabelWidth < thisWidth) {
			maxLabelWidth = thisWidth;
		}
	});
	$(".parentBrief .label").width(maxLabelWidth);
	var maxValueWidth = 0;
	$(".parentBrief .value").each(function() {
		var thisWidth = $(this).width();
		if (maxValueWidth < thisWidth) {
			maxValueWidth = thisWidth;
		}
	});
	$(".parentBrief .value").width(maxValueWidth);
});