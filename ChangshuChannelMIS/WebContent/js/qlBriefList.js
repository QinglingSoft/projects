$(function(){
	$("table.briefList > tbody > tr").click(function(){
		var pkJson = $(this).attr("primaryKeyValues");
		var primaryKeyValues = eval(pkJson);
		window.parent.displayProperties(primaryKeyValues["QLBM"]);
		$(this).addClass("selected").siblings().removeClass("selected");
	});
});
