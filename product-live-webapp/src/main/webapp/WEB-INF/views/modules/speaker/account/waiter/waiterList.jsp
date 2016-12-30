<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>设置客服</title>
    <link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_waiter_manage").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<span>设置客服</span>");
        })
        
        var sessionId = "${pageContext.session.id }";
      	function tips(count) {
	   		var limit = '${maxLimit }';
	   		if(count >= limit){
	   			alert("您最多可以设置" + count + "个客服帐号!");
	   			return false;
	   		}
	   		return true;
   		}
    </script>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">设置客服</h1>
    <div class="mt15">
        <p class="tips">您最多可以设置${maxLimit }个客服帐号(包括主帐号)。客服帐号的用途：直播期间可以在互动区解答观众提问，与观众互动。</p>
    </div>
    <div class="clearfix mt15">
        <a href="${ctx}/speaker/waiter/form" class="button-upload button-add" onclick="return tips(${totalCount});">添加客服</a>
    </div>
    <!-- 设置客服 -->
	<div class="mt20 set-service">
    	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table">
       		<tr>
	            <th width="300" style="text-indent: 35px;">姓名</th>
			    <th width="240" class="align-center" style="border-right: 1px solid #dbdbdb; border-left: 1px solid #dbdbdb;">登录账号</th>
			    <th style="text-indent: 35px;">操作</th>
			</tr>
            <tr>
	            <td>
		            <img src="${webSite }/static/web/modules/speaker/img/icon-service-speaker.png" height="32" width="32" class="service-photo" />
		            <span class="blue service-name">${speaker.name }</span>
	            </td>
	            <td class="align-center">${speaker.user.account }</td>
            </tr>
			<c:forEach items="${page.list}" var="waiter" varStatus="listStatus">
            <tr>
                <td>
                <c:choose>
	               	<c:when test="${not empty waiter.logoId }">
	               		<img src="${webSite }/static/upload/${waiter.uri }" height="32" width="32" class="service-photo" />
	               	</c:when>
	               	<c:otherwise>
	               		<img src="${webSite }/static/web/modules/speaker/img/icon-service.png" height="32" width="32" class="service-photo" />
	               	</c:otherwise>
                </c:choose>
				<span class="blue service-name">${waiter.name }</span>
                </td>
                <td class="align-center">${waiter.user.account }</td>
                <td class="setSer-handle">
                    <input type="button" value="修改" class="button-a update-btn" waiterId="${waiter.id }"/>
                    <input type="button" value="删除" class="button-a delete-btn" waiterId="${waiter.id }"/>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/speaker/js/main.js"></script>
<script type="text/javascript" src="${webSite}/static/web/js/speaker/waiter/waiter.js"></script>
</body>
</html>