<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播回顾</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/uploadify/css/uploadify.css" />
	<script type="text/javascript" src="${ctxStatic}/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/review/data/list?roomId=${roomReviewData.roomId}");
			$("#searchForm").submit();
			return false;
		}

		function sort(){
			var order = $("input[name$='order']");
			var orders = [];
			var ids = [];
			var orderValue;
			for(var i=0; i<order.length; i++){
				orderValue = $(order[i]).val();
				if(orderValue != ''){
					orders.push(orderValue);
					ids.push($("#pid" + i ).val());
				}
			}
			if(ids.length == 0){
				top.$.jBox.tip('请输入序号');
			}else{
				$.ajax({
					url: "${ctx}/live/review/sort",
					data: {
						id: ids,
						order: orders
					},
					type:"post",
					dataType:"json",
					success: function( data ) {
						top.$.jBox.tip("操作成功！");
						page();
					}
				});
			}
		}

		var allBtn = {"关闭" : true};
		function audit(id, roomId, operatorType) {
			var width = 700;
			var height;
			var typeStr;
			if (operatorType == "update") {
				height = 490;
				typeStr = "修改视频";
			} else {
				height = 410;
				typeStr = "查看视频"
			}
			top.$.jBox.open("iframe:${ctx}/live/review/data/form?id=" + id + "&operatorType=" + operatorType, typeStr, width, height, {
				buttons: allBtn,
				submit: function(v, h, f) {

				},
				loaded: function(h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				},
				closed: function() {
					if (operatorType == "update") {
						location.reload();
					}
				}
			});
			return false;
		}
	</script>
</head>
<body>
<c:set var="roomId" value="${roomReviewData.roomId}}"/>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/live/review/data/list?roomId=${roomReviewData.roomId}">直播回顾--${fns:getRoom(roomId).name}</a></li>
</ul>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr>
		<th width="40%">视频文件</th>
		<th width="20%">创建时间</th>
		<th width="5%">排序</th>
		<shiro:hasAnyPermissions name="live:roomReviewData:edit,live:roomReviewData:delete">
			<th width="25%">操作</th>
		</shiro:hasAnyPermissions>
	</tr></thead>
	<tbody>
	<c:forEach items="${page.list}" var="review" varStatus="vs">
		<tr>
			<td>${review.name}</td>
			<td><fmt:formatDate value="${review.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
				<input type="text" name="order" class="input-min order" value="${review.sort }"/>
				<input type="hidden" id="pid${vs.index }" value="${review.id }" />
			</td>
			<shiro:hasAnyPermissions name="live:roomReviewData:edit,live:roomReviewData:delete">
				<td>
					<a href="javascript:void(0)" onclick="return audit('${review.id }', '${review.roomId }', 'search');" >查看</a>
						<%--<shiro:hasPermission name="live:roomReviewData:edit">
		    				<a href="javascript:void(0)" onclick="return audit('${review.id }', '${review.roomId }', 'update');" >修改</a>
		    			</shiro:hasPermission>
		    			<shiro:hasPermission name="live:roomReviewData:delete">
							<a href="${ctx}/live/review/delete?id=${review.id }&roomId=${review.roomId }" onclick="return confirmx('确认要删除该视频吗？', this.href)">删除</a>
						</shiro:hasPermission>--%>
					<c:choose>
						<c:when test="${review.status eq 1}">
							<shiro:hasPermission name="live:roomReviewData:edit">
								<a href="${ctx}/live/review/disable?id=${review.id }&roomId=${review.roomId }" onclick="return confirmx('确认要禁用该视频吗？', this.href)">禁用</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<span><font color="red">已禁用</font></span>
							<shiro:hasPermission name="live:roomReviewData:edit">
								<a href="${ctx}/live/review/enable?id=${review.id }&roomId=${review.roomId }" onclick="return confirmx('确认要启用该视频吗？', this.href)">启用</a>
							</shiro:hasPermission>
						</c:otherwise>
					</c:choose>
				</td>
			</shiro:hasAnyPermissions>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
<c:if test="${not empty page.list}">
	<div class="form-actions">
		<shiro:hasPermission name="live:roomReviewData:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="sort()" value="排序"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</c:if>
</body>
</html>