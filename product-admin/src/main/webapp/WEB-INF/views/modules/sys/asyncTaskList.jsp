<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异步任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/asyncTask/list");
			$("#searchForm").submit();
	    	return false;
	    }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/asyncTask/list">任务列表</a></li>
<%-- 		<shiro:hasPermission name="sys:asyncTask:view"><li><a href="${ctx}/sys/asyncTask/form">添加任务</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="asyncTask" action="${ctx}/sys/asyncTask/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>任务名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label="全部状态"/>
					<form:option value="0" label="待执行"/>
					<form:option value="1" label="执行中"/>
					<form:option value="2" label="执行成功"/>
					<form:option value="3" label="执行失败"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="20%">任务名称</th>
			<th width="10%">类型</th>
			<th width="10%">状态</th>
			<th width="18%">最后执行日期</th>
			<th width="32%">执行结果</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="asyncTask">
			<tr>
				<td>${asyncTask.name}</td>
				<td>
					<c:choose>
						<c:when test="${asyncTask.type == 1}">视频上传</c:when>
						<c:otherwise>资料</c:otherwise>						
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${asyncTask.status == 1}"><span style="color: #999;">执行中</span></c:when>
						<c:when test="${asyncTask.status == 2}">执行成功</c:when>
						<c:when test="${asyncTask.status == 3}"><span style="color: red;">执行失败</span></c:when>
						<c:otherwise><span style="color: #e78b24;">待执行</span></c:otherwise>						
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${asyncTask.executeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${asyncTask.resultMessage}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>