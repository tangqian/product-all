<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="speaker" />
	<title>直播资料</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
</head>

<body>
	<!-- 右 -->
	<div class="fr w830">
		<h1 class="page-title">直播资料</h1>
		<div class="mt10">
			<div class="live-data">
			<input type="hidden" value="${roomId }" id="global">
				<!-- 表格：添加PPT -->
				<form>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
						<tr>
							<th width="15"></th>
							<th class="align-left" colspan="2">
								<span class="fl">PPT</span>
								<a href="javascript:" class="fl button-upload button-add live-data-add" id="addPPT" <c:if test="${fn:length(sList ) >= 3 }">style="display: none;"</c:if> >添加PPT</a>
							</th>
						</tr>
						<c:forEach items="${sList }" var="speech" varStatus="listStatus">
							<tr>
								<td></td>
								<td>${speech.source.name }</td>
								<c:if test="${status != 2 }">
									<td width="145" class="align-center">
										<a href="/room/speech/delete?id=${speech.id }&roomId=${speech.roomId }" class="button-a">移 除</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</form>
				<!-- 表格：添加视频 -->
				<form class="mt30">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
						<tr>
							<th width="15"></th>
							<th class="align-left" colspan="2">
								<span class="fl">视频</span>
								<a href="javascript:" class="fl button-upload button-add live-data-add" id="addVideo" <c:if test="${fn:length(vList ) >= 10 }">style="display: none;"</c:if>>添加视频</a>
							</th>
						</tr>
						<c:forEach items="${vList }" var="video" varStatus="listStatus">
							<tr>
								<td></td>
								<td>${video.source.name }</td>
								<c:if test="${status != 2 }">
									<td width="145" class="align-center">
										<a href="/room/video/delete?id=${video.id }&roomId=${video.roomId }" class="button-a">移 除</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</form>
				<!-- 表格：添加资料 -->
				<form class="mt30">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
						<tr>
							<th width="15"></th>
							<th class="align-left" colspan="3">
								<span class="fl">资料下载</span>
								<a href="javascript:" class="fl button-upload button-add live-data-add" id="addData" <c:if test="${fn:length(dList ) >= 10 }">style="display: none;"</c:if>>添加资料</a>
							</th>
						</tr>
						<tr class="add-download">
							<th width="15"></th>
							<th width="85" class="th-sort">排序</th>
							<th class="align-left">资料名称</th>
							<c:if test="${status != 2 }">
								<th class="align-center">操作</th>
							</c:if>
						</tr>
						<c:forEach items="${dList }" var="data" varStatus="listStatus">
							<tr>
								<td></td>
								<td><input type="text" class="input-sort" roomId="${data.roomId }" sourceId="${data.sourceId }" value="${data.sort }" name="sort" id="${data.id }"></td>
								<td>${data.source.name }</td>
								<c:if test="${status != 2 }">
									<td width="145" class="align-center">
										<a href="/room/data/delete?id=${data.id }&roomId=${data.roomId }" class="button-a">移 除</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</form>
				<!-- 选择PPT演讲稿 -->
				<div class="add-data-popup" id="selectPPT">
					<div class="adp-hd">
						<h2 class="fl page-title">选择PPT演讲稿</h2>
					</div>
					<c:choose>
						<c:when test="${fn:length(chooseRoomSpeechList ) == 0 }">
							<p class="adp-tips">您还没有PPT，请先
								<a href="/speaker/speech/form" class="blue">上传PPT演讲稿</a>
							</p>
						</c:when>
						<c:otherwise>
							<div>
								<form>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
										<tr>
											<th width="90" class="align-center">
												<!-- 全选 -->
												<input type="checkbox" class="checkbox-a" id="checkAll_a">
												<label for="checkAll_a">全选</label>
											</th>
											<th class="align-left">PPT</th>
										</tr>
										<c:forEach items="${chooseRoomSpeechList}" var="speech" varStatus="listStatus">
										<tr>
											<td class="align-center">
												<!-- 每个checkbox拥有唯一ID -->
												<input type="checkbox" id="ppt${listStatus.index }" class="checkbox-a" name="speechId" value="${speech.id }">
											</td>
											<td>
												<!-- for的属性值对应上面的checkbox的id -->
												<label for="ppt${listStatus.index }" class="data-name">${speech.name }</label>
											</td>
										</tr>
										</c:forEach>
									</table>
									<input type="button" value="提 交" class="button-b adp-submit speech-submit" />
								</form>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- 选择视频 -->
				<div class="add-data-popup" id="selectVideo">
					<div class="adp-hd">
						<h2 class="fl page-title">选择视频</h2>
					</div>
				<c:choose>
					<c:when test="${fn:length(chooseRoomVideoList ) == 0 }">
						<p class="adp-tips">您还没有视频，请先
							<a href="/speaker/video/form" class="blue">上传视频</a>
						</p>
					</c:when>
					<c:otherwise>
						<div>
							<form>
								<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
									<tr>
										<th width="90" class="align-center">
											<!-- 全选 -->
											<input type="checkbox" class="checkbox-a" id="checkAll_b">
											<label for="checkAll_b">全选</label>
										</th>
										<th class="align-left">视频</th>
									</tr>
									<c:forEach items="${chooseRoomVideoList}" var="video" varStatus="listStatus">
									<tr>
										<td class="align-center">
											<!-- 每个checkbox拥有唯一ID -->
											<input type="checkbox" id="video${listStatus.index }" class="checkbox-a" name="videoId" value="${video.id }">
										</td>
										<td>
											<!-- for的属性值对应上面的checkbox的id -->
											<label for="video${listStatus.index }" class="data-name">${video.name }
												<c:if test="${video.status == 4 }"><span class="not-pass">审核未通过</span></c:if>
											</label>
										</td>
									</tr>
									</c:forEach>
								</table>
								<input type="button" value="提 交" class="button-b adp-submit video-submit" />
							</form>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
				<!-- 选择资料 -->
				<div class="add-data-popup" id="selectData">
					<div class="adp-hd">
						<h2 class="fl page-title">选择资料</h2>
					</div>
					<div class="adp-bd">
					<c:choose>
						<c:when test="${fn:length(chooseRoomDataList ) == 0 }">
							<p class="adp-tips">您还没有资料，请先
								<a href="/speaker/data/form" class="blue">上传资料</a>
							</p>
						</c:when>
						<c:otherwise>
							<div>
								<form>
									<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table table-data">
										<tr>
											<th width="90" class="align-center">
												<!-- 全选 -->
												<input type="checkbox" class="checkbox-a" id="checkAll_c">
												<label for="checkAll_c">全选</label>
											</th>
											<th class="align-left">资料</th>
										</tr>
										<c:forEach items="${chooseRoomDataList}" var="data" varStatus="listStatus">
										<tr>
											<td class="align-center">
												<!-- 每个checkbox拥有唯一ID -->
												<input type="checkbox" id="data${listStatus.index }" class="checkbox-a" name="dataId" value="${data.id }">
											</td>
											<td>
												<!-- for的属性值对应上面的checkbox的id -->
												<label for="data${listStatus.index }" class="data-name">${data.name }
													<c:if test="${data.status == 4 }"><span class="not-pass">审核未通过</span></c:if>
												</label>
											</td>
										</tr>
										</c:forEach>
									</table>
									<input type="button" value="提 交" class="button-b adp-submit data-submit" />
								</form>
							</div>
						</c:otherwise>
					</c:choose>
					</div>
					<!-- end -->
				</div>
				<div class="align-center">
					<a href="javascript:" class="button-b live-data-back">返 回</a>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
	<script type="text/javascript" src="${webSite}/static/web/js/room/dataManage.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#sm_room_live").addClass('menu-son-cur');
			$("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/room/list'>管理直播</a>")
				.append("<span class='em1'>></span>").append("<span>直播资料</span>");
		});
	</script>
</body>
</html>