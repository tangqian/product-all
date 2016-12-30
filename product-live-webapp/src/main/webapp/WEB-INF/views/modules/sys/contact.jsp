<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.rpc.common.dto.LiveTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="typeLive" value="<%=LiveTypeEnum.LIVE%>"/>
<c:set var="typeExpo" value="<%=LiveTypeEnum.EXPO%>"/>
<c:set var="typeWebinar" value="<%=LiveTypeEnum.WEBINAR%>"/>
<html>
<head>
	<meta name="decorator" content="default" />
	<title>联系我们 - 智慧生活网直播</title>
</head>

<div class="bgfff">
	<div class="wrapper">
		<div class="contact-us-content">
			<h2 class="contact-us-title">联系我们</h2>
			<div class="fl contact-way contact-refer">
				<dl class="contact-dl">
					<dt>企业直播咨询</dt>
					<dd>电话：+86-0755-83279360，分机837/838</dd>
					<dd>Email：sales@ofweek.com</dd>
				</dl>
			</div>
			<div class="fr contact-way contact-service">
				<dl class="contact-dl">
					<dt>客服热线</dt>
					<dd>电话：+86-0755-83279360，分机833</dd>
					<dd>Email：service@ofweek.com</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
</body>
</html>