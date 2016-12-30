<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料审核审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/data/list");
			$("#searchForm").submit();
	    	return false;
		}
		
		var allBtn = {};
		
		function pushAuthority(){
			allBtn.审核通过 = 'pass';
			allBtn.审核不通过 = 'nopass';
		}
		
		function audit(id, roomId, type) {
		    var typeStr, buttonObj;
		    var width = 600;
		    var height = 450;
		    if (type == "speakerVideo") {
		        typeStr = "视频";
		        width = 700;
		        height = 500;
			<shiro:hasPermission name="	live:video:audit">
				pushAuthority();
			</shiro:hasPermission>
		    } else if (type == "speakerData") {
		        typeStr = "资料";
		        height = 200;
			<shiro:hasPermission name="live:data:audit">
				pushAuthority();
			</shiro:hasPermission>
		    } else if (type == "speakerSpeech") {
		        typeStr = "演讲稿";
		        height = 200;
			<shiro:hasPermission name="live:speech:audit">
				pushAuthority();
			</shiro:hasPermission>
		    }
		    allBtn.关闭 = true;
		    var status;
		    top.$.jBox.open("iframe:${ctx}/live/" + type + "/form?id=" + id + "&roomId=" + roomId, typeStr + "审核", width, height, {
		        buttons: allBtn,
		        submit: function(v, h, f) {
		            if (v == "pass") {
		                status = 3;
		            } else if (v == "nopass") {
		                status = 4;
		            }
		            if (status != undefined) {
		                loading('正在提交，请稍等...');
		                $.ajax({
		                    type: "POST",
		                    url: "${ctx}/live/" + type + "/audit",
		                    data: {
		                        id: id,
		                        roomId: roomId,
		                        status: status
		                    },
		                    success: function(msg) {
		                        top.$.jBox.tip("审核成功");
		                        window.location.reload();
		                    },
		                    error: function() {
		                        top.$.jBox.tip("审核失败,服务器出错了！！！", 'error');
		                    }
		                });
		                return true;
		            }
		        },
		        loaded: function(h) {
		            $(".jbox-content", top.document).css("overflow-y", "hidden");
		        },
		        closed: function() {
		        	if (status != 3 && status != 4) {
		            	location.reload();
		        	}
	            }
		    });
		    allBtn = {};
		    return false;
		}
		
		
		function syncCloud(){
			if(confirm("确定同步？")){
				$.ajax({
					url: "${ctx}/live/data/syncVideoToCloud",
					type:"post",
					dataType:"json",
					success: function( data ) {
						if(data && data.code == 0){
							alert(data.message);
							location.reload();
						}
						else{
							alert(data.message);
						}
					}
				});
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/data/list">资料审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dataAuditVo" action="${ctx}/live/data/list" method="post" class="breadcrumb form-search ">
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
			<li>
				<label>
					<form:select path="status"  class="input-small">
						<form:option value="" label="全部状态"/>
						<form:option value="0" label="待审核"/>
						<form:option value="3" label="审核通过"/>
						<form:option value="4" label="审核未通过"/>
					</form:select>
				</label>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" style="margin-left: 40px;"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="20%">直播主题</th>
			<th width="20%">公司</th>
			<th width="25%">资料</th>
			<th width="20%">资料提交时间</th>
			<th width="10%">资料状态</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td>${vo.room.name}</td>
				<td>${vo.speaker.company }</td>
				<td>
					<c:choose>
						<c:when test="${vo.type == 1}">
							<a href="javascript:void(0)" onclick="return audit('${vo.id}', '${vo.room.id}', 'speakerVideo');" title="${vo.dataName}">
								${vo.dataName }
							</a>
							[视频]
						</c:when>
						<c:when test="${vo.type == 2}">
							<a href="javascript:void(0)" onclick="return audit('${vo.id}', '${vo.room.id}', 'speakerData');" title="${vo.dataName}">
								${vo.dataName }
							</a>
							[资料]
						</c:when>
						<c:when test="${vo.type == 3}">
							<a href="javascript:void(0)" onclick="return audit('${vo.id}', '${vo.room.id}', 'speakerSpeech');" title="${vo.dataName}">
								${vo.dataName }
							</a>
							[演讲稿]
						</c:when>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${vo.createDate }" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${vo.status == 0}"><span style="color: #999;">待审核</span></c:when>
						<c:when test="${vo.status == 3}">审核通过</c:when>
						<c:when test="${vo.status == 4}"><span style="color: red;">审核未通过</span></c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
    <shiro:hasPermission name="live:data:view">
    <div>
        <c:if test="${fn:length(page.list)>0}">
            <button class="btn" onclick="syncCloud()">同步到腾讯云</button><br>
            <span style="color: red;">该命令会同步全部未结束直播房间下的视频</span>
        </c:if>
    </div>
    </shiro:hasPermission>
	<div class="pagination">${page}</div>
</body>
</html>