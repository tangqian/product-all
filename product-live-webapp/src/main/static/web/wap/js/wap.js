$(function(){
	var pageIndex;
	var resRows;
	$("#loadMore").on("click", function(){
		var $more = $("#loadMore"),
	    html = "";
		if (!pageIndex) pageIndex = 1;
		$.ajax({
	        url: "http://live.smartlifein.com/api/overLives?pageNo=" + pageIndex,
	        dataType: 'json',
	        success: function(data) {
	        	resRows = data.length;
	            if (!data.length) {
	            	$more.remove();
	                return ;
	            }
	            for (var i in data) {
	                var enterUrl = "http://m.live.smartlifein.com/live/" + data[i].id;
	                if (data[i].type == 'EXPO') {
	                    enterUrl = "http://expo.ofweek.com/exhibition/index.xhtml?expo=" + data[i].id;
	                } else if (data[i].type == 'WEBINAR') {
	                    enterUrl = "http://webinar.ofweek.com/active.action?activity.id=" + data[i].id;
	                }
	                html += '<li>';
	                html += '<a class="nav" href="' + enterUrl + '">';
	                html += '<span class="end">直播结束</span>';
	                html += '<img src="' + data[i].bigUrl + '" alt="">';
	                html += '</a>';
	                
	                html += '<div class="main">';
	                html += '<a class="linkBooth" href="' + enterUrl + '">' + data[i].name + '</a>';
	                html += '<p><span></span>' + new Date(data[i].startTime).format("yyyy年MM月dd日 hh:mm:ss") + '</p>';
	                html += '<a class="goLink" href="' + enterUrl + '">查看回顾</a>';
	                html += '</div>';
	                
	                if (data[i].speaker) {
                        html += '<div class="content_del">';
                        html += '<span></span>主办公司：' + data[i].company + '';
                        html += '<span></span>主讲人：' + data[i].speaker + '';
                        html += '</div>';
                    }
	                html += '</li>';
	            }
	
	            $(html).hide().insertBefore($more).fadeIn(800);
	            if (resRows < 10) $more.remove();
	            pageIndex++;
	        }
	    });
	});
});
		
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}