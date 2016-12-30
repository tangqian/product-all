<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出席观众管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/audience/count/list?roomId=${audience.roomId}");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function exportValidate() {
			var ids = [];
			$("input[name=id]").each(function(i, v){
				ids.push($(this).val());
			})
			window.location.href = ("${ctx }/live/audience/export?ids=" + ids.join(","));
		}
	</script>
</head>
<body>
	<c:set var="roomId" value="${audience.roomId }"/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/audience/count/list?roomId=${audience.roomId}">出席观众列表--${fns:getRoom(roomId).name}</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="audience" action="${ctx}/live/audience/count/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>
					<form:select path="searchType" class="input-small" id="searchType">
						<form:option value="1" label="用户名"/>
						<form:option value="2" label="公司名"/>
						<form:option value="3" label="邮箱"/>
					</form:select>
				</label>
				<form:input id="name" path="name" htmlEscape="false" maxlength="50" class="input-small" cssStyle="margin-left: 38px;width: 180px;"/>
			</li>
			<li>
            <c:if test="${fn:length(page.list)>0 }">
	            <shiro:hasPermission name="live:audienceRegister:export">
		            <li style="float:right;">
		                <a href="#" class="fr fz14 mr20" onclick="return exportValidate();">导出全部观众>></a>
		            </li>
	            </shiro:hasPermission>
            </c:if>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="20%">进入时间</th>
			<th width="20%">用户名</th>
			<th width="15%">姓名</th>
			<th width="20%">公司名</th>
			<th width="10%">职位</th>
			<th width="15%">注册邮箱</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="audience" varStatus="vs">
			<tr>
				<input type="hidden" name="id" value="${audience.id }" class="id${vs.index }"/>
				<td><fmt:formatDate value="${audience.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${audience.user.account }</td>
				<td>${audience.name }</td>
				<td>${audience.company }</td>
				<td>${audience.job }</td>
				<td>${audience.user.email }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	<div class="pagination">${page}</div>
</body>
</html>