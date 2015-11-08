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
					type: "POST",
					success: function(jsonResult) {
						if (jsonResult.success) {
							var xpoint,ypoint,nameStr,idStr;
							nameStr = $("#addDialog form input[name=dataTableName]").val();
							if(nameStr=="T_HDDM"||nameStr=="T_SNCZ"){
								xpoint = $("#addDialog form input[name=values.DMZXX]").val();
								ypoint = $("#addDialog form input[name=values.DMZXY]").val();
							}else if(nameStr=="T_SN"){
								xpoint = $("#addDialog form input[name=values.SNQDX]").val();
								ypoint = $("#addDialog form input[name=values.SNQDY]").val();
							}else if(nameStr=="T_HB"){
								xpoint = $("#addDialog form input[name=values.HBSBX]").val();
								ypoint = $("#addDialog form input[name=values.HBSBY]").val();
							}else if(nameStr=="T_HDBP"){
								xpoint = $("#addDialog form input[name=values.BPX]").val();
								ypoint = $("#addDialog form input[name=values.BPY]").val();
							}else if(nameStr=="T_SSGK"||nameStr=="T_SSCC"||nameStr=="T_SSJYZ"||nameStr=="T_SSHA"||nameStr=="T_SSMT"||nameStr=="T_LHSS"){
								xpoint = $("#addDialog form input[name=values.ZZWQWZX]").val();
								ypoint = $("#addDialog form input[name=values.ZZWQWZY]").val();
							}else if(nameStr=="T_MD"){
								xpoint = $("#addDialog form input[name=values.MDWQWZX]").val();
								ypoint = $("#addDialog form input[name=values.MDWQWZY]").val();
							}else if(nameStr=="T_GHQL"){
								xpoint = $("#addDialog form input[name=values.SSGHJZWX]").val();
								ypoint = $("#addDialog form input[name=values.SSGHJZWY]").val();
							}else if(nameStr=="T_DC"||nameStr=="T_SSGHW"||nameStr=="T_SXGHW"||nameStr=="T_SD"){
								xpoint = $("#addDialog form input[name=values.SSGHJZWX]").val();
								ypoint = $("#addDialog form input[name=values.SSGHJZWY]").val();
							}
							
							var primaryKeyValues = eval(jsonResult.object.primaryKeyValues);
							for (var pkName in primaryKeyValues) {
								idStr = primaryKeyValues[pkName];
							}
							//调用
							if(xpoint!=undefined&&ypoint!=undefined){
								AddPointMap(nameStr,idStr,xpoint,ypoint);
							}
							
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