$(function(){
	$(".deleteButton").live("click", function(){
		var $li = $(this).parents("li"); 
		if (!confirm("确定删除" + "（" + $(this).attr("brief") + "）？")) {
			return;
		}
		var pkJson = $li.attr("primaryKeyValues");
		var primaryKeyValues = eval(pkJson);
		var params = {dataTableName: $li.attr("dataTableName")};
		for (var pkName in primaryKeyValues) {
			params["primaryKeys." + pkName] = primaryKeyValues[pkName];
		}
		$.getJSON("deleteData.action", params, function(jsonResult) {
			if (jsonResult.success) {
				
				//去掉已被删除的记录的列表元素
				$li.remove();
				
				//如已无记录，则一对一表的“新增”按钮显现
				if ($(".childTableData > li").length == 0) {
					$("#addButton").show();
				}
			}
		});
		return false;
	});
});
