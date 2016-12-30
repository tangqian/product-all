<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="speakerData" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">资料名称:</label>
			<div class="controls">
				<label class="lbl"><a href="${fns:getLiveSysFile(speakerData.fileId).url}" target="_blank">${speakerData.name}</a></label>
			</div>
		</div>
	</form:form>
</body>
</html>