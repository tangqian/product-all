

$.fn.loadVedio = function(element, url) {
    var $element = $(element);
	var id = $element.attr("id");
	jwplayer(id).setup({
	    "flashplayer": ctxStatic + "/jwplayer/player.swf",
	    "file": url,
	    "controlbar": "bottom",
	    "width": '300',
	    "height": '200'
	  });
};