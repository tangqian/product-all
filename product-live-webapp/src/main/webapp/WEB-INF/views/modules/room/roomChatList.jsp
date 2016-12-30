<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>聊天记录</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/jeesite.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/bootstrap.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">聊天记录</h1>
    <!-- 表格：资料 -->
    <div class="mt20">
    <form id="chatForm" method="post">
		<div class="clearfix chat-record-tab" id="chatRecordTab">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="currRoomId" name="currRoomId" type="hidden" value="${currRoomId}"/>
			<c:forEach items="${fns:getRoomList()}" var="room" varStatus="listStatus">
				<a href="javascript:" title="${room.name }" roomId="${room.id }" ${currRoomId == room.id ? 'class="current room-a"' : 'class="room-a"'}>
		        	${room.name }
		        </a>
			</c:forEach>
		</div>
		<p class="chat-rt">展开全部</p>
		<div class="chat-content">
			<ul>
				<c:forEach items="${page.list}" var="chat" varStatus="listStatus">
				<li>
					<p><span class="blue mr10">
					<c:if test="${chat.user.type == 1 }">[观众]</c:if>
					<c:if test="${chat.user.type == 3 }">[主播]</c:if>
					<c:if test="${chat.user.type == 4 }">[观众]</c:if>
					${chat.name }</span><fmt:formatDate value="${chat.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
					<div class="mt10">
						<p>${chat.content }</p>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
	</form>	
	</div>
	<c:if test="${fn:length(page.list) > 0 }">
		<div class="pagination">${page}</div>
	</c:if>	
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_room_chat").find('strong').css({"color" : '#cc0000'});
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>聊天记录</span>");
   		
   		$('form .room-a').on('click', function(){
   			var roomId = $(this).attr("roomId");
   			location.href = "/room/chat/list?roomId=" + roomId;
   		});
   	});
   	
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#chatForm").attr("action","${webSite }/room/chat/list?roomId=${currRoomId}");
		$("#chatForm").submit();
    	return false;
    }
</script>
</body>
</html>
