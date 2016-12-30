<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>展商公司</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/exhibitor/exhibitor/live2Exhibitors");
		$("#searchForm").submit();
		return false;
	}
	
	$(function(){
		$("#btnExh").on("click", function(){
			var exh = $("input[type=radio]:checked").val();
			if (exh == undefined) {
				alert("请选择展商!");
				return ;
			}
			
			var tr = $("input[type=radio]:checked").parents("tr");
			var account = $(tr).find("td:eq(1)").text();
			var company = $(tr).find("td:eq(2)").text();
			var industryIds = "," + $(tr).find("input[name=industryIds]").val() + ",";
			var name = $(tr).find("input[name=name]").val();
			var job = $(tr).find("input[name=job]").val();
			var mobilePhone = $(tr).find("input[name=mobilePhone]").val();
			var telephone = $(tr).find("input[name=telephone]").val();
			var email = $(tr).find("input[name=email]").val();
			var fax = $(tr).find("input[name=fax]").val();
			var sex = $(tr).find("input[name=sex]").val();
			var department = $(tr).find("input[name=department]").val();
			var address = $(tr).find("input[name=address]").val();
			
			var params = ["account=" + account, "company=" + company, "industry=" + industryIds, "source=2"
			  			, "name=" + name, "job=" + job, "mobilePhone=" + mobilePhone, "telephone=" + telephone
			  			, "email=" + email, "fax=" + fax, "sex=" + sex, "department=" + department, "address=" + address];
			window.location.href = "${ctx}/live/speaker/form?" + params.join("&");
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/live/speaker/list">直播公司列表</a></li>
		<shiro:hasPermission name="live:speaker:edit"><li><a href="${ctx}/live/speaker/form?source=2">直播公司添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/exhibitor/exhibitor/live2Exhibitors">展商列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="exhibitor" action="${ctx}/exhibitor/exhibitor/live2Exhibitors" method="post" class="breadcrumb form-search ">
		<form:hidden path="id"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>公司名称：</label> <form:input path="company" htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message }"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="3%"></th>
				<th width="25%">帐号</th>
				<th width="25%">公司名称</th>
				<th width="27%">行业</th>
				<th width="20%">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="vo" varStatus="vs">
				<tr>
					<td>
						<input type="radio" name="id" value="${vo.id }" class="exh-${vo.id }" id="exh-${vo.id }" />
						<input type="hidden" name="name" value="${vo.name }">
						<input type="hidden" name="job" value="${vo.job }">
						<input type="hidden" name="mobilePhone" value="${vo.mobilePhone }">
						<input type="hidden" name="telephone" value="${vo.telephone }">
						<input type="hidden" name="email" value="${vo.email }">
						<input type="hidden" name="fax" value="${vo.fax }">
						<input type="hidden" name="sex" value="${vo.sex }">
						<input type="hidden" name="department" value="${vo.department }">
						<input type="hidden" name="address" value="${vo.address }">
					</td>
					<td>${vo.account}</td>
					<td>${vo.company}</td>
					<td><input type="hidden" name="industryIds" value="${vo.industryIds }">${vo.industryText}</td>
					<td><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="form-actions">
		<input id="btnExh" class="btn btn-primary" type="submit" value="保 存"/>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>