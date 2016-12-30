<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>智慧生活网直播_观众登录</title>
	<meta content="telephone=no" name="format-detection">
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/reset.css">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/login.css">
	<script src="${ctxStatic}/web/plugins/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${webSite}/static/web/wap/js/resize.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				submitHandler : function(form) {  //验证通过后的执行方法
					$("#login-submit").val("正在登录中...").css("background","#aaa");
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						type:"POST",
						dataType:"json",
						success:function( data ){
							if (data && data.sessionid) {
								top.location = "${not empty callback ? callback : ctxWap}";
							} else {
								if( data.isValidateCodeLogin){
									$("#validateCodeDiv").show();
									$('.validateCodeRefresh').click();
								}else{
									$("#validateCodeDiv").hide();
								}
								$("#messageBox").show();
								$("#login-submit").val("登录").css("background","#c60000");
							}
						}
					});

				},
				rules: {
					validateCode: {remote: "${ctx}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名"},
					password: {required: "请填写密码"},
					validateCode: {remote: "验证码不正确", required: "请填写验证码"}
				},
				errorElement : "p",
				errorClass : "errorp",
				errorPlacement: function(error, element) {
					if(element.attr("name") == 'validateCode'){
						error.insertAfter(element.parent());
					}else{
						error.appendTo(element.parent());
					}
				}
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			//alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = window.location;
			//top.location = "${ctxWap}/login";
		}
	</script>
</head>
<body>
	<header class="header">
		<a class="goback" onclick="window.history.go(-1)"></a>
		<div class="title">
			<img src="${webSite}/static/web/wap/img/logo.png" alt="">
		</div>
	</header>
<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
	<input type="hidden" name="type" value="1" />
	<section class="login_content">
		<p class="error_t" id="messageBox">您输入的账号密码不匹配，请重新输入！</p>
		<div class="login_group">
			<input type="text" id="username" class="required" name="username" value="${username}" placeholder="请输入用户名/邮箱">
		</div>
		<div class="login_group">
			<input type="password" id="password" class="required" name="password" placeholder="请输入密码" onchange="$('#messageBox').hide();">
		</div>
		<div class="login_pass">
			<div class="pass_group clearfix" id="validateCodeDiv" style="display: none">
				<input type="text" class="required" maxlength="5" name="validateCode" placeholder="请输入验证码">
				<img id="validateCodeImg" src="${ctx}/servlet/validateCodeServlet" onclick="$('.validateCodeRefresh').click();" alt=""/>
				<a href="javascript:;" class="validateCodeRefresh" onclick="$('#validateCodeImg').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">换张图</a>
			</div>
		</div>
		<div class="login_sub">
			<input type="submit" value="登 录" id="login-submit">
		</div>
		<div class="login_link">
			<a href="http://www.smartlifein.com/3g/registerUser3g.do">会员注册</a>
			<a href="http://www.smartlifein.com/user/userGetPassword.do">找回密码</a>
		</div>
	</section>
</form>
	<footer class="footer">
		<a href="${ctxWap}">首页</a>-<a href="${ctx}/index/help">直播指南</a><br>
		OFweek.com 粤ICP备06087881号-1
	</footer>
</body>
</html>