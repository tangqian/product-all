$(function() {
	
	$("#fileField").uploadify({
	    'swf': webSite + '/static/web/plugins/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
	    'uploader': ctx + '/sys/file/upload?__sid=' + sessionId + '&var=' + (new Date()).getTime(),
	    'height': 30,
	    // 设置文件浏览按钮的高度
	    'width': 120,
	    // 设置文件浏览按钮的宽度
	    'buttonText': '上传头像',
	    // 浏览按钮的文本
	    'buttonCursor': 'hand',
	    // 鼠标指针悬停在按钮上
	    'fileSizeLimit': '200MB',
	    // 上传文件的大小限制
	    'fileObjName': 'file',
	    // 文件上传对象的名称
	    'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',
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
				$("#logoId").val(info.fileId);
				$("#logoUri").attr("src", webSite + "/static/upload/" + info.uri);
			}else{
				alert("上传头像失败！");
			}
	    },
	    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
			onSelectError(file, errorCode, errorMsg);
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
   
   $("#waiterForm").validate({
       debug: false,
       errorClass: "error",
       focusInvalid: true,
       submitHandler: function(form) {
           form.submit();
       },
       rules: {
			'user.account': {
		   		required: true,
		   		minlength: 6,
				remote: {
		            url: "/speaker/waiter/verifyAccount",
		            type: "post",
		            dataType: 'json',
			        data: {
			        	'user.account' : function() {
			        		return $("#id").val() == '' ? $("#account").val() : "{~!@#$%|^&*()+-}";
			        	}
			        }
		    	}
			},
			'user.password': {
				minlength: 6
			},
			'user.newPassword': {
		   		minlength: 6
		   	},
		   	name: {
		   		required: true
		   	}
		},
		messages: {
			'user.account': {
		   		required: "&nbsp;&nbsp;请输入登录帐号",
		   		minlength: "&nbsp;&nbsp;登录帐号不能小于6位",
		   		remote: "&nbsp;&nbsp;此帐号已存在,请更换其它名称"
			},
			'user.password': {
				required: "&nbsp;&nbsp;请输入密码",
				minlength: "&nbsp;&nbsp;密码长度不能小于6位"
			},
			'user.newPassword': {
				required: "&nbsp;&nbsp;请确认密码",
				minlength: "&nbsp;&nbsp;密码长度不能小于6位",
				equalTo: "&nbsp;&nbsp;两次输入的密码不同,请重新输入"
			},
		   	name: {
		   		required: "&nbsp;&nbsp;请输入姓名"
		   	}
		},
   });
   
   $(".update-btn").on('click', function(){
	   var waiterId = $(this).attr("waiterId");
	   location.href = "/speaker/waiter/form?id=" + waiterId;
   }); 
   
   $(".delete-btn").on('click', function(){
	   var waiterId = $(this).attr("waiterId");
	   if (confirm("您确定要删除选择的客服吗?")) {
		   location.href = "/speaker/waiter/delete?id=" + waiterId;
	   }
   }); 
   
});