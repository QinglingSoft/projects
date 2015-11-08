$(function(){
	$("ul.briefTree .brief").live('contextmenu', function(event) {
		if($("#rightClickMenu").attr("permission")=="true"){
			var $selfTreeLi = $(this).parent("li");
			$("ul.briefTree li").removeClass("rightClicked");
			$selfTreeLi.addClass("rightClicked");
			var params = buildAjaxParams($selfTreeLi);
			$("#rightClickMenu")
				.offset({
					top: event.pageY,
				 	left: event.pageX
				 })
			 	.empty()
			 	.load("ajax/treeRightClickMenu.jsp", params)
			 	.show();
		 	roadBridge.$rightClickedElement = $selfTreeLi;
		 	event.preventDefault();
		}
	});
	
	$(document).click(function(){
		$("ul.briefTree li").removeClass("rightClicked");
		$("#rightClickMenu").offset({
			top: 0,
		 	left: 0
		 }).hide();
	});
	
	$("li.adder").live("click", function() {
		var $addDialog = $("div#addDialog");
		$addDialog.attr("title", "增加" + $(this).attr("dataTableLabel"));
		var params = {dataTableName: $(this).attr("dataTableName")};
		var parentPkJson = $(this).attr("parentPrimaryKeyValues")
		var parentPrimaryKeyValues = eval(parentPkJson);
		for (var parentPkName in parentPrimaryKeyValues) {
			params["parentPrimaryKeys." + parentPkName] = parentPrimaryKeyValues[parentPkName];
		}
		$addDialog.load("ajax/addDialog.jsp", params, function() {
			$addDialog.dialog('open');
		});
	});
});