<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>演讲稿审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="speakerSpeech" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">演讲稿名称:</label>
			<div class="controls">
				<label class="lbl"><a href="${fns:getLiveSysFile(speakerSpeech.fileId).url}" target="_blank">${speakerSpeech.name}</a></label>
			</div>
		</div>
	</form:form>
</body>
</html>