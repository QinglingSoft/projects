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
		width: 180,
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
						if (jsonResult.success) {
							var $sourceLi = roadBridge.$rightClickedElement;
							$sourceLi.addClass("expanded");
							loadSubTree($sourceLi);
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
});