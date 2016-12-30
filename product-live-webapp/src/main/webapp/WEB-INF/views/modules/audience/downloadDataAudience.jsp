<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta name="decorator" content="speaker" />
		<title>下载资料观众</title>
		<link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
		<link rel="stylesheet" href="${webSite}/static/web/css/jeesite.css" />
		<link rel="stylesheet" href="${webSite}/static/web/css/bootstrap.css" />
	</head>

	<body>
		<!-- 右 -->
		<div class="fr w830">
			<c:set var="roomId" value="${roomId }"/>
			<h1 class="page-title">下载资料观众<span class="page-title-min">（${fns:getRoomById(roomId).name}） </span></h1>
			<!-- 下载资料观众 -->
			<div class="mt20 set-service">
			<form:form id="downloadDataAudienceForm" method="post" modelAttribute="audience">
				<div class="clearfix mt10">
					<!-- 导出名单 -->
					<a href="/speaker/audience/export?expType=download&roomChat.roomId=${roomId }" class="button-a fl">导出名单</a>
					<div class="fr">
						<!-- 查询 -->
						<form:input type="text" class="upc-name fl query-input" path="name"/>
						<form:select class="fl query-select" path="searchType">
							<form:option value="">全部</form:option>
							<form:option value="1">公司</form:option>
							<form:option value="2">姓名</form:option>
						</form:select>
						<input type="submit" value="查询" class="fl button-b query-submit" />
					</div>
				</div>
				</br>
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table data-table">
						<tr>
							<th width="140" class="align-center">公司</th>
							<th width="60" class="align-center" style="border-left: 1px solid #dbdbdb;">姓名</th>
							<th width="90" class="align-center" style="border-left: 1px solid #dbdbdb;">部门</th>
							<th width="90" class="align-center" style="border-left: 1px solid #dbdbdb;">职位</th>
							<th width="120" class="align-center" style="border-left: 1px solid #dbdbdb;">联系方式</th>
							<th width="210" class="align-center" style="border-left: 1px solid #dbdbdb;">下载资料</th>
							<th width="110" class="align-center" style="border-left: 1px solid #dbdbdb;">下载时间</th>
						</tr>
						<c:forEach items="${page.list}" var="item" varStatus="listStatus">
						<tr>
							<td class="align-left">${item.company }</td>
							<td class="align-center">
								${item.name }
							</td>
							<td class="align-center">
								${item.department }
							</td>
							<td class="align-center">
								${item.job }
							</td>
							<td class="align-center">
								<c:choose>
                            		<c:when test="${item.mobilePhone ne '' && item.telephone ne '' }">
		                            	${item.mobilePhone },<br />${item.telephone }
                            		</c:when>
                            		<c:otherwise>
	                            		<c:choose>
	                            			<c:when test="${item.mobilePhone ne '' }">
			                            		${item.mobilePhone }
	                            			</c:when>
	                            			<c:when test="${item.telephone ne '' }">
			                            		${item.telephone }
	                            			</c:when>
	                            		</c:choose>
                            		</c:otherwise>
                            	</c:choose>
							</td>
							<td class="align-left">
								${item.speakerData.name }
							</td>
							<td class="align-center">
								<fmt:formatDate value="${item.speakerData.createDate }" pattern="yyyy-MM-dd HH:mm" />
							</td>
						</tr>
						</c:forEach>
					</table>
				    <div class="clearfix mt20">
                    <!-- 导出名单 -->
                    	<a href="/speaker/audience/export?expType=download&roomChat.roomId=${roomId }" class="button-a fl">导出名单</a>
                	</div>
					<c:if test="${fn:length(page.list) > 0 }">
						<div class="pagination">${page}</div>
					</c:if>
				</form:form>	
			</div>
		</div>		
		<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#sm_audience_data").addClass('menu-son-cur');
				$("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/audience/data'>观众数据</a>")
					.append("<span class='em1'>></span>").append("<span>下载资料观众</span>");
			});

			function page(n, s) {
				if(n) $("#pageNo").val(n);
				if(s) $("#pageSize").val(s);
				$("#downloadDataAudienceForm").attr("action", "${webSite }/speaker/audience/downloadData?roomChat.roomId=${roomId }");
				$("#downloadDataAudienceForm").submit();
				return false;
			}
		</script>
	</body>
</html>