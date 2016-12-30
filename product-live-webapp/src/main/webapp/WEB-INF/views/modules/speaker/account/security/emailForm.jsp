<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>账号安全</title>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_security_manage").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/account/security'>账号安全</a>")
                    .append("<span class='em1'>></span>").append("<span>修改登录邮箱</span>");
        })
    </script>
    <style type="text/css">
   		.error {
    		color: #ff0000;
    	}
    </style>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
	<h1 class="page-title">修改登录邮箱</h1>
	<!-- 上传视频 -->
	<div class="mt5 account-container">
		<form id="emailForm" action="/speaker/account/saveUser" method="post">
		<input type="hidden" id="id" name="id" value="${user.id }"/>
			<dl class="clearfix upc-box">
				<dt>当前邮箱：</dt>
				<dd>
					<!-- 当前邮箱 -->
					<p class="fl line-h30">${user.email }</p>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>请输入新邮箱：</dt>
				<dd>
					<input type="text" class="upc-name" name="email" id="email" maxlength="50"/>
				</dd>
			</dl>
			<input type="submit" value="提 交" class="button-b upc-save">
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		jQuery.validator
		.addMethod(
				"emailValidate",
				function(value, element) {
					return this.optional(element)
							|| /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(value);
				}, $.validator.format("&nbsp;&nbsp;请输入正确的邮箱格式"));
		
		$("#emailForm").validate({
            debug: false,
            errorClass: "error",
            focusInvalid: true,
            submitHandler: function(form) {
                form.submit();
            },
            rules: {
            	email: {
                    required: true,
                    emailValidate : true
                }
            },
            messages: {
            	email: {
                    required: "&nbsp;&nbsp;请输入新邮箱"
                }
            }
        });
		
	});
</script>
</body>
</html>