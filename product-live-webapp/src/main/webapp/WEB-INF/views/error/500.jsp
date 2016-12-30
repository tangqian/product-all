<%
response.setStatus(500);
// 获取异常类
Throwable ex = Exceptions.getThrowable(request);
if (ex != null){
	LoggerFactory.getLogger("500.jsp").error(ex.getMessage(), ex);
}
String forwardUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
if(UserTypeEnum.SPEAKER.isMyPersonalUri(forwardUrl)){
	request.setAttribute("isSpeakerVisit", true);
}

// 编译错误信息
StringBuilder sb = new StringBuilder("系统出错了");
// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print(sb);
}
// 输出异常信息页面

else if (forwardUrl.startsWith("/wap")){
%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="com.ofweek.live.core.common.web.Servlets"%>
<%@page import="com.ofweek.live.core.common.utils.Exceptions"%>
<%@ page import="com.ofweek.live.core.modules.sys.enums.UserTypeEnum" %>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta content="telephone=no" name="format-detection">
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/reset.css">
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/login.css">
	<script type="text/javascript" src="${webSite}/static/web/plugins/jquery/jquery-1.8.3.min.js"></script>
	<script src="${webSite}/static/web/wap/js/resize.js"></script>
	<title>404</title>
</head>
<body>
<header class="header">
	<a class="goback flagClass" href="javascript:history.go(-1);" style="display: none"></a>
	<div class="title">
		<img src="${webSite}/static/web/wap/img/logo.png" alt="">
	</div>
</header>
<div class="error_content">
	<img src="${webSite}/static/web/wap/img/error.png" alt="">
	<p>对不起，您访问的页面出现错误。</p>
	<a class="gobackBtn flagClass" href="javascript:history.go(-1);" style="display: none">返回上一页</a>
	<a class="backBtn" href="${webSite}/wap">返回直播首页</a>
</div>
<footer class="footer">
	<a href="${ctxWap}">首页</a>-<a href="${ctx}/index/help">直播指南</a><br>
	OFweek.com 粤ICP备06087881号-1
</footer>
<script type="application/javascript">
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
		if(history.length > 0){
			$(".flagClass").show();
		}
	}else{ //非IE浏览器
		if (navigator.userAgent.indexOf('Firefox') >= 0 ||
				navigator.userAgent.indexOf('Opera') >= 0 ||
				navigator.userAgent.indexOf('Safari') >= 0 ||
				navigator.userAgent.indexOf('Chrome') >= 0 ||
				navigator.userAgent.indexOf('WebKit') >= 0){
			if(window.history.length > 1){
				$(".flagClass").show();
			}
		}else{ //未知的浏览器
			$(".flagClass").show();
		}
	}
</script>
</body>
</html>
<%
	} else {
%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="com.ofweek.live.core.common.web.Servlets"%>
<%@page import="com.ofweek.live.core.common.utils.Exceptions"%>
<%@ page import="com.ofweek.live.core.modules.sys.enums.UserTypeEnum" %>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
<c:if test="${pageContext.request.getAttribute('isSpeakerVisit')}">
	<meta name="decorator" content="speaker"/>
</c:if>
	<link rel="stylesheet" type="text/css" href="${webSite}/static/web/css/live.css"/>
	<title>404</title>
</head>
<body>
<div class="error_content">
	<img src="${webSite}/static/web/img/error.png" alt="">
	<p>对不起，您访问的页面出现错误。</p>
	<a class="goback" href="javascript:history.go(-1);" id="gobackBtn" style="display: none">返回上一页</a>
	<c:choose>
		<c:when test="${pageContext.request.getAttribute('isSpeakerVisit')}"><a class="back" href="${ctx}/room">返回首页</a></c:when>
		<c:otherwise><a class="back" href="${webSite}">返回直播首页</a></c:otherwise>
	</c:choose>

</div>
<script type="application/javascript">
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
		if(history.length > 0){
			$("#gobackBtn").show();
		}
	}else{ //非IE浏览器
		if (navigator.userAgent.indexOf('Firefox') >= 0 ||
				navigator.userAgent.indexOf('Opera') >= 0 ||
				navigator.userAgent.indexOf('Safari') >= 0 ||
				navigator.userAgent.indexOf('Chrome') >= 0 ||
				navigator.userAgent.indexOf('WebKit') >= 0){
			if(window.history.length > 1){
				$("#gobackBtn").show();
			}
		}else{ //未知的浏览器
			$("#gobackBtn").show();
		}
	}
</script>
</body>
</html>
<%
} out = pageContext.pushBody();
%>