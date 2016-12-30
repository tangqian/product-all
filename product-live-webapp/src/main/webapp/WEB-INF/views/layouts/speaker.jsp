<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head lang="zh-cmn-Hans">
	<meta charset="utf-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><sitemesh:title/> - 主播后台</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	<link href="${webSite}/static/web/modules/speaker/css/ofweek_live_admin.css" rel="stylesheet">
	<script type="text/javascript" src="${webSite}/static/web/plugins/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
	<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}', webSite='${webSite}';</script>
	<sitemesh:head/>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/speaker/header.jsp" />
    <!-- 主体 -->
    <div class="w1000">

        <!-- 面包屑 -->
        <div class="crumb" id="headCrumbs">
            <a href="http://www.smartlifein.com" target="_blank">智慧生活网</a><span class="em1">></span>
            <a href="${webSite}" target="_blank">直播</a><span class="em1">></span>
            <a href="${ctx}/room">主播后台</a>
        </div>
        <!-- 面包屑 -->

        <div class="clearfix mt5">
        	<%@ include file="/WEB-INF/views/include/speaker/left.jsp"%>
        	<sitemesh:body/>
        </div>
	</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>