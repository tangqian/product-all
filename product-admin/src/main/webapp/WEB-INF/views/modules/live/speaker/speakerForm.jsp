<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新建直播公司</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					'password': {
				    	minlength: 6
				   	},
				   	'account': {
				   		minlength: 6,
						remote: {
				            url: "${ctx}/live/speaker/checkAccount",
				            type: "post",
				            dataType: 'json',
					        data: {
					        	'account' : function() { return $("#account").val(); }
					        }
				    	}
				   	},
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
				   	}
				},
				messages: {
					'password': {
				        minlength: "密码长度不能少于6位"
				     },
				   	'account': {
				   		minlength: "用户名长度不能少于6位",
				   		remote: "亲.用户名已存在~"
				   	},
				   	'company': {
				   		remote: "亲.公司名称已存在~"
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
			if ("2" == type) {
				window.location.href = ("${ctx }/live/speaker/form?source=" + type);
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/live/speaker/list">直播公司列表</a></li>
		<li class="active"><a href="${ctx}/live/speaker/form?id=${speaker.id}&source=1">直播公司<shiro:hasPermission name="live:speaker:edit">${not empty speaker.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="live:speaker:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="speaker" action="${ctx}/live/speaker/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="id"/>
		<sys:message content="${message}"/>
		<%--<c:if test="${empty speaker.id}">
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
		</c:if>--%>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在行业:</label>
			<div class="controls">
				<form:select path="industriesIdList"  class="input-xlarge required">
					<form:options items="${fns:getDictList('industry_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<c:if test="${empty speaker.id}">
			<div class="control-group">
				<label class="control-label">设置用户名:</label>
				<div class="controls">
					<form:input path="account" htmlEscape="false" maxlength="50" class="input-xlarge required" id="account"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">设置密码:</label>
				<div class="controls">
					<form:password path="password" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font>若不修改密码，请留空。若修改密码，不少于6位，必须包含字母、数字、符号的其中两种以上组合 </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位:</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				<label for="sex0"><form:radiobutton id="sex0" path="sex" value="0" checked="true"/>保密</label>
				<label for="sex1"><form:radiobutton id="sex1" path="sex" value="1" />男</label>
				<label for="sex2"><form:radiobutton id="sex2" path="sex" value="2" />女</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门:</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobilePhone" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机:</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网址:</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${not empty speaker.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${speaker.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">修改时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${speaker.updateDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="live:speaker:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>