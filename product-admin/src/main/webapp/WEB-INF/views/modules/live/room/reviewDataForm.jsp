<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看视频</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/uploadify/css/uploadify.css" />
	<script src="${ctxStatic}/jwplayer/jwplayer.js" type="text/javascript"></script>
	<script src="${ctxStatic}/js/main.js" type="text/javascript"></script>
	<script src="https://qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
	<script src="${ctxStatic}/baiduPlayer/player/cyberplayer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var player = cyberplayer("playercontainer").setup({
				width: 300,
				height: 200,
				file: "${roomReviewData.sourceUrl}",
				image: "${roomReviewData.coverPage}",
				autostart: false,
				stretching: "uniform",
				repeat: false,
				volume: 100,
				controls: true,
				ak: 'e53a45c491584eb29e41475ea3f9d29a'
			});
		});
	</script>
</head>
<body>
<form:form id="inputForm" modelAttribute="roomReviewData" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">视频名称:</label>
		<div class="controls">
			<label class="lbl"><a href="${fns:getLiveSysFile(roomReviewData.fileId).url}" target="_blank">${roomReviewData.name}</a></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">视频文件:</label>
		<div class="controls">
			<label class="lbl">
				<div style="width: 480px; height:210px;">
					<div id="playercontainer">
						<img src="${ctxStatic}/images/logo_mr.gif"
							 style="border: 1px solid #ddd;" />
					</div>
				</div>
			</label>
		</div>
	</div>
</form:form>
</body>
</html>