<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.sys.utils.UserUtils" %>
<%@ page import="com.ofweek.live.core.modules.sys.enums.UserTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%
	String uri = (String)request.getRequestURI();
	if("/index/help".equals(uri)){
		request.setAttribute("isHelp", true);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><sitemesh:title/></title>
	<link rel="stylesheet" type="text/css" href="http://images.ofweek.com/css/ofw-global-steven.css"/>
	<link rel="stylesheet" type="text/css" href="${webSite}/static/web/css/live.css"/>
	<script type="text/javascript" src="${webSite}/static/web/plugins/jquery/jquery-1.8.3.min.js"></script>
	<sitemesh:head/>
</head>
<body>
<div id="g-topbar" class="g-topbar">
	<div class="g-wrap">
		<div class="g-topbar-nav">
			<a href="http://www.smartlifein.com/" target="_blank" class="ofweek-index">智慧生活网首页</a>
		</div>
		<ul class="g-topbar-loginbar">
		<c:choose>
			<c:when test="${not empty fns:getUser()}">
				<li id="g-topbar-userinfo" class="g-topbar-userinfo">
					欢迎您：
					<%
						Integer userType = UserUtils.getUser().getType();
						if (UserTypeEnum.isAudience(userType)) {
					%>
					<a class="red" style="color: red;" href="http://bbs.ofweek.com/of_mail.php" rel="nofollow"
					   target="_blank">${fns:getUser().account}</a><i>|</i>
					<% } else if (UserTypeEnum.isSpeaker(userType)) { %>
					<a class="red" style="color: red;" href="${ctx}/room" rel="nofollow">${fns:getUser().account}！</a><i>|</i>
					<% } else if (UserTypeEnum.isWaiter(userType)) { %>
						${fns:getUser().account}！<i>|</i>
					<% } %>
					<a href="${ctx}/logout" rel="nofollow" target="_self">退出</a>
				</li>
			</c:when>
			<c:otherwise>
				<li id="g-topbar-logininfo" class="g-topbar-logininfo">
					<span class="redbg"><a href="javascript:void(0)" id="login-btn" rel="nofollow">登录</a></span><i>|</i>
					<a href="http://www.smartlifein.com/user/registerUser.do" rel="nofollow" target="_blank">注册</a> <i>|</i>
					<a href="${ctx}/login?type=3" rel="nofollow" target="_blank">主播登录</a>
				</li>
			</c:otherwise>
		</c:choose>
		</ul>
	</div>

	<div class="login_header">
		<div class="wrapper">
			<a class="fl" href="${webSite}"><img src="${webSite}/static/web/img/logo_live.png" alt=""></a>
			<c:if test="${pageContext.request.getAttribute('isHelp')}">
				<img class="fl" src="${webSite}/static/web/img/zhinan.png" alt="">
			</c:if>
		</div>
	</div>
</div>
<sitemesh:body/>

<!-- 页脚 -->
<div class="footer">
	<div class="foot clearfix">
		<div class="foot-wrap">
			<p class="footer-links">
				<a target="_blank" href="${ctx}/index/help">直播指南</a> -
				<a target="_blank" href="http://www.smartlifein.com/about/company.html">关于我们</a> -
				<a target="_blank" href="${ctx}/index/contact">联系我们</a>
			</p>
			<p class="copyright">copyright © OFweek.com 网站所有图片、文字未经许可不得拷贝<br />
				备案号：<a target="_blank" rel="nofollow" href="http://www.miitbeian.gov.cn/">粤ICP备06087881号-1</a></p>
		</div>
	</div>
</div>

<!-- BEGIN Comm100 Live Chat Button Code -->
<a href="#" target="_blank" onclick="comm100_10001561.openChatWindow(event,2578,-1);return false;">
	<img style="border:0px" id="comm100_ButtonImage"
		 src="https://solution.comm100.cn/livechatserver/BBS.aspx?siteId=10001561&amp;planId=2578&amp;partnerId=-1"></img>
</a>
<script src="https://solution.comm100.cn/livechatserver/js/LiveChat.js?siteId=10001561&amp;planId=2578&amp;partnerId=-1"
		type="text/javascript"></script>
<!-- End Comm100 Live Chat Button Code -->
<div style="position:fixed;top:50%;right:0; z-index:9999 !important;width:162px;margin-top:60px;_position:absolute;_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight/2+59));">
	<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=3318632707&amp;site=qq&amp;menu=yes">
		<img border="0" src="${webSite}/static/web/img/qq_blue.png" alt="点击这里给我发消息" title="点击这里给我发消息">
	</a>
	<style>
		* html {
			background-image: url(about:blank);
			background-attachment: fixed;
		}
	</style>
</div>

<c:if test="${empty fns:getUser()}">
<div id="loginBox" style="display: none; overflow: hidden; position: fixed; z-index: 999999; left: 50%; top: 50%; width: 460px; height: 510px; margin: -230px 0 0 -235px;">
    <iframe id="main" frameborder="0" width="460" height="510"></iframe>
    <img src="${webSite}/static/web/img/close.png" id="loginClose" alt="" style="position: absolute; right: 5px; top: 5px; cursor: pointer;" />
</div>

	<script type="text/javascript">
		(function($){
			var shadow = '<div id="loginShadow" style="position: fixed; left: 0; top: 0; z-index: 999998; width: 100%; height: 100%; background: #000; opacity:0.3; filter:alpha(opacity=30);"></div>';
			// 显示登录弹窗
			$('body').on('click', '#login-btn', function() {
				if(!document.getElementById("main").src){
					document.getElementById("main").src = "${ctx}/login?callback=" + window.location.href + "&type=1&r=" + new Date().getTime();
				}
				//document.getElementById("main").src = "${ctx}/login";
				$('#loginBox').show();
				$('body').append($(shadow));
			});

			// 隐藏登录弹窗
			$('body').on('click', '#loginClose', function() {
				$('#loginBox').hide();
				$('#loginShadow').remove();
			});
		}(jQuery));
	</script>
</c:if>
</body>
</html>