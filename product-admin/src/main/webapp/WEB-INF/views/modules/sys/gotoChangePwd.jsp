<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>修改参展商密码</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#changePwdForm").validate({
		rules : {
			account: {
				remote:{
					type:"POST",
					url:"${ctx}/sys/manage/checkAccount",
					data:{
						account:function(){
							return $("#account").val();
						}
					},
					dataType: "json",
					dataFilter: function (data, type) {//判断控制器返回的内容
						if(data != ''){
							$("#id").val(data);
							return "true";
						}else{
							$("#id").val('');
							return "false";
						}
	                 }
				}
			},
			password: {
			    required: true,
			    minlength : 6
			}
		},
		messages : {
			account: {
				required: "请输入用户名/邮箱",
				remote: "用户名/邮箱不存在！"
			},
			password: {
			    required: "请输入新密码",
			    minlength : $.validator.format("密码至少{0}个字符")
			}
		},
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});

</script>
<style type="text/css">
#changePwdForm{padding:20px;}
#changePwdForm label.tag{float: left;line-height:30px;text-align:right;width:100px;}
#changePwdForm .txt{float:left;}
#changePwdForm p{overflow:hidden;*zoom:1;} 
</style>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:;">修改参展商密码</a></li>
    </ul>
	<form action="${ctx }/sys/manage/changePwd" method="post" id="changePwdForm" novalidate="novalidate" class="form-horizontal">
        <input id="id" type="hidden" name="id">
        <sys:message content="${message}"/>
        <p><label class="tag">用户名/邮箱：</label>
        	<input id="account" class="input-xlarge required" type="text" value="" name="account">
        </p>
        <p><label class="tag">新密码：</label>
        	<input id="password" class="input-large required" type="password" value="" name="password">
        </p>
        <p style="padding-left: 135px;">
        	<input id="btnSubmit" type="submit" class="btn btn-primary" value="提交">
        </p>
	</form>
</body>
</html>