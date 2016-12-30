$(function() {
	
	$("#fileField").uploadify({
	    'swf': webSite + '/static/web/plugins/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
	    'uploader': ctx + '/sys/file/upload?var=' + (new Date()).getTime() + "&__sid=" + sessionId,
	    'height': 30,
	    // 设置文件浏览按钮的高度
	    'width': 120,
	    // 设置文件浏览按钮的宽度
	    'buttonText': '上传资料',
	    // 浏览按钮的文本
	    'buttonCursor': 'hand',
	    // 鼠标指针悬停在按钮上
	    'fileSizeLimit': '20MB',
	    // 上传文件的大小限制
	    'fileObjName': 'file',
	    // 文件上传对象的名称
	    'fileTypeExts': '*.doc;*.docx;*.pdf;*.xls;*.xlsx;*.rar;*.zip',
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
				$("#name").val(file.name);
				$("#fileId").val(info.fileId);
			}else{
				alert("上传资料失败！");
			}
	    },
	    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
			onSelectError(file, errorCode, errorMsg);
	    },
	    'onSelectError' : function(file, errorCode, errorMsg) {
	    	onSelectError(file, errorCode, errorMsg);
	    }
	});

	$("#inputForm").validate({
		submitHandler: function(form){
			if($("#fileId").val().length > 0){
				form.submit();
			}else{
				alert("请上传资料");
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
   
	$('#delete_btn').on('click', function(){
		var flag = false;
		var dataIds = "";
		$("input[name=dataId]:checkbox").each(function() {
			if(this.checked){
				flag = true;
				dataIds += $(this).val() + ","
			}
		});
		if (!flag) {
			alert("请选择要删除的资料!");
			return false;
		} else if (confirm("您确定要删除选择的资料吗?")) {
			$.ajax({
				url : "/speaker/data/delete",
				type:"POST", 
				data : {
					dataIds : dataIds
				},
				success : function(data) {
					if (data.status == "0") {
						location.reload();
					} else {
						if(data.msg){
							alert(data.msg);
						}else{
							alert("删除资料失败!");
						}
					}
				}
			});
		}
	});
	
	$('#save_btn').on('click', function(){
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/speaker/data/asyncCheckDataName',
			data : JSON.stringify({id : $('#id').val(), name : $('#name').val()}),
			success : function(data){
				if (data.status == "0") {
					$('#inputForm').submit();
				} else if (data.status == "1"){
					alert("资料名称重复!");
				} else {
					alert("上传资料失败!");
				}
			}
		};
		$.ajax(options);
	});
});