<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/live/room/list");
			$("#searchForm").submit();
	    	return false;
	    }

		function setTop(isTop){
			var id = $("input[name$='id']:checked");
			if(id.length == 0){
				top.$.jBox.tip("请选择直播！");
				return false;
			}
			var ids = [];
			for(var i=0; i<id.length; i++){
				ids.push($(id[i]).val());
			}
			$.ajax({
				url: "${ctx}/live/room/top",
				data: {
					id: ids,
					isTop: isTop
				},
				type:"post",
				dataType:"json",
				success: function( data ) {
					top.$.jBox.tip("操作成功！");
					page();
				}
			});
		}

		function sort(){
			var order = $("input[name$='topSort']");
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
					url: "${ctx}/live/room/topSort",
					data: {
						ids: ids,
						orders: orders
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

        $(document).ready(function(){
            $("a[name='status']").click(function(){
                var id = $(this).attr("href");
                top.$.jBox.open("iframe:${ctx}/live/room/statusForm?id=" + id, "修改直播状态",400,200,{
                    buttons:{"确定":"ok", "关闭":true}, bottomText:"",submit:function(v, h, f){
                        var status = h.find("iframe")[0].contentWindow.checkId;
                        if (v == "ok"){
                            // 执行保存
                            loading('正在提交，请稍等...');
                            $.ajax({
                                type: "POST",
                                url: "${ctx}/live/room/updateStatus",
                                data: { id : id, status: status },
                                success: function(msg){
                                    top.$.jBox.tip("修改直播状态成功", 'info');
                                    page();
                                },
                                error : function(){
                                    top.$.jBox.tip("操作失败,服务器出错了！！！", 'error');
                                }
                            });
                            return true;
                        }
                    }, loaded:function(h){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
                return false;
            });
        });
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/live/room/list">直播列表</a></li>
		<shiro:hasPermission name="live:room:edit"><li><a href="${ctx}/live/room/form">直播添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="room" action="${ctx}/live/room/list" method="post" class="breadcrumb form-search ">
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
						<form:option value="0" label="待举办"/>
						<form:option value="2" label="进行中"/>
						<form:option value="4" label="已结束"/>
					</form:select>
				</label>
			</li>
			<li>
				<label>
					<form:select path="publishMode"  class="input-small" cssStyle="margin-left: 35px;">
						<form:option value="" label="全部模式"/>
						<form:option value="0" label="视频/ppt"/>
						<form:option value="1" label="图文直播"/>
					</form:select>
				</label>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" style="margin-left: 70px;"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th width="3%">选项</th>
			<th width="18%">直播主题</th>
			<th width="6%">直播模式</th>
			<th width="13%">公司</th>
			<th width="22%">举行时间</th>
			<th width="5%">状态</th>
			<th width="5%">在线观众</th>
			<th width="5%">登记观众</th>
			<th width="5%">出席观众</th>
			<th width="4%">游客</th>
			<th width="4%">人气</th>
			<th width="4%">显示</th>
			<shiro:hasAnyPermissions name="live:room:edit,live:room:delete">
				<th width="25%">操作</th>
			</shiro:hasAnyPermissions>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="room" varStatus="st">
			<tr>
				<td>
					<input type="checkbox" id="pid${st.index }" name="forms[${st.index }].id" value="${room.id }"/>
				</td>
				<td>
					<shiro:hasPermission name="live:room:check">
						<a href="${ctx}/live/room/check?id=${room.id}" target="_blank">${room.name}</a>
					</shiro:hasPermission>
					<shiro:lacksPermission name="live:room:check">
						<a href="http://live.smartlifein.com/live/${room.id }" target="_blank">${room.name}</a>
					</shiro:lacksPermission>
					<c:if test="${room.isTop == 1}">
						<br/><span style="color: #c00">【置顶】</span>
						<input type="text" class="input-min" name="forms[${st.index }].topSort" value="${room.topSort }"/>
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${room.publishMode == 0}">视频/ppt</c:when>
						<c:when test="${room.publishMode == 1}">图文直播</c:when>
					</c:choose>
				</td>
				<td>${room.company }</td>
				<td>
					<fmt:formatDate value="${room.startTime }" pattern="yyyy-MM-dd HH:mm"/>
					至
					<fmt:formatDate value="${room.bgEndTime }" pattern="yyyy-MM-dd HH:mm"/>
					<span style="color: black">
					<br/>对外显示时间：<br/>
					<fmt:formatDate value="${room.startTime }" pattern="yyyy-MM-dd HH:mm"/>
					至
					<fmt:formatDate value="${room.endTime }" pattern="yyyy-MM-dd HH:mm"/>
					</span>
				</td>
				<td>
					<shiro:hasPermission name="live:room:edit">
					<a name="status" href="${room.id}" title="点击修改状态">
						</shiro:hasPermission>
							${fns:getRoomStatus(room.status).meaning}
						<shiro:hasPermission name="live:room:edit">
					</a>
					</shiro:hasPermission>
				</td>
				<td>${room.onLineAudienceCount}</td>
				<td>
					<c:choose>
						<c:when test="${room.registerAudienceCount == 0 }">0</c:when>
						<c:otherwise>
							<a href="${ctx}/live/audienceRegister/count/list?roomId=${room.id }" class="blue">${room.registerAudienceCount }</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${room.presentAudienceCount == 0 }">0</c:when>
						<c:otherwise>
							<a href="${ctx}/live/audience/count/list?roomId=${room.id }" class="blue">${room.presentAudienceCount }</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td>${room.visitorCount }</td>
				<td>${room.pv }</td>
				<td>
					<c:choose>
						<c:when test="${room.isShow == 0}">是</c:when>
						<c:when test="${room.isShow == 1}"><span style="color: red;">否</span></c:when>
					</c:choose>
				</td>
				<shiro:hasAnyPermissions name="live:room:edit,live:room:delete">
					<td>
						<shiro:hasPermission name="live:room:edit">
							<a href="${ctx}/live/room/form?id=${room.id}">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="live:room:delete">
							<a href="${ctx}/live/room/delete?id=${room.id}" onclick="return confirmx('确认要删除该直播吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</td>
				</shiro:hasAnyPermissions>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="live:room:top">
		<c:if test="${not empty page.list}">
			<div>
				<button class="btn btn-primary" onclick="setTop(1)">置顶</button>
				<button class="btn btn-primary" onclick="setTop(0)">取消置顶</button>
				<button class="btn btn-primary" onclick="sort()">排序</button>
			</div>
		</c:if>
	</shiro:hasPermission>
</body>
</html>