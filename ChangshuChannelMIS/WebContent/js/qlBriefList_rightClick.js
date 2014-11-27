$(function(){
	$("table.briefList > tbody > tr").bind("contextmenu", function(event) {
		var pkJson = $(this).attr("primaryKeyValues");
		var primaryKeyValues = eval(pkJson);
		$("#rightClickMenu")
			.attr("QLBM", primaryKeyValues["QLBM"])
			.offset({
				top: event.pageY,
			 	left: event.pageX
			 })
			 .show();
		event.preventDefault();
	});
	
	$(document).click(function(){
		$("#rightClickMenu").offset({
			top: 0,
		 	left: 0
		 }).hide();
	});
	
	$("#rightClickMenu").click(function(event) {
		window.parent.briefDingwei($(this).attr("QLBM"));
	});
});