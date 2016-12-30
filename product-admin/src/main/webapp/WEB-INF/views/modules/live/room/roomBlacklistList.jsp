<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>黑名单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/roomBlacklist/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/roomBlacklist/list">黑名单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="roomBlacklist" action="${ctx}/live/roomBlacklist/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>用户名：</label><form:input path="user.account" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="15%">用户名</th>
			<th width="20%">加入黑名单时间</th>
			<th width="20%">直播主题</th>
			<th width="20%">理由</th>
			<shiro:hasPermission name="live:roomBlacklist:delete">
				<th width="10%">操作</th>
			</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="roomBlacklist">
			<tr>
				<td>${roomBlacklist.user.account }</td>
				<td><fmt:formatDate value="${roomBlacklist.createDate }" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${roomBlacklist.room.name }</td>
				<td>${roomBlacklist.reason }</td>
    			<shiro:hasPermission name="live:roomBlacklist:delete">
				<td>
					<a href="${ctx}/live/roomBlacklist/delete?id=${roomBlacklist.id}" onclick="return confirmx('确认要移除黑名单吗？', this.href)">移除黑名单</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>