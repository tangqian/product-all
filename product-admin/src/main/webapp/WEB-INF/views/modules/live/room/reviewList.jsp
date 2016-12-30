<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播回顾</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/review/list");
			$("#searchForm").submit();
	    	return false;
	    }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/review/list">直播回顾列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="roomReviewData" action="${ctx}/live/review/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>
					<form:select path="room.searchType"  class="input-small">
						<form:option value="1" label="直播主题"/>
						<form:option value="2" label="举办公司"/>
					</form:select>
				</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-small" cssStyle="margin-left: 38px;width: 180px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="20%">直播主题</th>
			<th width="20%">公司</th>
			<th width="20%">举行时间</th>
			<th width="10%">回顾资料</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="review">
			<tr>
				<td>${review.room.name}</td>
				<td>${review.room.company }</td>
				<td>
					<fmt:formatDate value="${review.room.startTime }" pattern="yyyy-MM-dd HH:mm"/>
					至
					<fmt:formatDate value="${review.room.endTime }" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td><a href="${ctx}/live/review/data/list?roomId=${review.room.id}">查看</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>