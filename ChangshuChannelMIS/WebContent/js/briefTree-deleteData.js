$(function(){
	$("li.deleter").live("click", function() {
		if (!confirm("确定删除？")) {
			return;
		}
		$sourceLi = roadBridge.$rightClickedElement;
		var pkJson = $sourceLi.attr("primaryKeyValues");
		var primaryKeyValues = eval(pkJson);
		var params = {dataTableName: $sourceLi.attr("dataTableName")};
		for (var pkName in primaryKeyValues) {
			params["primaryKeys." + pkName] = primaryKeyValues[pkName];
		}
		$.getJSON("deleteData.action", params, function(jsonResult) {
			if (jsonResult.success) {
				$sourceLi.remove();
			}
		});
	});
});
