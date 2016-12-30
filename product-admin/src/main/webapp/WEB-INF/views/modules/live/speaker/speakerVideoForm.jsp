<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频审核</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#fileId").uploadify({
			    'swf': ctxStatic + '/uploadify/swf/uploadify.swf?var=' + (new Date()).getTime(),
			    'uploader': 'http://live.smartlifein.com/sys/file/secretUpload2?var=' + (new Date()).getTime(),
			    'height': 30,
			    // 设置文件浏览按钮的高度
			    'width': 120,
			    // 设置文件浏览按钮的宽度
			    'buttonText': '上传视频',
			    // 浏览按钮的文本
			    'buttonCursor': 'hand',
			    // 鼠标指针悬停在按钮上
			    'fileSizeLimit': '200MB',
			    // 上传文件的大小限制
			    'fileObjName': 'file',
			    // 文件上传对象的名称
			    'fileTypeExts': '*.flv;*.mp4;*.ravi;*.mov;*.mkv;*.wmv;*.mpeg;*.mpg;*.dat;',
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
			    // 设置上传进度显示方式，percentage显示上传百分比，speed显示上传速度
			    'successTimeout' : 3,
			    'formData':{'operationType' : 'speakerVideo'},
			    'onUploadSuccess': function(file, data, response) {
			    	if(data != ""){
				        var info = eval("(" + data + ")");
						if(info.status == "0"){
							$.ajax({
								url: "${ctx}/live/speakerVideo/rewrite",
								data: {
									id: "${speakerVideo.id}",
									roomId: "${speakerVideo.roomId}",
									name: file.name,
									fileId: info.fileId
								},
								type:"post",
								dataType:"json",
								success: function( data ) {
									if (data.result == "success") {
										location.reload();
									} else {
										alert("上传直播视频失败(原因:修改数据失败)！");
									}
								}
							});
						}else{
							alert("上传直播视频失败！");
						}
			    	} else {
			    		alert("上传失败!请检查直播系统是否能正常访问(http://live.smartlifein.com)");
			    	}
			    },
			    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
		        },
			    'onSelectError' : function(file, errorCode, errorMsg) {
			    	alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#fileId').uploadify('settings', 'fileSizeLimit') + "大小！");
			    }
			});			
		});
		
		function goUpload(){
			top.$("#jbox-iframe").attr("src", "${ctx}/live/speakerVideo/rewriteForm?id=${speakerVideo.id}&___t=" + Math.random() );
			return false;
		}
	</script>
	<script src="${ctxStatic}/jwplayer/jwplayer.js" type="text/javascript"></script>
	<script src="${ctxStatic}/js/main.js" type="text/javascript"></script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="speakerVideo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">视频名称:</label>
			<div class="controls">
				<label class="lbl">${speakerVideo.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频文件:</label>
			<div class="controls">
				<label class="lbl">
				<c:choose>
					<c:when test="${not empty qcloudFileId}">
						<div id="id_video_container" style="width:100%;height:210px;"></div>
					</c:when>
					<c:otherwise>
						<span style="color: red;">视频暂未同步到云上,无法预览,请稍候再预览</span><br/>
					</c:otherwise>
				</c:choose>
			<shiro:hasPermission name="live:video:audit">
				<div style="float:left;">
					${fns:getLiveSysFile(speakerVideo.fileId).size}&nbsp;M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${fns:getLiveSysFile(speakerVideo.fileId).url}" target="_blank">下载视频</a>
				</div>
			</shiro:hasPermission>
				</label>
			</div>
			<div class="control-group">
			<div class="controls">
				<input type="hidden" value="${speakerVideo.fileId}" name="fileId"/>
				<input type="file" id="fileId"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频介绍:</label>
			<div class="controls">
				<label class="lbl">${speakerVideo.detail}</label>
			</div>
		</div>
	</form:form>
	<c:if test="${not empty qcloudFileId}">
		<script src="https://qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js" charset="utf-8"></script>
		<script type="text/javascript">
			(function(){
				var option ={"auto_play":"0","file_id":"${qcloudFileId}","app_id":"${fns:getAppId()}","height":210,"https":1};
				/*调用播放器进行播放*/
				new qcVideo.Player(
						/*代码中的id_video_container将会作为播放器放置的容器使用,可自行替换*/
						"id_video_container",
						option
				);
			})()
		</script>
	</c:if>
</body>
</html>