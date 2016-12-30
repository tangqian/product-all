<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>重置主播密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#inputForm").validate({
				rules: {
					'account': {
				   		minlength: 6,
					},	
				   	'password' : {
				   		minlength: 6,
				   	}
				},
				messages: {
					'account': {
				   		minlength: "用户名长度不能少于6位"
					},
				   	'password' : {
				   		minlength: "密码长度不能小于6位"
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/speaker/resetPassForm">重置主播密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="speaker" action="${ctx}/live/speaker/resetPass" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<form:input path="account" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<form:password path="password" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>不少于6个字符，且必须包含字母和数字</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="live:speaker:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>