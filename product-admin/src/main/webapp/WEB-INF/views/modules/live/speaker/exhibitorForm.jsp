<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新建直播</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#inputForm").validate({
				rules: {
				   	'company': {
						remote: {
				            url: "${ctx}/live/speaker/checkCompany",
				            type: "post",
				            dataType: 'json',
					        data: {
					        	'id' : function() { return $("#id").val(); },
					        	'company' : function() { return $("#company").val(); }
					        }
				    	}
				   	},
				   	'password' : {
				   		minlength: 6,
				   	}
				},
				messages: {
				   	'company': {
				   		remote: "亲.公司名称已存在~"
				   	},
				   	'password' : {
				   		minlength: "密码长度不能小于6个字符"
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
		 
		function showPage(type) {
			if ("1" == type) {
				window.location.href = ("${ctx }/live/speaker/form?source=" + type);
			}
		}
		
		function page() {
			window.location.href = ("${ctx}/exhibitor/exhibitor/live2Exhibitors");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/live/speaker/list">直播公司列表</a></li>
		<shiro:hasPermission name="live:speaker:edit"><li class="active"><a href="${ctx}/live/speaker/form?source=2">直播公司添加</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="speaker" action="${ctx}/live/speaker/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${speaker.id }"/>
		<form:hidden path="account"/>
		<form:hidden path="industry"/>
		<form:hidden path="name"/>
		<form:hidden path="job"/>
		<form:hidden path="mobilePhone"/>
		<form:hidden path="telephone"/>
		<form:hidden path="email"/>
		<form:hidden path="fax"/>
		<form:hidden path="sex"/>
		<form:hidden path="department"/>
		<form:hidden path="address"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">来源:</label>
			<div class="controls">
				<form:select path="source" id="source" onchange="showPage(this.value)" class="input-small">
					<form:option value="1" label="手工录入"/>
					<form:option value="2" label="展商中选择"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="200" readonly="true" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="return page();"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设置密码:</label>
			<div class="controls">
				<form:input path="password" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>不少于6个字符，且必须包含字母和数字</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="live:room:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/live/speaker/list'"/>
		</div>
	</form:form>
</body>
</html>