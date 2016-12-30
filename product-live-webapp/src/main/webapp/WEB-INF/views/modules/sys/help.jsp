<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.rpc.common.dto.LiveTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="typeLive" value="<%=LiveTypeEnum.LIVE%>"/>
<c:set var="typeExpo" value="<%=LiveTypeEnum.EXPO%>"/>
<c:set var="typeWebinar" value="<%=LiveTypeEnum.WEBINAR%>"/>
<html>
<head>
	<meta name="decorator" content="default" />
	<title>直播指南 - 智慧生活网直播</title>
</head>
<div class="bgfff">
	<div class="wrapper">
	<div class="zhinan_content">
		<h3>1.如何申请举办直播?</h3>
		<div class="ml20">
			<p class="mtb12">请联系智慧生活网客服人员申请开通直播服务。当您开通直播服务之后，智慧生活网客服人员将对您的直播操作进行指导。</p>
			<div class="one_zhinan">直播服务热线：<br>联系邮箱：<span>service@ofweek.com</span> <br>联系电话：0755-83279360-823 <br>联系直播服务热线，开通直播服务</div>
		</div>
		<h3>2.直播电脑配置推荐</h3>
		<div class="ml20">
			<table width="880" class="zn_tb">
				<tr>
					<th width="170">类型</th>
					<th>建议标准配置</th>
				</tr>
				<tr>
					<td>电脑</td>
					<td>
						<div class="ml27 lh22">
							CPU：intel酷睿i5 <br>显卡：集成HD G4000以上（集成）+独立显卡 2G<br>内存：DDR3 4GB×2/8GB<br>显示器：2560×1440(16:9) 2K<br>电脑配置越高越好，操控、响应流畅，显示器分辨率2K及以上
						</div>
					</td>
				</tr>
				<tr>
					<td>摄像头</td>
					<td>
						<div class="ml27">
							视频直播、录制分辨率至少能够达到720p水准，才能保证视频直播时用户的画面足够清晰，优先选1080p全高清录制摄像头
						</div>
					</td>
				</tr>
				<tr>
					<td>麦克风</td>
					<td>
						<div class="ml27">
							优选专业电容麦克风</td>
		</div>
		</tr>
		<tr>
			<td>专业声卡</td>
			<td>
				<div class="ml27">
					电容麦克风、声卡、监听耳塞套装（针对网络K歌和主播，有专门的声卡，优先选购外置USB声卡）
				</div>
			</td>
		</tr>
		<tr>
			<td>监听耳机/耳塞</td>
			<td>
				<div class="ml27">
					监听耳塞套装、入耳式耳塞 （监听自己直播的效果）
				</div>
			</td>
		</tr>
		<tr>
			<td>传输网络</td>
			<td>
				<div class="ml27">
					上	行带宽4M(正常情况下50M以上宽带，其上行带宽约4M)
				</div>
			</td>
		</tr>
		</table>
	</div>
</div>
</div>
</div>
</body>
</html>