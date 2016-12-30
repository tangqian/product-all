<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>banner图管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
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

			$("#btnSubmit").on('click', function(){
				if($("#imageId").val().length > 0){
					form.submit();
				}else{
					alert("亲.请上传图片哦~");
				}
			});

			$("#fileField").uploadify({
			    'swf': ctxStatic + '/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
			    'uploader': 'http://live.smartlifein.com/sys/file/secretUpload?var=' + (new Date()).getTime(),
			    'height': 30,
			    // 设置文件浏览按钮的高度
			    'width': 120,
			    // 设置文件浏览按钮的宽度
			    'buttonText': '上传图片',
			    // 浏览按钮的文本
			    'buttonCursor': 'hand',
			    // 鼠标指针悬停在按钮上
			    'fileSizeLimit': '20MB',
			    // 上传文件的大小限制
			    'fileObjName': 'file',
			    // 文件上传对象的名称
			    'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;',
			    // 设置可以选择的文件的类型，格式如：’*.jpg;*.gif;*.jpeg;*.png;*.bmp;’
			    'preventCaching': true,
			    // 不允许缓存
			    'auto': true,
			    // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
			    'multi': false,
			    // 设置为true时可以上传多个文件
			    'method': 'post',
			    // 提交方式Post或Get
			    'progressData': 'percentage',
			    'successTimeout' : 3,
			    // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
			    'onUploadSuccess': function(file, data, response) {
			    	if(data != ""){
				        var info = eval("(" + data + ")");
						if(info.status == "0"){
							$("#imageId").val(info.fileId);
							$("#imagePreview img").attr("src", "${fns:getLiveUrlPrefix()}" + info.uri);
							$("#imagePreview").show();
						}else{
							alert("上传图片失败！");
						}
			    	} else {
			    		alert("上传失败!请检查直播系统是否能正常访问(http://live.smartlifein.com)");
			    	}
			    },
			    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
		        }
			});

		});

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/live/homeBanner/list">banner图列表</a></li>
		<li class="active"><a href="${ctx}/live/homeBanner/form?id=${homeBanner.id}">banner图<shiro:hasPermission name="live:homeBanner:edit">${not empty homeBanner.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="live:homeBanner:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="homeBanner" action="${ctx}/live/homeBanner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input id="imageId" type="hidden" value="${homeBanner.fileId}" name="fileId">
				<ol id="imagePreview" <c:if test="${empty homeBanner.fileId}">style="display:none;"</c:if> >
					<li>
						<img style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;" src="${fns:getLiveSysFile(homeBanner.fileId).url}">
					</li>
				</ol>
				<input type="file" id="fileField" />
				<span class="help-inline"><font color="red">*</font>建议大小：1920 * 398</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接地址:</label>
			<div class="controls">
				<form:input path="href" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>示例：http://www.smartlifein.com </span>
			</div>
		</div>
		<c:if test="${not empty homeBanner.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${homeBanner.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">修改时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${homeBanner.updateDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="live:homeBanner:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>