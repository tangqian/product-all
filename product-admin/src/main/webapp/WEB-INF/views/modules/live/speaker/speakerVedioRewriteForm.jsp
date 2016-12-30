<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频替换</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#inputForm").validate({
				submitHandler: function(form){
					if($("#name").val().length > 0){
						if(confirm("确认替换原视频文件?")){
							loading('正在提交，请稍等...');
							form.submit();							
						}
					}else{
						alert("请选择新视频文件");
					}
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
	<script src="${ctxStatic}/jwplayer/jwplayer.js" type="text/javascript"></script>
	<script src="${ctxStatic}/js/main.js" type="text/javascript"></script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="speakerVideo" action="${ctx}/live/speakerVideo/rewrite" method="post" class="form-horizontal" autocomplete="off">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">视频预览:</label>
			<div class="controls">
				<label class="lbl">
				<div style="width: 305px; height:203px;">
					<div id="vedioBox_${speakerVideo.fileId}">
						<img src="${ctxStatic}/images/logo_mr.gif"
							style="border: 1px solid #ddd;" />
					</div>
				</div>
				<script type="text/javascript">
					$.fn.loadVedio("#vedioBox_${speakerVideo.fileId}", '${fns:getLiveSysFile(speakerVideo.fileId).url}');
				</script>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新视频文件:</label>
			<div class="controls">
				<label class="lbl">
					<input type="hidden" id="name" name="name" htmlEscape="false" maxlength="255" class="input-xlarge" autocomplete="off"/>
					<sys:ckfinder input="name" vedioDomId="vedioBox_${speakerVideo.fileId}" type="vedio" uploadPath="" selectMultiple="false"/>
				</label>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>