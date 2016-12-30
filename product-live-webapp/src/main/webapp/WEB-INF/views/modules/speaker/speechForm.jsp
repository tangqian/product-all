<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="title" value="${not empty speech.id?'修改':'上传'}"/>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>${title}PPT</title>
    <link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerSpeech.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_speaker_speech").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/speech/list'>PPT管理</a>")
                    .append("<span class='em1'>></span>").append("<span>${title}PPT</span>");
        })
        
        var sessionId = "${pageContext.session.id }";
    </script>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">${title}PPT</h1>
    <!-- 上传资料 -->
    <form:form id="inputForm" modelAttribute="speech" action="${ctx}/speaker/speech/save" method="post">
        <form:hidden path="id" id="id"/>
        <form:hidden path="fileId" id="fileId"/>
       	<div style="color: red;font-size: 12px;">
    		<strong><c:if test="${error ne null && error ne '' }">✘操作失败!原因：${error }</c:if> </strong>
   		</div>
        <div class="mt5 upload-container">
           	<div class="">
	            <p class="tips">不支持直接上传PPT/PDF文档。为确保演讲稿最佳显示效果，请务必把PPT文档转换成jpg格式图片并有序命名后，压缩上传。</p>
	            <p class="tips"><span class="red">请参考文档：</span><a href="http://expo.ofweek.com/exhibitor/booth/speech/help_for_ppt_convert.doc" target="_blank" class="blue">《1分钟轻松把PPT批量转换为JPG图片教程》</a>，压缩包后缀可以是.rar或者zip。</p>
            </div>
            <dl class="clearfix upc-box">
                <dt>
                    <span class="red-start">*</span>PPT演讲稿：
                </dt>
                <dd>
                    <input type="file" name="file" class="file" id="fileField" />
                    <span id="fileName">${speech.name}</span>
                </dd>
            </dl>
            <input type="button" value="保 存" class="button-b upc-save" id="save_btn"/>
        </div>
    </form:form>
</div>
</body>
</html>
