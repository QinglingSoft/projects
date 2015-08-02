$(function() {
	$("#dataTableName").change(function() {
		var $this = $(this);
		$this.siblings(".value").empty();
		$this
			.parent()
			.parent()
			.siblings("ul")
			.empty()
			.attr("disabled", true)
			.addClass("loading")
			.load(
				"ajax/searchChoose.jsp",
				{
					dataTableName : $this.val()
				},
				function() {
					$this.parent().parent().siblings("ul").attr("disabled", false).removeClass("loading");
				}
			);
	});
});
