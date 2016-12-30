<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.rpc.common.dto.LiveTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="typeLive" value="<%=LiveTypeEnum.LIVE%>"/>
<c:set var="typeExpo" value="<%=LiveTypeEnum.EXPO%>"/>
<c:set var="typeWebinar" value="<%=LiveTypeEnum.WEBINAR%>"/>
<html>
<head>
	<meta name="decorator" content="default" />
	<title>智慧生活网直播_企业直播_产品发布会直播_在线展直播 - 高科技行业专业的直播平台</title>
	<meta name="keywords" content="在线展直播，发布会直播，在线研讨会直播，企业直播，智慧生活网直播">
	<meta name="description" content="智慧生活网直播是智慧生活网中国高科技行业门户旗下专业的直播平台，为用户提供最新鲜全面的高科技行业直播活动，包括在线展会直播、研讨会直播、产品发布会直播、企业直播、公司直播。可提供全程图文直播、视频直播、PPT直播等多种直播形式。">
</head>
<body>
<div class="banner">
	<ul id="banner_list"  class="banner_list">
	<c:forEach var="banner" items="${homeBanners}">
		<li><a href="${banner.href}" target="_blank"><img src="${fns:getSysFile(banner.fileId).url}" width="1200" height="800" alt="" /></a></li>
	</c:forEach>
	</ul>
	<ul id="dot_list" class="dot_list">
	<c:forEach var="banner" items="${homeBanners}" varStatus="vt">
		<li ${vt.index == 0 ? 'class="active"' : ''}></li>
	</c:forEach>
	</ul>
</div>
<div class="main_box">
	<div class="main-wrapper">
		<div class="main_left fl">
			<div class="live_list">
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
						<c:set var="enterUrl" value="http://live.smartlifein.com/live/${liveVo.id}" />
					</c:otherwise>
				</c:choose>
				<dl>
					<dt class="fl">
						<a href="${enterUrl}" target="_blank"><img src="${liveVo.bigUrl}"></a>
						<i></i>
						${liveVo.living ? '<span class="ing">正在直播</span>': '<span class="coming">直播预告</span>'}
					</dt>
					<dd class="fr">
						<h3><a href="${enterUrl}" target="_blank">${liveVo.name}</a></h3>
						<div class="timeshare">
							<strong><fmt:formatDate value="${liveVo.startTime}" pattern="yyyy-MM-dd HH:mm"/>
								至 <fmt:formatDate value="${liveVo.endTime}" pattern="yyyy-MM-dd HH:mm"/></strong>
							<div class="jiathis_style fr liveshare">
								<a href="javascript:;" class="jiathis sharelink" target="_blank">分享</a>
							</div>
						</div>
					<c:if test="${not empty liveVo.speaker}">
						<p class="zhujiang">
							<span>主办公司：${liveVo.company}</span>
							<span>&nbsp;主讲人：${liveVo.speaker}</span>
						</p>
					</c:if>
						<p>
							${liveVo.brief}<a href="${enterUrl}" target="_blank">详细</a>
						</p>
						<div class="opera">
							<a href="${enterUrl}" class="red_btn" target="_blank">${liveVo.living ? '立即进入': '查看'}</a>
						</div>
					</dd>
				</dl>
				</c:forEach>
			</div>

			<div class="live_list ended-live-list">
				<div class="end-hd">
					<h2>已结束的直播</h2>
					<h3>
						<a href="http://webinar.ofweek.com/foretimeConduct.action?user.id=2" target="_blank">在线研讨会</a>
						<a href="http://expo.ofweek.com/endedExh.xhtml" target="_blank">在线展会</a>
					</h3>
				</div>
			<c:forEach items="${overLives}" var="liveVo">
				<c:choose>
					<c:when test="${liveVo.type eq typeExpo}">
						<c:set var="enterUrl" value="http://expo.ofweek.com/exhibition/index.xhtml?expo=${liveVo.id}" />
					</c:when>
					<c:when test="${liveVo.type eq typeWebinar}">
						<c:set var="enterUrl" value="http://webinar.ofweek.com/active.action?activity.id=${liveVo.id}" />
					</c:when>
					<c:otherwise>
						<c:set var="enterUrl" value="http://live.smartlifein.com/live/${liveVo.id}" />
					</c:otherwise>
				</c:choose>
				<dl>
					<dt class="fl">
						<a href="${enterUrl}" target="_blank"><img src="${liveVo.bigUrl}"></a>
						<i></i>
						<span class="end">直播结束</span>
					</dt>
					<dd class="fr">
						<h3><a href="${enterUrl}" target="_blank">${liveVo.name}</a></h3>
						<div class="timeshare">
							<strong><fmt:formatDate value="${liveVo.startTime}" pattern="yyyy-MM-dd HH:mm"/>
								<fmt:formatDate value="${liveVo.endTime}" pattern="yyyy-MM-dd HH:mm"/></strong>
							<div class="jiathis_style fr liveshare">
								<a href="javascript:;" class="jiathis sharelink" target="_blank">分享</a>
							</div>
						</div>
						<p>
							${liveVo.brief}<a href="${enterUrl}" target="_blank">详细</a>
						</p>
						<div class="opera">
							<a href="${enterUrl}" class="grey_btn" target="_blank">查看回顾</a>
						</div>
					</dd>
				</dl>
			</c:forEach>
				<div class="live_list">
					<a href="javascript:;" class="more" id="js-more" rel="nofollow">更多</a>
				</div>
			</div>
		</div>
		<div class="main_right fr">
			<dl class="live-hotline">
				<dt>
				<div class="title-bg">
					<h2>直播服务热线</h2>
				</div>
				</dt>
				<dd>
					<p class="live-email">联系邮箱：service@ofweek.com</p>
					<p class="live-phone">联系电话：0755-83279360-823</p>
				</dd>
			</dl>

		</div>
	</div>
</div>
<script src="${webSite}/static/web/js/main.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
</body>
</html>