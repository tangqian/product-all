<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="title" value="${not empty data.id?'修改':'上传'}"/>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>${title}资料</title>
    <link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerData.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_speaker_data").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/data/list'>下载资料管理</a>")
                    .append("<span class='em1'>></span>").append("<span>${title}资料</span>");
        })
        var sessionId = "${pageContext.session.id }";
    </script>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">${title}资料</h1>
    <!-- 上传资料 -->
    <form:form id="inputForm" modelAttribute="data" action="${ctx}/speaker/data/save" method="post">
        <form:hidden path="id" id="id"/>
        <form:hidden path="fileId" id="fileId"/>
        <input type="hidden" id="sessionId" value="${pageContext.session.id}" />
        <div class="mt5 upload-container">
            <dl class="clearfix upc-box">
                <dt>
                    <span class="red-start">*</span>文<span class="em1"></span>件：
                </dt>
                <dd>
                    <input type="file" name="file" class="file" id="fileField" />
                    <p class="tips upc-tips">
                       	 资料大小不能超过20MB，仅支持doc、docx、pdf、xls、xlsx、rar、zip格式文件。
                    </p>
                </dd>
            </dl>
            <dl class="clearfix upc-box">
                <dt>
                    <span class="red-start">*</span>文件名：
                </dt>
                <dd>
                    <input type="text" readonly="readonly" class="upc-name" style="width: 315px;" value="${data.name}" id="name">
                </dd>
            </dl>
            <input type="button" value="保 存" class="button-b upc-save" id="save_btn"/>
        </div>
    </form:form>
</div>
</body>
</html>
