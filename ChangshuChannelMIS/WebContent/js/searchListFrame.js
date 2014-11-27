/**
 * 需要jquery.cookie.js
 */

/**
 * 从搜索列表或路线树中定位，工作区左侧显示地图，右侧显示属性
 * @param qlbm
 */
function briefDingwei(qlbm) {
	
	var layersStr = $.cookie("mapLayers");
	var dingweiUrl = "/YZGL/mapload.jsp?QLBM=" + qlbm + "&LayerID=" + layersStr;
	$("#briefList").attr("name", "map").attr("src", dingweiUrl);
	
	displayProperties(qlbm);

}

function displayProperties(qlbm) {
	$("#ql").attr("src", "qlFrame.jsp?QLBM=" + qlbm);
	$("#rootFrameset").attr("cols", "*, 30%");
}
