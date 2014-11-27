$(function() {
	$(".advancedSearch select.field").change(function() {
		var $this = $(this);
		var $form = $this.parents("form");
		var $input = $form.children("input[name=dataTableName]");
		var dataTableName = $input.val();
		$this.siblings(".value").empty();
		$this
			.siblings(".operator")
			.empty()
			.attr("disabled", true)
			.addClass("loading")
			.load(
				"ajax/advancedSearchOperator.jsp",
				{
					dataTableName : dataTableName,
					fieldName : $this.val()
				},
				function() {
					$this.siblings(".operator").attr("disabled", false).removeClass("loading");
				}
			);
		$this
			.siblings(".valueInput")
			.empty()
			.addClass("loading")
			.load("ajax/advancedSearchValueInput.jsp",
				{
					dataTableName : dataTableName,
					fieldName : $this.val()
				},
				function() {
					$this.siblings(".valueInput").removeClass("loading");
				}
			);
	});
	$(".advancedSearch form[name=newConditionForm]").submit( function(event) {
		event.preventDefault();
		var $this = $(this);
		$this.ajaxSubmit({target: $this.siblings(".conditionList")});
	});
	$(".advancedSearch .conditionList a").live("click", function(event) {
		event.preventDefault();
		$(this).parents(".conditionList").load($(this).attr("href"));
	});
});
