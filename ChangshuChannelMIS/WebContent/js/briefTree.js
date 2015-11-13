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

//递归加载子节点
function loadSubTrees($selfTreeLi,keyIds,count) {
	$selfTreeLi.children("ul").remove();
	var params = buildAjaxParams($selfTreeLi);
	var $newUl = $("<ul class='loading'></ul>");
	$newUl.appendTo($selfTreeLi).load("ajax/briefSubtree.jsp", params, function() {
		$newUl.removeClass("loading");
		if(keyIds.length>0){
			if(count<keyIds.length){
				var $selfTreeLi = $("#"+keyIds[count]);
				$selfTreeLi.toggleClass("expanded");
				if ($selfTreeLi.hasClass("expanded")) {
					loadSubTrees($selfTreeLi,keyIds,count+1);
				}
			}
			
		}
		
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
	
	//条件查询 递归加载子节点
	if($("#keyIds").val()!=null&&$("#keyIds").val()!=""){
		var keyIds = $("#keyIds").val().split(",");
		var $selfTreeLi = $("#"+keyIds[0]); 
		$selfTreeLi.toggleClass("expanded");
		if ($selfTreeLi.hasClass("expanded")) {
			loadSubTrees($selfTreeLi,keyIds,1);
		}
	}
	
	
	$("ul.briefTree .brief").live("click", function() {
		var $selfTreeLi = $(this).parent("li");
		var params = buildAjaxParams($selfTreeLi);
		window.parent.treeItemSelected(params);
		$("ul.briefTree li").removeClass("selected");
		$selfTreeLi.addClass("selected");
		$selfTreeLi.toggleClass("expanded",true);
		if ($selfTreeLi.hasClass("expanded")) {
			loadSubTree($selfTreeLi);
		}
	});
});