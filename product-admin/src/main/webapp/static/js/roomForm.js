$(function() {
	$("#fileField").uploadify({
	    'swf': ctxStatic + '/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
	    'uploader': '',
	    'height': 30,
	    // 设置文件浏览按钮的高度
	    'width': 120,
	    // 设置文件浏览按钮的宽度
	    'buttonText': '上传图片',
	    // 浏览按钮的文本
	    'buttonCursor': 'hand',
	    // 鼠标指针悬停在按钮上
	    'uploadLimit': 1,
	    // 最大上传文件数量，如果达到或超出此限制将会触发onUploadError事件
	    'queueSizeLimit': 5,
	    // 队列最多显示的任务数量，如果选择的文件数量超出此限制，将会出发onSelectError事件。
	    'removeTimeout': 1,
	    // 如果设置了任务完成后自动从队列中移除，则可以规定从完成到被移除的时间间隔
	    'fileSizeLimit': '2MB',
	    // 上传文件的大小限制
	    'fileObjName': 'coverId',
	    // 文件上传对象的名称
	    'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',
	    // 设置可以选择的文件的类型，格式如：’*.jpg;*.gif;*.jpeg;*.png;*.bmp;’
	    'preventCaching': true,
	    // 不允许缓存
	    'requeueErrors': true,
	    // 如果设置为true，则单个任务上传失败后将返回错误，并重新加入任务队列上传
	    'auto': true,
	    // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
	    'multi': false,
	    // 设置为true时可以上传多个文件
	    'method': 'post',
	    // 提交方式Post或Get
	    'progressData': 'percentage',
	    // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
	    'onUploadSuccess': function(file, data, response) { },
	    'onDialogClose': function(queueData) { },
	    'onUploadError' : function(file, errorCode, errorMsg, errorString) { }
	});
	   
   function onSelectError(file, errorCode, errorMsg) {
	    switch (errorCode) {
	    case - 100 : alert("亲.同时只能上传 " + $('#fileField').uploadify('settings', 'queueSizeLimit') + " 张图片哦~");
	        break;
	    case - 110 : alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#fileField').uploadify('settings', 'fileSizeLimit') + "大小！");
	        break;
	    case - 120 : alert("文件 [" + file.name + "] 大小异常！");
	        break;
	    case - 130 : alert("文件 [" + file.name + "] 类型不正确！");
	        break;
	    default : alert("服务器异常，上传图片失败！");
	    	break;
	    }
	}
});