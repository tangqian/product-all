<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>资料回收站</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/jeesite.css" />
	<link rel="stylesheet" href="${webSite}/static/web/css/bootstrap.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">回收站</h1>
    <div class="mt15">
        <p class="tips">说明：回收站中的资料将保留180天，超过180天的资料将自动清除。</p>
    </div>
    <!-- 表格：资料 -->
    <div class="mt10">
        <form id="speakerRecycleForm" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
                <tr>
	                <th width="30"></th>
	                <th class="align-left">文件</th>
	                <th width="145" class="align-center" style="border-right: 1px solid #dbdbdb; border-left: 1px solid #dbdbdb;">类型</th>
	                <th width="145" class="align-center" style="border-right: 1px solid #dbdbdb;">删除时间</th>
	                <th width="145" class="align-center">操作</th>
                </tr>
            <c:forEach items="${page.list}" var="recycle" varStatus="listStatus">
                <tr>
                 	<td><input id="speakerId" name="speakerId" type="hidden" value="${recycle.speakerId}"/></td>
                    <td>
                        <!-- for的属性值对应上面的checkbox的id -->
                        <label for="speech${listStatus.index}" class="data-name">${recycle.subjectName}</label>
                    </td>
                    <td class="align-center">
                    	<c:choose>
                    		<c:when test="${recycle.subjectType == 256 }">资料</c:when>
                    		<c:when test="${recycle.subjectType == 257 }">视频</c:when>
                    		<c:when test="${recycle.subjectType == 258 }">PPT</c:when>
                    	</c:choose>
                    </td>
                    <td class="align-center">
                    	<fmt:formatDate value="${recycle.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td class="align-center">
                		<input type="button" value="还原" class="button-a" onclick='validateReport("${recycle.subjectType}", "${recycle.subjectId}", "${recycle.subjectName}", "${recycle.id}")' />
                    </td>
                </tr>
            </c:forEach>
            </table>
            <c:if test="${fn:length(page.list) > 0 }">
	            <div class="mt20">
		            <input type="button" value="清空回收站" class="button-a" id="clean_ecycle"/>
	            </div>
	      		<div class="pagination">${page}</div>
            </c:if>
        </form>
    </div>
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_speaker_data_recycle").addClass('menu-son-cur');
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>资料回收站</span>");
   		
   		$('#clean_ecycle').on('click', function(){
   			if (confirm("您确定要清空回收站吗?")) {
   				location.href= ctx + '/speaker/recycle/delete';
   			}
   		});
   	});
   	
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#speakerRecycleForm").attr("action","${webSite }/speaker/recycle/list");
		$("#speakerRecycleForm").submit();
    	return false;
    }
	
	function validateReport(subjectType, subjectId, subjectName, id) {
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/speaker/recycle/validateReport',
			data : JSON.stringify({subjectType : subjectType, subjectName : subjectName}),
			success : function(data){
				if (data.status == "1") {
					if (confirm("名称重复,确认覆盖吗?")) {
						reduction(subjectType, subjectId, id);
					}
				} else if (data.status == "2"){
					alert("资料已达上限,不能还原!");
				} else if (data.status == "0") {
					if (confirm("您确定要还原吗?")) {
						reduction(subjectType, subjectId, id);
					} 
				} else {
					 alert("还原资料失败!");
				}
			}
		};
		$.ajax(options);
	}
	
	function reduction(subjectType, subjectId, id) {
		var options = {
			type:"POST", 
			contentType:"application/json",
			url : '/speaker/recycle/asyncReduction',
			data : JSON.stringify({subjectType : subjectType, subjectId : subjectId, id : id}),
			success : function(data){
				if (data.status == "0") {
					location.reload();
				} else {
					alert("还原资料失败!");
				}
			}
		};
		$.ajax(options);
	}
</script>
</body>
</html>
