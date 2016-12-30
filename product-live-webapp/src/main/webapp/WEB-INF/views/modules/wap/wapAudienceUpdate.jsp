<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>完善资料</title>
	<meta content="telephone=no" name="format-detection">
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/reset.css">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/login.css">
	<script src="${ctxStatic}/web/plugins/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${webSite}/static/web/wap/js/resize.js"></script>
	<style type="text/css">
		input.errorp {
			border: 1px solid red;
		}

		p.errorp {
			color: #CC0000;
			font-family: Simsun;
			margin-top: 5px;
		}
	</style>
</head>
<body>
<header class="header">
	<a class="goback" onclick="window.history.go(-1)"></a>
	<div class="title">
		完善个人资料
	</div>
</header>
<form id="loginForm" class="form-signin" action="${ctx}/api/audience/update" method="post">
<section class="login_content">
	<div class="login_group">
		<input type="text" name="company" class="required" placeholder="请输入公司名">
	</div>
	<div class="login_group">
		<input type="text" name="name" class="required" placeholder="请输入姓名">
	</div>
	<div class="login_group">
		<input type="text" name="telephone" class="required" placeholder="请输入手机或座机">
	</div>
	<div class="login_group">
		<input type="text" name="department" class="required" placeholder="请输入部门">
	</div>
	<div class="login_group">
		<input type="text" name="job" class="required" placeholder="请输入职位">
	</div>
	<div class="login_sub">
		<input type="submit" value="确 定">
	</div>
</section>
</form>
<footer class="footer">
	首页-直播指南<br>
	OFweek.com 粤ICP备06087881号-1
</footer>

<!-- 个人资料完善成功弹窗 -->
<div class="mean_dia" id="mean_dia"></div>
<div class="mean_content" id="mean_content">
	<p>资料完善成功</p>
	<div class="time_down">
		<span id="timeDown">3</span>s后跳转回直播间
	</div>
	<a href="${not empty callback ? callback : ctxWap}" class="mean_link">确定</a>
</div>

<script>

	// 电话号码验证
	jQuery.validator.addMethod("complexPhone", function(value, element) {
		var tel = /(^(\d{3,4}-?)?\d{7,9}$)|(^1[3,5,8]\d{9}$)/g;
		return this.optional(element) || (tel.test(value));
	}, "格式为:固话为区号(3-4位)号码(7-9位),手机为:13,15,18号段");

	$(document).ready(function() {
		$("#loginForm").validate({
			submitHandler : function(form) {  //验证通过后的执行方法
				//当前的form通过ajax方式提交（用到jQuery.Form文件）
				$(form).ajaxSubmit({
					type:"POST",
					dataType:"json",
					success:function( data ){
						if (data && data.code == 0) {
							$("#mean_dia").show();
							$("#mean_content").show();
							setInterval(timeDown,1000);
						} else {
							alert(data.message);
						}
					}
				});

			},
			rules: {
				telephone :{
					complexPhone : ""
				}
			},
			messages: {
				company: {required: "请输入公司名"},
				name: {required: "请输入姓名"},
				job: {required: "请输入职位"},
				department: {required: "请输入部门"},
				telephone: {required: "请输入手机或座机"}
			},
			errorElement : "p",
			errorClass : "errorp",
			errorPlacement: function(error, element) {
				error.appendTo(element.parent());
			}
		});

		var timed = document.getElementById('timeDown');
		function timeDown(){
			var num = timed.innerHTML;
			// 倒计时结束跳转
			if(num == 0){
				window.location.href = '${not empty callback ? callback : ctxWap}'
				return
			}
			num--;
			timed.innerHTML = num;
		}
	});
</script>
<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?678cb18b67c0b9c9cf5a8a1e6944e558";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>
<script type="text/javascript">
	var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F28a416fcfc17063eb9c4f9bb1a1f5cda' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>