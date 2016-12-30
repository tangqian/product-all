<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异步任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					name : {
						required : true
					},
					type : {
						required : true
					},
					status : {
						required : true
					}
				},
				messages: {
					name : {
						required : "请填写任务名称"
					},
					type : {
						required : "请选择类型"
					},
					status : {
						required : "请选择状态"
					},
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
		<li><a href="${ctx}/sys/asyncTask/list">任务列表</a></li>
		<li class="active"><a href="${ctx}/sys/asyncTask/form?id=${asyncTask.id}"><shiro:hasPermission name="sys:asyncTask:edit">${not empty asyncTask.id?'修改':'添加'}</shiro:hasPermission>任务</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="asyncTask" action="${ctx}/sys/asyncTask/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">任务名称:</label>
			<div class="controls">
				<form:input path="name" id="name" htmlEscape="false" maxlength="50" class="input-large required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="图片"/>
					<form:option value="2" label="文件"/>
					<form:option value="3" label="视频"/>
					<form:option value="4" label="资料"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:option value="0" label="待执行"/>
					<form:option value="1" label="执行中"/>
					<form:option value="2" label="执行成功"/>
					<form:option value="3" label="执行失败"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<c:if test="${not empty asyncTask.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${asyncTask.createTime}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">修改时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${asyncTask.lastOperationTime}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:asyncTask:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>