$(function() {
	
	$("img").live("click", function() {
		$("#searchConditions").toggle();
		if($(this).attr("src")=="images/down.gif"){
			$(this).attr("src","images/up.gif");
		}else{
			$(this).attr("src","images/down.gif");
		}
	});
	
	loadDataTable();
	$(".dataTableSearch select.dataT").change(loadDataTable);
	
})

function loadDataTable(){
	var $this = $(".dataTableSearch select.dataT");
	var dataTableName = $this.val();
	if(dataTableName!=null){
		$this
		.siblings(".normalSearch")
		.empty()
		.attr("disabled", true)
		.addClass("loading")
		.load(
			"ajax/dataTableCondition.jsp",
			{
				dataTableName : dataTableName
			},
			function() {
				$this.siblings(".normalSearch").attr("disabled", false).removeClass("loading");
			}
		);
	}
}
