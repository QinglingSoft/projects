$(function() {

	//在cookie选择的图层前打钩
	var layersStr = $.cookie('mapLayers');
	if (layersStr.match(/".*"/)) {
		layersStr = eval(layersStr);
	}
	if (layersStr) {
		var layerIds = layersStr.split(",");
		$("#mapLayerSelectForm input[name=layers]").each(function(i, input) {
			var inputValue = $(input).attr("value");
			if ($.inArray(inputValue, layerIds) != -1) {
				$(input).attr("checked", true);
			}
		});
	}
	
	//图层选项改变时影响cookie
	$("#mapLayerSelectForm input").click(function() {
		var layersStr = $("[name=layers]", this.form).fieldValue().join(",");
		
		$.cookie("mapLayers", layersStr);
		
		var workspaceFrame = window.top.frames["workspace"];
		if (workspaceFrame) {
			if (workspaceFrame.selectLayers) {
				workspaceFrame.selectLayers(layersStr);
			}
		}
	});
});