<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>视频管理</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">视频管理</h1>
    <div class="clearfix mt5">
        <div class="fl mr20">
            <a href="${ctx}/speaker/video/form" class="button-upload upload-viedo" onclick="return tips(${totalCount});">上传视频</a>
        </div>
        <div class="fl tips">
            <p>支持格式：FLV, MP4, MPEG/MPG/DAT, RAVI, MOV, MVB（RM）, MKV, WMV</p>
            <p>您总共可上传${maxLimit }条视频，您已上传 <span class="red">${totalCount }</span> 条视频。</p>
        </div>
        <div class="fr solution">
                  	视频无法播放解决办法
        <div class="solution-detail">
	        <p>如果视频在制作时不规范或者视频编码为非通用，将会造成视频上传后无法播放或者播放时只有声音没有画面。</p>
	        <p class="mt5">这类问题视频将在直播资料提交官方审核后由审核人员统一处理。</p>
	        <p class="mt5">为您带来不便深感抱歉，如仍有疑问，请联系在线客服处理。</p>
        </div>
      </div>
    </div>
    <!-- 表格：资料 -->
    <div class="mt10">
        <form id="speakerVideoForm" method="post">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
                <tr>
                    <th width="55" class="align-center">
                        <!-- 全选 -->
                        <input type="checkbox" class="checkbox-a" id="checkAll" />
                    </th>
                    <th class="align-left">视频</th>
                    <th width="145" class="align-center" style="border-right: 1px solid #dbdbdb; border-left: 1px solid #dbdbdb;">状态</th>
                    <th width="145" class="align-center">操作</th>
                </tr>
            <c:forEach items="${page.list}" var="video" varStatus="listStatus">
                <tr>
                    <td class="align-center">
                        <!-- 每个checkbox拥有唯一ID -->
                        <input type="checkbox" id="video${listStatus.index}" value="${video.id}"  class="checkbox-a" name="videoId"  />
                    </td>
                    <td>
                        <!-- for的属性值对应上面的checkbox的id -->
                        <label for="video${listStatus.index}" class="data-name">${video.name}</label>
                    </td>
                    <td class="align-center">
                        <!-- 未通过，等待中，通过，分别对应class：not-pass，wait-pass，pass -->
                        ${fns:getAuditHtml(video.status)}
                    </td>
                    <td class="align-center">
                        <input type="button" value="修改" onclick="location.href='${ctx}/speaker/video/form?id=${video.id}'" class="button-a" />
                    </td>
                </tr>
            </c:forEach>
            </table>
            <c:if test="${fn:length(page.list) > 0 }">
	            <div class="mt20">
	                <input type="button" value="删除" class="button-a" id="delete_btn"/>
	            </div>
      		</c:if>
        </form>
    </div>
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerVideo.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_speaker_video").addClass('menu-son-cur');
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>视频管理</span>");
   	});
   	
   	var sessionId = "${pageContext.session.id }";
   	function tips(count) {
   		var limit = '${maxLimit }';
   		if(count >= limit){
   			alert("您最多可上传" + limit + "条视频,若您需要上传更多视频,请与我们的客服人员联系!");
   			return false;
   		}
   		return true;
   	}
</script>
</body>
</html>
