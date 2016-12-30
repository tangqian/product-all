$(function() {
	function getIoomId() {
		return $('#global').val();
	}
	
	saveObject("data-submit", "dataId", "请选择资料!", "/room/data/save", "添加资料失败!");
	saveObject("video-submit", "videoId", "请选择视频!", "/room/video/save", "添加视频失败!");
	saveObject("speech-submit", "speechId", "请选择PPT!", "/room/speech/save", "添加PPT失败!");
	
	function saveObject(className, id, alertMsg, url, alertErrMsg) {
		$('form .'+ className +'').on('click', function(){
			var flag = false;
			var objIds = "";
			$('form input[name='+ id +']').each(function(){
				if (this.checked) {
					flag = true;
					objIds += $(this).val() + ","
				}
			});
			if (!flag) {
				alert(alertMsg);
				return false;
			}
			var data = {
					roomId : getIoomId(),
					objIds : objIds
			};
			ajaxRequest(url, data, alertErrMsg);
		});
		
	}
	
	function ajaxRequest(url, data, msg) {
		$.ajax({
			url : url,
			data : data,
			success : function(data) {
				if (data.status == "0") {
					location.reload();
				} else {
					alert(msg);
				}
			}
		});
	}
	
	var globalSort;
	
	$('form .input-sort').on('focus', function(){
		globalSort = $(this).val();
	});
	
	$('form .input-sort').on('blur', function(){
		if ($(this).val() == globalSort) {
			return ;
		}
		var id = $(this).attr("id");
		var roomId = $(this).attr("roomId");
		var sourceId = $(this).attr("sourceId");
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/room/data/sort',
			data : JSON.stringify({id : id, sort : $(this).val(), roomId : roomId, sourceId : sourceId}),
			success : function(data){
				if (data.status == "0") {
					location.reload();
				} else {
					alert("资料排序失败!");
				}
			}
		};
		$.ajax(options);
	});
	
	$('.live-data-back').on('click', function(){
		location.href = "/room/list";
	});

});