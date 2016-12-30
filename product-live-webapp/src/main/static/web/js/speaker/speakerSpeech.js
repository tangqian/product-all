$(function() {
	
	$("#fileField").uploadify({
	    'swf': webSite + '/static/web/plugins/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
	    'uploader': ctx + '/sys/file/upload?__sid=' + sessionId + '&var=' + (new Date()).getTime(),
	    'height': 30,
	    // 设置文件浏览按钮的高度
	    'width': 120,
	    // 设置文件浏览按钮的宽度
	    'buttonText': '上传PPT',
	    // 浏览按钮的文本
	    'buttonCursor': 'hand',
	    // 鼠标指针悬停在按钮上
	    'fileSizeLimit': '200MB',
	    // 上传文件的大小限制
	    'fileObjName': 'file',
	    // 文件上传对象的名称
	    'fileTypeExts': '*.rar;*.zip;',
	    // 设置可以选择的文件的类型，格式如：’*.jpg;*.gif;*.jpeg;*.png;*.bmp;’
	    'preventCaching': true,
	    // 不允许缓存
	    'auto': true,
	    // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
	    'multi': false,
	    // 设置为true时可以上传多个文件
	    'method': 'post',
	    // 提交方式Post或Get
	    'progressData': 'percentage',
	    // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
	    'onUploadSuccess': function(file, data, response) {
			//alert(data);
	        var info = eval("(" + data + ")");
			if(info.status == "0"){
				$("#fileName").html(file.name);
				$("#fileId").val(info.fileId);
			}else{
				alert("上传PPT失败！");
			}
	    },
	    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
			onSelectError(file, errorCode, errorMsg);
	    }
	});

	var isCommitted = false;
	
	$("#inputForm").validate({
		submitHandler: function(form){
			if($("#fileId").val().length > 0){
				if (!isCommitted) {
					isCommitted = true;// 防止表单重复提交
					form.submit();
				}
			}else{
				alert("请上传PPT");
			}
		}
	});
	   
   function onSelectError(file, errorCode, errorMsg) {
	    switch (errorCode) {
	    case - 110 : alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#fileField').uploadify('settings', 'fileSizeLimit') + "大小！");
	        break;
	    case - 120 : alert("文件 [" + file.name + "] 大小异常！");
	        break;
	    case - 130 : alert("文件 [" + file.name + "] 类型不正确！");
	        break;
	    default : alert("服务器异常，上传资料失败！");
	    	break;
	    }
	}
	
	$('#save_btn').on('click', function(){
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/speaker/speech/asyncCheckSpeechName',
			data : JSON.stringify({id : $('#id').val(), name : $('#fileName').text()}),
			success : function(data){
				if (data.status == "0") {
					$('#inputForm').submit();
				} else if (data.status == "1"){
					alert("PPT名称重复!");
				} else {
					alert("上传PPT失败!");
				}
			}
		};
		$.ajax(options);
	});
	
});