$(function() {
	function updateTips(t) {
		var tips = $(".validateTips");
		tips
			.text(t)
			.addClass('ui-state-highlight');
		setTimeout(function() {
			tips.removeClass('ui-state-highlight', 1500);
		}, 500);
	}
	$("#addDialog").dialog({
		autoOpen: false,
		width: 170,
		modal: true,
		open: function(event, ui) {
			if ($("#addDialog form input[type!=hidden]").length + $("#addDialog form select").length + $("#addDialog form textarea").length == 0) {
				updateTips("新增记录");
			}
		},
		buttons: {
			'新增': function() {
				var bValid = true;
				$(this).children().removeClass('ui-state-error');
				$(this).children("form").ajaxSubmit({
					dataType: 'json',
					success: function(jsonResult) {
					
						//非一对多的子表隐藏“新增”按钮
						$("#addButton[dataTableIsMulti=false]").hide();
					
						//动态绘制新增加的记录框
						if (jsonResult.success) {
							var params = new Object();
							var newRecordIndex = jsonResult.object;
							params.dataTableName = newRecordIndex.dataTableName;
							var primaryKeyValues = newRecordIndex.primaryKeyValues;
							for (var pkName in primaryKeyValues) {
								params["primaryKeys." + pkName] = primaryKeyValues[pkName];
							}
							var pkJson = "";
							for (var pkName in primaryKeyValues) {
								if (pkJson) {
									pkJson += ", ";
								}
								pkJson += pkName;
								pkJson += ": '";
								pkJson += primaryKeyValues[pkName];
								pkJson += "'";
							}
							pkJson = "({" + pkJson + "})";
							var $newLi = $("<li></li>");
							$newLi
								.attr("dataTableName", newRecordIndex.dataTableName)
								.attr("primaryKeyValues", pkJson)
								.appendTo($(".childTableData"))
								.load("ajax/record.jsp", params, function() {
									window.scrollTo($newLi.offset().left, $newLi.offset().top - $(".parentBrief").outerHeight() - 2);
									$("form.fileTypeFieldForm").submit(fileTypeFieldFormSubmit);
								});
						} else {
							updateTips("失败：" + jsonResult.errorMessage);
						}
					}
				});
				$(this).dialog('close');
			},
			'取消': function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			$("#addDialog input").removeClass('ui-state-error');
			$("#addDialog textarea").removeClass('ui-state-error');
			$("#addDialog form").clearForm();
		}
	});
	$('#addButton')
		.button()
		.click(function() {
			$('#addDialog').dialog('open');
		});
});