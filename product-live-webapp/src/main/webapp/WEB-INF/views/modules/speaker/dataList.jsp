<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>下载资料管理</title>
	<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">下载资料管理</h1>
    <div class="clearfix mt5">
        <div class="fl mr20">
            <a href="${ctx}/speaker/data/form" class="button-upload" onclick="return tips(${totalCount});">上传资料</a>
        </div>
        <div class="fl tips">
            <p>您可在此栏目上传白皮书等文件资料供观众阅读与下载；</p>
            <p>您总共可上传${maxLimit }条资料，您已上传 <span class="red">${totalCount }</span> 条资料。</p>
        </div>
    </div>
    <!-- 表格：资料 -->
    <div class="mt10">
        <form id="speakerDataForm" method="post">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
                <tr>
                    <th width="55" class="align-center">
                        <!-- 全选 -->
                        <input type="checkbox" class="checkbox-a" id="checkAll" />
                    </th>
                    <th class="align-left">资料</th>
                    <th width="145" class="align-center" style="border-right: 1px solid #dbdbdb; border-left: 1px solid #dbdbdb;">状态</th>
                    <th width="145" class="align-center">操作</th>
                </tr>
            <c:forEach items="${page.list}" var="data" varStatus="listStatus">
                <tr>
                    <td class="align-center">
                        <!-- 每个checkbox拥有唯一ID -->
                        <input type="checkbox" id="data${listStatus.index}" value="${data.id}"  class="checkbox-a" name="dataId"  />
                    </td>
                    <td>
                        <!-- for的属性值对应上面的checkbox的id -->
                        <label for="data${listStatus.index}" class="data-name">${data.name}</label>
                    </td>
                    <td class="align-center">
                        <!-- 未通过，等待中，通过，分别对应class：not-pass，wait-pass，pass -->
                        ${fns:getAuditHtml(data.status)}
                    </td>
                    <td class="align-center">
                        <input type="button" value="修改" onclick="location.href='${ctx}/speaker/data/form?id=${data.id}'" class="button-a" />
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
<script type="text/javascript" src="${webSite}/static/web/js/speaker/speakerData.js"></script>
<script type="text/javascript">
   	$(document).ready(function(){
   		$("#sm_speaker_data").addClass('menu-son-cur');
   		$("#headCrumbs").append("<span class='em1'>></span>").append("<span>下载资料管理</span>");
   	});

   	var sessionId = "${pageContext.session.id }";
   	function tips(count) {
   		var limit = '${maxLimit }';
   		if(count >= limit){
   			alert("您最多可上传" + limit + "条资料,若您需要上传更多资料,请与我们的客服人员联系!");
   			return false;
   		}
   		return true;
   	}
</script>
</body>
</html>
