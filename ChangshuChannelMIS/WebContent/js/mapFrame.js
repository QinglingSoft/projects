/**
 * 需要jquery.cookie.js
 */

/**
 * 地图中选择桥梁，右侧显示属性
 * @param qlbm
 */
function qlSelect(qlbm) {
	displayProperties(qlbm);
}

function displayProperties(qlbm) {
	$("#ql").attr("src", "qlFrame.jsp?QLBM=" + qlbm);
	$("#rootFrameset").attr("cols", "*, 30%");
}

/**
 * 外部定位
 * @param qlbm
 */
function dingwei(qlbm) {
	var mapFrame = window.frames["map"];
	if (mapFrame) {
		if (mapFrame.dingweiQLBM) {
			mapFrame.dingweiQLBM(qlbm);
		} else {
			alert("地图没有定位指令");
		}
	};
	displayProperties(qlbm);
}

function selectLayers(layersStr) {
	var mapFrame = window.frames["map"];
	if (mapFrame) {
		if (mapFrame.ControlMapVisible) {
			mapFrame.ControlMapVisible(layersStr);
		} else {
			alert("地图没有选择图层指令");
		}
	}
}