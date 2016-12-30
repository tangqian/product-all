<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看登记观众</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#name").focus();
			$("#inputForm").validate({
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
		<li><a href="${ctx}/live/audienceRegister/list">登记观众列表</a></li>
		<li class="active"><a href="${ctx}/live/audienceRegister/form?id=${audienceRegister.id}">登记观众查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="audienceRegister" action="${ctx}/live/audienceRegister/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">登记时间:</label>
			<div class="controls">
				<label class="lbl"><fmt:formatDate value="${audienceRegister.createDate }" pattern="yyyy-MM-dd HH:mm"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.company }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.country }${audienceRegister.province }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.address }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.name }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.job }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				<label class="lbl">
					<c:if test="${audienceRegister.sex == 0}">保密</c:if>
					<c:if test="${audienceRegister.sex == 1}">男</c:if>
					<c:if test="${audienceRegister.sex == 2}">女</c:if>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.department }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.mobilePhone }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.telephone }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<label class="lbl">${audienceRegister.fax }</label>
			</div>
		</div>

		<c:if test="${not empty audienceRegister.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${audienceRegister.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">修改时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${audienceRegister.updateDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>