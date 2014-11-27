function buildAjaxParams($hostElement) {
	var params = {};
	params.catalog = $hostElement.attr("catalog");
	params.dataTableName = $hostElement.attr("dataTableName");
	var pkJson = $hostElement.attr("primaryKeyValues");
	var primaryKeyValues = eval(pkJson);
	for (var pkName in primaryKeyValues) {
		params["primaryKeys." + pkName] = primaryKeyValues[pkName];
	}
	return params;
}
function loadSubTree($selfTreeLi) {
	$selfTreeLi.children("ul").remove();
	var params = buildAjaxParams($selfTreeLi);
	var $newUl = $("<ul class='loading'></ul>");
	$newUl.appendTo($selfTreeLi).load("ajax/briefSubtree.jsp", params, function() {
		$newUl.removeClass("loading");
	});
}
$(function(){
	$("ul.briefTree .expander").live("click", function() {
		var $selfTreeLi = $(this).parent("li");
		$selfTreeLi.toggleClass("expanded");
		if ($selfTreeLi.hasClass("expanded")) {
			loadSubTree($selfTreeLi);
		}
	});
	$("ul.briefTree .brief").live("click", function() {
		var $selfTreeLi = $(this).parent("li");
		var params = buildAjaxParams($selfTreeLi);
		window.parent.treeItemSelected(params);
		$("ul.briefTree li").removeClass("selected");
		$selfTreeLi.addClass("selected");
	});
});