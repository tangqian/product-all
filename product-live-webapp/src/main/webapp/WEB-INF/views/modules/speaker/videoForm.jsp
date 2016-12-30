<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="title" value="${not empty video.id?'修改':'上传'}"/>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>${title}视频</title>
    <link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerVideo.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_speaker_video").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/video/list'>视频管理</a>")
                    .append("<span class='em1'>></span>").append("<span>${title}视频</span>");
        })
        var sessionId = "${pageContext.session.id }";
    </script>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">${title}视频</h1>
    <!-- 上传资料 -->
    <form:form id="inputForm" modelAttribute="video" action="${ctx}/speaker/video/save" method="post">
        <form:hidden path="id" id="id"/>
        <form:hidden path="fileId" id="fileId"/>
        <div class="mt5 upload-container">
            <dl class="clearfix upc-box">
                <dt>
                    <span class="red-start">*</span>视频文件：
                </dt>
                <dd>
                    <input type="file" name="file" class="file" id="fileField" />
                    <div class="tips upc-tips">
	                    <p>1、视频大小不能超过 <span class="red">200M</span>；</p>
	                    <p>2、支持格式：FLV, MP4, MPEG/MPG/DAT, RAVI, MOV, MVB（RM）, MKV, WMV</p>
	                    <p>3、16：9视频展示效果最佳</p>
	                    <p>4、无法播放的视频官方审核时会统一处理</p>
                    </div>
                </dd>
            </dl>
            <dl class="clearfix upc-box">
	            <dt>
	            	<span class="red-start">*</span>视频名：
	            </dt>
	            <dd>
	           		<input type="text" readonly="readonly" class="upc-name" style="width: 315px;" value="${video.name}" id="name"/>
	            </dd>
            </dl>
            <input type="button" value="保 存" class="button-b upc-save" id="save_btn"/>
        </div>
    </form:form>
</div>
</body>
</html>
