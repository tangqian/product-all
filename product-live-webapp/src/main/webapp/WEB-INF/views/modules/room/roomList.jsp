<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>管理直播</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/jeesite.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/bootstrap.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">管理直播</h1>
    <!-- 表格：资料 -->
    <div class="mt10">
    <c:choose>
    	<c:when test="${fn:length(page.list) > 0 }">
	   		<div>
		    	<form id="roomForm" method="post">
		        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table manage-live">
						<tr>
						    <th width="15"></th>
						    <th class="align-left">直播主题</th>
						    <th width="260" class="align-center" style="border-left: 1px solid #dbdbdb;">举行时间</th>
						    <th width="80" class="align-center" style="border-left: 1px solid #dbdbdb;">直播资料</th>
						    <th width="80" class="align-center" style="border-left: 1px solid #dbdbdb;">观众</th>
						    <th width="55" class="align-center" style="border-left: 1px solid #dbdbdb;">人气</th>
						    <th width="125" class="align-center" style="border-left: 1px solid #dbdbdb;">直播间</th>
						</tr>
		            <c:forEach items="${page.list}" var="room" varStatus="listStatus">
						<tr>
						    <td></td>
						    <td>
						        <a href="http://live.smartlifein.com/live/${room.id }" target="_blank" class="blue">${room.name }</a>
						    </td>
						    <td class="align-center">
						    	<c:choose>
						    		<c:when test="${room.status == 2 }">
						    			<strong class="red">
						        			进行中...
						        		</strong>
						    		</c:when>
						    		<c:otherwise>
						    			<fmt:formatDate value="${room.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										至
						    			<fmt:formatDate value="${room.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						    		</c:otherwise>
						    	</c:choose>
						    </td>
						    <td class="align-center">
						        <a href="javascript:" roomId="${room.id }" class="blue manager_a">管理</a>
						    </td>
						    <td class="align-center">
						    	<c:choose>
						    		<c:when test="${room.audienceCount == 0 }">0</c:when>
						    		<c:otherwise>
								        <a href="/speaker/audience/all?roomChat.roomId=${room.id }" class="blue">${room.audienceCount }</a>
						    		</c:otherwise>
						    	</c:choose>
						    </td>
						    <td class="align-center">
						    	<c:choose>
						    		<c:when test="${room.visitorCount == 0 }">0</c:when>
						    		<c:otherwise>
								   		${room.visitorCount }
						    		</c:otherwise>
						    	</c:choose>
						    </td>
						    <td class="align-center">
						    	<c:choose>
						    		<c:when test="${room.status == 2 }">
										<a href="http://live.smartlifein.com/live/${room.id }" target="_blank" class="button-a">进入>></a>
						    		</c:when>
						    		<c:when test="${room.status == 4 }">
						    			<span class="button-a live-end">已结束</span>
						    		</c:when>
						    		<c:when test="${room.status == 0 }">
						    			<a href="http://live.smartlifein.com/live/${room.id }?mode=1" target="_blank" class="button-a">预览</a>
		                           		<a href="http://live.smartlifein.com/live/${room.id }" target="_blank" class="button-a">进入>></a>
						    		</c:when>
						    	</c:choose>
						    </td>
						</tr>
		            </c:forEach>
		            </table>
		      	<div class="pagination">${page}</div>
		        </form>
	    	</div>
    	</c:when>
    	<c:otherwise>
	 		<div class="no-live">
	        	<strong>您还没有直播，您可以联系我们的客服申请开通直播。</strong>
	      	</div>
    	</c:otherwise>
    </c:choose>
    </div>
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_room_live").addClass('menu-son-cur');
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>管理直播</span>");
   		
   		$('#roomForm .manager_a').on('click', function(){
   			var roomId = $(this).attr("roomId");
   			location.href = "/room/dataManage?roomId=" + roomId;
   		});	
   	});
   	
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#roomForm").attr("action","${webSite }/room/list");
		$("#roomForm").submit();
    	return false;
    }
</script>
</body>
</html>
