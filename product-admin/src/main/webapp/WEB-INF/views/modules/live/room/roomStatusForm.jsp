<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改状态</title>
	<meta name="decorator" content="blank"/>
</head>
<body>
	<div class="control-group">
	<form:form action="" method="head" modelAttribute="room">
			<label class="control-label">【${room.name}】状态修改为:</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${statusOptions}" itemLabel="meaning" itemValue="code" htmlEscape="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
	</form:form>		
	</div>
	<script type="text/javascript">
		var checkId = $('input[name="status"]:checked').val();
		$("input[name='status']").click(function(){
			checkId = $(this).val();
		});
	</script>
</body>
</html>
