<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.rpc.common.dto.LiveTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="typeLive" value="<%=LiveTypeEnum.LIVE%>"/>
<c:set var="typeExpo" value="<%=LiveTypeEnum.EXPO%>"/>
<c:set var="typeWebinar" value="<%=LiveTypeEnum.WEBINAR%>"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>智慧生活网直播_企业直播_产品发布会直播_在线展直播 - 高科技行业专业的直播平台</title>
	<meta name="keywords" content="在线展直播，发布会直播，在线研讨会直播，企业直播，智慧生活网直播">
	<meta name="description" content="智慧生活网直播是智慧生活网中国高科技行业门户旗下专业的直播平台，为用户提供最新鲜全面的高科技行业直播活动，包括在线展会直播、研讨会直播、产品发布会直播、企业直播、公司直播。可提供全程图文直播、视频直播、PPT直播等多种直播形式。">
	<meta content="telephone=no" name="format-detection"/>
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/reset.css"/>
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/index.css"/>
	<link rel="stylesheet" href="${webSite}/static/web/wap/css/login.css">
	<script type="text/javascript" src="${webSite}/static/web/plugins/jquery/jquery-1.8.3.min.js"></script>
	<script src="${webSite}/static/web/wap/js/resize.js"></script>
	<script src="${webSite}/static/web/wap/js/wap.js"></script>
	<style type="text/css">
		input.errorp {
			border: 1px solid red;
		}

		p.errorp {
			color: #CC0000;
			font-family: Simsun;
			margin-top: 5px;
		}
	</style>
</head>
<body>
	<header class="header">
		<a class="list" href="${webSite}"></a>
		<div class="title">
			<img src="${webSite}/static/web/wap/img/logo.png" alt="">
		</div>
	<c:if test="${empty fns:getUser()}">
		<a class="userLogin" href="${ctxWap}/login"></a>
	</c:if>
	</header>
	<section class="content">
		<ul>
			<c:forEach items="${notOverLives}" var="liveVo">
				<c:choose>
					<c:when test="${liveVo.type eq typeExpo}">
						<c:set var="enterUrl" value="http://expo.ofweek.com/exhibition/index.xhtml?expo=${liveVo.id}" />
					</c:when>
					<c:when test="${liveVo.type eq typeWebinar}">
						<c:set var="enterUrl" value="${liveVo.living ? 'http://webinar.ofweek.com/active.action?activity.id='
							: 'http://webinar.ofweek.com/activityDetail.action?user.id=2&activity.id='}${liveVo.id}" />
					</c:when>
					<c:otherwise>
						<c:set var="enterUrl" value="http://m.live.smartlifein.com/live/${liveVo.id}" />
					</c:otherwise>
				</c:choose>
				<li>
					<a class="nav" href="${enterUrl}">
						${liveVo.living ? '<span class="living">正在直播</span>': '<span class="loading">直播预告</span>'}
						<img src="${liveVo.bigUrl}" alt="">
					</a>
					<div class="main">
						<a class="linkBooth" href="${enterUrl}">${liveVo.name}</a>
						<p><span></span><fmt:formatDate value="${liveVo.startTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></p>
						<a class="goLink" href="${enterUrl}">${liveVo.living ? '立即进入': '查看'}</a>
					</div>
					<c:if test="${not empty liveVo.speaker}">
						<div class="content_del">
							<span></span>主办公司：${liveVo.company }
							<span></span>主讲人：${liveVo.speaker}
						</div>
					</c:if>
				</li>
			</c:forEach>
		</ul>

		<div class="loadMore" id="loadMore"><span></span>加载更多内容</div>
	</section>

	<footer class="footer">
		<a href="${ctxWap}">首页</a>-<a href="${ctx}/index/help">直播指南</a><br>
		OFweek.com 粤ICP备06087881号-1
	</footer>
</body>
</html>