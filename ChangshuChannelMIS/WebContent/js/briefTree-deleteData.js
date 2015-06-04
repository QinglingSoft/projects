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
		$.ajax({
			url:"deleteData.action",
			data: params,
			type: "POST",
			dataType: "json",
			success: function(jsonResult, textStatus) {
				if (!jsonResult.success) {
					alert(jsonResult.errorMessage);
				}else{
					$sourceLi.remove();
					window.parent.resetDetail();
				}
			}
		});
//		$.getJSON("deleteData.action", params, function(jsonResult) {
//			if (jsonResult.success) {
//				$sourceLi.remove();
//			}
//		});
	});
});
