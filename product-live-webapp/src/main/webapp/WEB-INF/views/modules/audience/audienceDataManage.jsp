<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="speaker" />
		<title>观众数据</title>
		<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
		<link rel="stylesheet" href="${webSite}/static/web/css/jeesite.css" />
		<link rel="stylesheet" href="${webSite}/static/web/css/bootstrap.css" />
	</head>

	<body>
		<!-- 右 -->
		<div class="fr w830">
			<h1 class="page-title">观众数据</h1>
			<!-- 表格：资料 -->
			<div class="mt20 set-service">
				<div>
					<form id="audienceDataForm" method="post">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
						<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table manage-live">
							<tr>
								<th style="text-indent: 15px;">直播主题</th>
								<th width="270" class="align-center" style="border-left: 1px solid #dbdbdb;">举行时间</th>
								<th width="80" class="align-center" style="border-left: 1px solid #dbdbdb;">全部观众</th>
								<th width="80" class="align-center" style="border-left: 1px solid #dbdbdb;">资料下载</th>
								<th width="80" class="align-center" style="border-left: 1px solid #dbdbdb;">互动聊天</th>
							</tr>
							<c:forEach items="${page.list}" var="item" varStatus="listStatus">
								<tr>
									<td style="text-indent: 15px;">${item.name }</td>
									<td class="align-center">
										<fmt:formatDate value="${item.startTime }" pattern="yyyy-MM-dd HH:mm:ss" /> 至
										<fmt:formatDate value="${item.endTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td class="align-center">
										<c:choose>
											<c:when test="${item.audienceCount == 0}">0</c:when>
											<c:otherwise>
												<a href="/speaker/audience/all?roomChat.roomId=${item.id }" class="blue" title="${item.name }直播间全部观众">${item.audienceCount }</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="align-center">
										<c:choose>
											<c:when test="${item.downloadAudienceCount == 0 }">0</c:when>
											<c:otherwise>
												<a href="/speaker/audience/downloadData?roomChat.roomId=${item.id }" class="blue" title="${item.name }直播间资料下载观众">${item.downloadAudienceCount }</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="align-center">
										<c:choose>
											<c:when test="${item.roomChatAudienceCount == 0 }">0</c:when>
											<c:otherwise>
												<a href="/speaker/audience/chat?roomChat.roomId=${item.id }" class="blue" title="${item.name }直播间互动聊天观众">${item.roomChatAudienceCount }</a>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</table>
						<c:if test="${fn:length(page.list) > 0 }">
							<div class="pagination">${page}</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#sm_audience_data").addClass('menu-son-cur');
				$("#headCrumbs").append("<span class='em1'>></span>").append("<span>观众数据</span>");
			});

			function page(n, s) {
				if(n) $("#pageNo").val(n);
				if(s) $("#pageSize").val(s);
				$("#audienceDataForm").attr("action", "${webSite }/speaker/audience/data/list");
				$("#audienceDataForm").submit();
				return false;
			}
		</script>
	</body>
</html>