$(function() {
	//根元素点击加载路线层
	$("ul.lxTree > li > .title").click(function() {
		var $li = $(this).parent("li");
		if (!$li.hasClass("empty") && $li.children("ul").length == 0) {
			var $childUl = $("<ul class='lx loading'></ul>").appendTo($li);
			var data = {lxbmPrefix: $li.attr("lxbmPrefix")};
			$childUl.load("ajax/lxTree/lx.jsp", data, function() {
				$childUl.removeClass("loading");
				if ($childUl.children().length == 0) {
					$childUl.parent("li").addClass("empty");
					$childUl.remove();
				}
			});
		}
	});
	
	//路线节点点击加载路段层
	$("ul.lx > li > .title").live("click", function() {
		var $li = $(this).parent("li");
		if (!$li.hasClass("empty") && $li.children("ul").length == 0) {
			var $childUl = $("<ul class='ld loading'></ul>").appendTo($li);
			var data = {lxbm: $li.attr("lxbm")};
			$childUl.load("ajax/lxTree/ld.jsp", data, function() {
				$childUl.removeClass("loading");
				if ($childUl.children().length == 0) {
					$childUl.parent("li").addClass("empty");
					$childUl.remove();
				}
			});
		}
	});
	
	//路段点击加载桥梁层
	$("ul.ld > li > .title").live("click", function() {
		var $li = $(this).parent("li");
		if (!$li.hasClass("empty") && $li.children("ul").length == 0) {
			var $childUl = $("<ul class='ql loading'></ul>").appendTo($li);
			var data = {lxbm: $li.attr("lxbm"), ldxlh: $li.attr("ldxlh")};
			$childUl.load("ajax/lxTree/ql.jsp", data, function() {
				$childUl.removeClass("loading");
				if ($childUl.children().length == 0) {
					$childUl.parent("li").addClass("empty");
					$childUl.remove();
				}
			});
		}
	});
	
	//桥梁点击显示地图和属性
	$("ul.lxTree li.link").live("click", function(event) {
		var QLBM = $(this).attr("qlbm");
		//同时存在"map"框和dingwei函数时说明正在mapFrame中，调用；否则可能在searchListFrame中，加载mapFrame.jsp
		var workspaceFrame = window.top.frames["workspace"];
		if (workspaceFrame) {
			var mapFrame = workspaceFrame.frames["map"];
			if (mapFrame && workspaceFrame.dingwei) {
				workspaceFrame.dingwei(QLBM);
			} else {
				workspaceFrame.frameElement.src = "mapFrame.jsp?QLBM=" + QLBM;
			}
		}

	});
});