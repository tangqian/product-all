<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
</head>
<style>
    *{ padding: 0; margin: 0;}
    a:link, a:visited{ color: #666; text-decoration: none; }
    a:hover{ text-decoration: underline; }
    .red, a.red:link, a.red:visited{ color: #cc0000; text-decoration: none; }
    a.red:hover{ text-decoration: underline; }
    .login-box{ position: relative; width: 460px; margin: 0 auto; padding: 0 0 30px; font-size: 12px; background: #fff; }
    .login-title{ width: 4em; padding: 22px 0 0; margin: 0 auto; font: normal 18px/22px "Microsoft Yahei";
        white-space: nowrap; }
    .login-title-sub{ font-size: 12px; color: #a3a3a3; }
    .login-bd{ padding: 0 55px; }
    .login-input-box{ position: relative; z-index: 5; margin-top: 15px; }
    .login-input-wrap{ position: relative; z-index: 3; }
    .login-input{ position: relative; left: 0; top: 0; z-index: 2; width: 338px; height: 28px; padding: 5px;
        line-height: 28px; border: 1px solid #ccc; background: transparent; outline: none; }
    .login-input-placeholder{ position: absolute; z-index: -1; left: 5px; top: 0;
        font: normal 12px/40px "Microsoft Yahei"; color: #a3a3a3; }
    .login-input-fd{ height: 24px; line-height: 24px; }
    .login-error-msg{ line-height: 16px; margin: 0 0 5px; float: left; padding: 0 0 0 25px; background: url(/static/web/img/icon_warn.png) left center no-repeat; }
    .forget-password{ float: right; }
    .login-submit{ display: block; width: 100%; height: 40px; font: bold 18px/40px "Microsoft Yahei"; color: #fff;
        border: 0; border-radius: 7px; background: #cc0000; cursor: pointer; outline: none; }
    .login-reg{ float: right; margin-top: 5px; color: #666; }
    .login-code-box{ height: 70px; }
    .login-code-wrap{ float: left; width: 238px; margin: 0 5px 0 0; }
    .login-code-pic{ float: left; text-align: right; line-height: 24px; }
    .login-code{ display: block; width: 95px; height: 40px; border: 0; }
    .login-fd{ padding: 20px 0 0 55px; margin: 18px 0 0; color: #666; border-top: 1px solid #e1e1e1; }
    .other-login{ height: 32px; padding: 10px 0 0; }
    .other-login a{ float: left; margin-right: 10px; }
    input.errorp{border:1px solid red;}
	p.errorp{color: #CC0000;font-family: Simsun;margin-top: 5px;}
	.login-form{ clear: both; }
</style>
<body>
<div class="login-box">
    <h1 class="login-title">观众登录<span class="login-title-sub">（个人会员）</span></h1>
    <div class="login-bd">
		<p id="login-error" class="red login-error-msg" style="display: block;"></p>
        <form id="loginForm" class="login-form" action="${ctx}/login">
        <input type="hidden" name="type" value="1" />
        <input type="hidden" name="callback" value="${callback}" />
            <!-- 用户名 -->
            <div class="login-input-box">
                <div class="login-input-wrap">
                    <input type="text" class="login-input required" id="username" name="username" value="${username}"/>
                </div>
                <p class="login-input-placeholder">用户名</p>
            </div>
            <!-- 密码 -->
            <div class="login-input-box">
                <div class="login-input-wrap">
                    <input type="password" class="login-input required" id="password" name="password"/>
                </div>
                <p class="login-input-placeholder">密码</p>
                <div class="login-input-fd">
                    <a href="http://www.smartlifein.com/user/userGetPassword.do" target="_blank" class="forget-password">忘记密码</a>
                </div>
            </div>
            <!-- 验证码 -->
	        <div id="validateCodeDiv" class="login-input-box" style="display: none">
	            <div class="login-input-wrap login-code-box">
	                <input type="text" class="login-input login-code-wrap required" maxlength="5" name="validateCode" id="validateCode"/>
	                <div class="login-code-pic">
	                    <img id="validateCodeImg" src="${ctx}/servlet/validateCodeServlet" onclick="$('.validateCodeRefresh').click();" alt="" class="login-code" />
	                    <a href="javascript:;" class="validateCodeRefresh" onclick="$('#validateCodeImg').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">换张图</a>
	                </div>
	            </div>
	            <p class="login-input-placeholder">输入验证码</p>
	        </div>
            <!-- 登录按钮 -->
            <div class="login-input-box">
                <input type="submit" value="登 录" class="login-submit" />
                <div class="login-input-fd">
                    <p class="login-reg">还不是智慧生活网会员？<a href="http://www.smartlifein.com/user/registerUser.do" target="_blank" class="red">立即注册</a></p>
                </div>
            </div>
            <!-- -->
        </form>
    </div>
    <div class="login-fd">
        <p>观众也可以使用以下帐号登录</p>
        <div class="other-login">
            <a href="javascript:parent.location.href='${webSite}/thirdPartyLogin?method=qqLogin&callback=${callback }'"><img src="${webSite}/static/web/img/qq.png" alt="使用qq登录"/></a>
            <a href="javascript:parent.location.href='${webSite}/thirdPartyLogin?method=weiboLogin&callback=${callback }'"><img src="${webSite}/static/web/img/weibo.png" alt="使用微博登录"/></a>
            <a href="javascript:parent.location.href='${webSite}/thirdPartyLogin?method=weixinLogin&callback=${callback }'"><img src="${webSite}/static/web/img/weixin.png" alt="使用微信登录"/></a>
        </div>
    </div>
</div>

<link href="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/web/plugins/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/web/plugins/jquery-validation/1.11.1/lib/jquery.form.js" type="text/javascript"></script>
<script>
    $('.login-input').on('input propertychange', function() {
        var content = $(this).val();
        var $placeholder = $(this).parents('.login-input-box').find('.login-input-placeholder');
        if (content === '') {
            $placeholder.show();
        } else {
            $placeholder.hide();
        }
    });
    
	$(document).ready(function() {
		$("#loginForm").validate({
			submitHandler : function(form) {  //验证通过后的执行方法
				$(".login-submit").val("正在登录中...").css("background","#aaa");
				//当前的form通过ajax方式提交（用到jQuery.Form文件）
				$(form).ajaxSubmit({
					type:"POST",
					dataType:"json",
					success:function( data ){
						if (data && data.sessionid) {
							if ("${callback}".indexOf("http://live.smartlifein.com") != -1) {
								top.location = "${callback}";
							} else {
								top.location = "${ctx}";
							}
						} else {
							if (data.isValidateCodeLogin) {
								$("#validateCodeDiv").show();
								$('.validateCodeRefresh').click();
								$('#validateCode').val('');
							}else{
								$("#validateCodeDiv").hide();
							}
							$("#login-error").text("您输入的账号密码不匹配，请重新输入！");
							$(".login-submit").val("登 录").css("background","#cc0000");
						}
					}
				});

			},
			rules: {
				validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
			},
			messages: {
				username: {required: "请填写用户名"},password: {required: "请填写密码"},
				validateCode: {remote: "验证码不正确", required: "请填写验证码"}
			},
			errorElement : "p",
			errorClass : "errorp",
			errorPlacement: function(error, element) {
				error.appendTo(element.parent());
			}
		});
	});
</script>
</body>
</html>