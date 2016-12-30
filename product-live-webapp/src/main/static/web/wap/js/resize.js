/**
 * Created by 77 on 2016/1/22.
 */
//控制字体
var calculate_size = function() {
	var BASE_FONT_SIZE = 100;
	var docEl = document.documentElement,
		clientWidth = docEl.clientWidth;
	if (!clientWidth) return;
	if(clientWidth > 520)
		clientWidth = 520;
	docEl.style.fontSize = BASE_FONT_SIZE * (clientWidth / 720) + 'px';
};
// 如果浏览器不支持addEventListener，则中止
if (document.addEventListener) {
	var resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize';
	window.addEventListener(resizeEvt, calculate_size, false);
	document.addEventListener('DOMContentLoaded', calculate_size, false);
	calculate_size();
}