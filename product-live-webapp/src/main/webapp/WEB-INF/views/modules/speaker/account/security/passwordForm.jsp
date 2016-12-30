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
                    .append("<span class='em1'>></span>").append("<span>修改密码</span>");
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
	<h1 class="page-title">修改密码</h1>
	<!-- 修改密码 -->
	<div class="mt5 account-container">
		<form id="passwordForm" action="/speaker/account/saveUser" method="post">
		<input type="hidden" id="id" name="id" value="${user.id }"/>
			<dl class="clearfix upc-box">
				<dt>原密码：</dt>
				<dd>
					<input type="password" class="fl upc-name" name="password" id="password" minlength="6"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>新密码：</dt>
				<dd>
					<input type="password" class="fl upc-name" name="newPassword" id="newPassword" minlength="6"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>请确认新密码：</dt>
				<dd>
					<input type="password" class="fl upc-name" name="confirmNewPassword" id="confirmNewPassword" minlength="6" equalTo="#newPassword"/>
				</dd>
			</dl>
			<input type="submit" value="提 交" class="button-b upc-save">
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#passwordForm").validate({
            debug: false,
            errorClass: "error",
            focusInvalid: true,
            submitHandler: function(form) {
                form.submit();
            },
			rules: {
				password: {
					required: true,
					minlength: 6,
					remote: {
			            url: "/speaker/account/verifyOriginalPass",
			            type: "post",
			            dataType: 'json',
				        data: {
				        	password : function() { 
				        		return $("#password").val(); 
				        	}
				        }
			    	}
				},
				newPassword: {
					required: true,
			        minlength: 6
			   	},
			   	confirmNewPassword: {
			   		required: true,
			   		minlength: 6
			   	}
			},
			messages: {
				password: {
					required: "&nbsp;&nbsp;原密码不能为空",
					minlength: "&nbsp;&nbsp;密码长度不能小于6位",
					remote: "&nbsp;&nbsp;原密码错误,请重新输入"
				},
				newPassword: {
					required: "&nbsp;&nbsp;请输入新密码",
					minlength: "&nbsp;&nbsp;密码长度不能小于6位"
			   	},
				confirmNewPassword: {
					required: "&nbsp;&nbsp;请确认新密码",
					minlength: "&nbsp;&nbsp;密码长度不能小于6位",
					equalTo: "&nbsp;&nbsp;两次输入的密码不同,请重新输入"
				}
			},
        });
		
	});
</script>
</body>
</html>