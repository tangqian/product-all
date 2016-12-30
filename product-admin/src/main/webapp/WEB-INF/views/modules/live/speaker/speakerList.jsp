<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播公司管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/speaker/list");
			$("#searchForm").submit();
	    	return false;
	    }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/speaker/list">直播公司列表</a></li>
		<shiro:hasPermission name="live:speaker:edit"><li><a href="${ctx}/live/speaker/form?source=1">直播公司添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="speaker" action="${ctx}/live/speaker/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>公司名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="20%">用户名</th>
			<th width="20%">公司名称</th>
			<th width="20%">行业</th>
			<th width="20%">创建时间</th>
			<shiro:hasAnyPermissions name="live:speaker:edit,live:speaker:delete">
				<th width="20%">操作</th>
			</shiro:hasAnyPermissions>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="speaker">
			<tr>
				<td>${speaker.account}</td>
				<td><a href="${ctx}/live/speaker/form?id=${speaker.id}&source=1">${speaker.company}</a></td>
				<td><c:set var="industry" value="${speaker.industry}"/>${fns:getDictLabels(industry, 'industry_option', '')}</td>
				<td><fmt:formatDate value="${speaker.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<shiro:hasAnyPermissions name="live:speaker:edit,live:speaker:delete">
					<td>
					<shiro:hasPermission name="live:speaker:edit">
	    				<a href="${ctx}/live/speaker/form?id=${speaker.id}&source=1">修改</a>
	    			</shiro:hasPermission>
	    			<shiro:hasPermission name="live:speaker:delete">
						<a href="${ctx}/live/speaker/delete?id=${speaker.id}" onclick="return confirmx('确认要删除该直播公司吗？', this.href)">删除</a>
					</shiro:hasPermission>
					</td>
				</shiro:hasAnyPermissions>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>