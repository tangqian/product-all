<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>banner图管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/homeBanner/list");
			$("#searchForm").submit();
	    	return false;
	    }

		function sort(){
			var order = $("input[name$='orderIndex']");
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
					url: "${ctx}/live/homeBanner/sort",
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/homeBanner/list">banner图列表</a></li>
		<shiro:hasPermission name="live:homeBanner:edit"><li><a href="${ctx}/live/homeBanner/form">banner图添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="homeBanner" action="${ctx}/live/homeBanner/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="25%">排序</th>
			<th width="25%">banner图名称</th>
			<th width="20%">创建时间</th>
			<shiro:hasAnyPermissions name="live:homeBanner:edit,live:homeBanner:delete">
				<th width="20%">操作</th>
			</shiro:hasAnyPermissions>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="homeBanner" varStatus="st">
			<tr>
				<td>
					<input type="text" class="input-min" name="orderIndex" value="${homeBanner.sort }"/>
					<input type="hidden" id="pid${st.index }" value="${homeBanner.id}" />
				</td>
				<td><a href="${fns:getLiveSysFile(homeBanner.fileId).url}" target="_blank">${homeBanner.name}</a></td>
				<td><fmt:formatDate value="${homeBanner.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<shiro:hasAnyPermissions name="live:homeBanner:edit,live:homeBanner:delete">
				<td>
				<shiro:hasPermission name="live:homeBanner:edit">
    				<a href="${ctx}/live/homeBanner/form?id=${homeBanner.id}">修改</a>
    			</shiro:hasPermission>
    			<shiro:hasPermission name="live:homeBanner:delete">
					<a href="${ctx}/live/homeBanner/delete?id=${homeBanner.id}" onclick="return confirmx('确认要删除该banner图吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</shiro:hasAnyPermissions>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<c:if test="${not empty page.list}">
	<shiro:hasPermission name="live:homeBanner:edit">
		<div>
			<input type="button" class="btn btn-primary" onclick="sort()" value="排序"/>
		</div>
	</shiro:hasPermission>
	</c:if>
</body>
</html>