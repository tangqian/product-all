<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/dataDownload/list");
			$("#searchForm").submit();
	    	return false;
		}
		
		function exportAudience(ids) {
			window.location.href = ("${ctx }/live/dataDownload/export?ids=" + ids);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/dataDownload/list">资料下载列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dataDownloadVo" action="${ctx}/live/dataDownload/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>
					<form:select path="searchType"  class="input-small">
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
			<th width="15%">资料下载观众数</th>
			<th width="15%">资料下载数</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td>${vo.room.name}</td>
				<td>${vo.speaker.company }</td>
				<td>
					<c:choose>
						<c:when test="${vo.attentionCount != 0}">
							<a href="javascript:void(0)" onclick="return exportAudience('${vo.exportAttentionIds}');" title="导出资料下载观众数据">${vo.attentionCount }</a>
						</c:when>
						<c:otherwise>0</c:otherwise>
					</c:choose>
				</td>
				<td>${vo.dataDownloadCount }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>