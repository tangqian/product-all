<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>CDN同步命令</title>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:;">系统消息列表</a></li>
    </ul>
	<div>
		<div class="bread_nav">CDN同步命令</div>
		<div class="con_box mt10" style="padding-bottom: 40px;">
			${command}
		</div>
	</div>
</body>
</html>