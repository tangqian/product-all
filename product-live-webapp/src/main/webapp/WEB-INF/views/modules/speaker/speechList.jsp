<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>PPT管理</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">PPT管理</h1>
    <div class="clearfix mt5">
        <div class="fl mr20">
            <a href="${ctx}/speaker/speech/form" class="button-upload upload-ppt" onclick="return tips(${totalCount});">添加PPT</a>
        </div>
        <div class="fl tips">
            <p class="mt10">您总共可上传${maxLimit }个PPT，您已上传 <span class="red">${totalCount }</span> 个PPT。</p>
        </div>
    </div>
    <div class="mt20">
        <p class="tips">不支持直接上传PPT/PDF文档。为确保演讲稿最佳显示效果，请务必把PPT文档转换成jpg格式图片并有序命名后，压缩上传。</p>
        <p class="tips"><span class="red">请参考文档：</span><a href="http://expo.ofweek.com/exhibitor/booth/speech/help_for_ppt_convert.doc" target="_blank" class="blue">《1分钟轻松把PPT批量转换为JPG图片教程》</a>，压缩包后缀可以是.rar或者zip。</p>
    </div>
    <!-- 表格：资料 -->
    <div class="mt10">
        <form id="speakerSpeechForm" method="post">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
                <tr>
                	<th width="30"></th>
                    <th class="align-left">演讲稿</th>
                    <th width="145" class="align-center" style="border-left: 1px solid #dbdbdb;">上传时间</th>
                    <th width="145" class="align-center" style="border-right: 1px solid #dbdbdb; border-left: 1px solid #dbdbdb;">状态</th>
                    <th width="145" class="align-center">操作</th>
                </tr>
            <c:forEach items="${page.list}" var="speech" varStatus="listStatus">
                <tr>
                 	<td></td>
                    <td>
                        <!-- for的属性值对应上面的checkbox的id -->
                        <label for="speech${listStatus.index}" class="data-name">${speech.name}</label>
                    </td>
                    <td class="align-center">
                    	<fmt:formatDate value="${speech.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td class="align-center">
                        <!-- 未通过，等待中，通过，分别对应class：not-pass，wait-pass，pass -->
                        ${fns:getAuditHtml(speech.status)}
                    </td>
                    <td class="align-center">
                		<input type="button" value="删除" class="button-a" onclick="prompts('${speech.id}')"/>
                    </td>
                </tr>
            </c:forEach>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerSpeech.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_speaker_speech").addClass('menu-son-cur');
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>PPT管理</span>");
   	});
   	
   	var sessionId = "${pageContext.session.id }";
   	function tips(count) {
   		var limit = '${maxLimit }';
   		if(count >= limit){
   			alert("您最多可上传" + limit + "条PPT,若您需要上传更多PPT,请与我们的客服人员联系!");
   			return false;
   		}
   		return true;
   	}
	
	function prompts(speechId) {
		var options = {
			type:"POST", 
			url : '/speaker/speech/check',
			data : {speechId : speechId},
			success : function(data){
				if (data.status == "1") {
					alert("此演讲稿正在直播中,不可删除");
				} else {
					if (confirm("您确定要删除选择的PPT吗?")) {
						deleted(speechId);
					}
				}
			}
		};
   		$.ajax(options);
	}
	
	function deleted(id) {
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/speaker/speech/delete',
			data : JSON.stringify({id : id}),
			success : function(data){
				if (data.status == "0") {
					location.reload();
				} else {
					alert("删除PPT失败!");
				}
			}
		};
		$.ajax(options);
	}
</script>
</body>
</html>
