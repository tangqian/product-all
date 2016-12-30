<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="title" value="${not empty waiter.id?'修改':'添加'}"/>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>${title}客服</title>
    <link rel="stylesheet" href="${webSite}/static/web/plugins/uploadify/css/uploadify.css" />
    <script type="text/javascript" src="${webSite}/static/web/plugins/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${webSite}/static/web/js/speaker/waiter/waiter.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_waiter_manage").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<a href='${ctx}/speaker/waiter/list'>设置客服</a>")
                    .append("<span class='em1'>></span>").append("<span>${title}客服</span>");
        })
        
        var sessionId = "${pageContext.session.id }";
    </script>
    <style type="text/css">
   		.error {
    		color: #ff0000;
    	}
    	img {
    		max-width: 125px; max-width: 125px; 
    	}
    </style>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
    <h1 class="page-title">${title}客服</h1>
    <!-- 上传视频 -->
	<div class="mt5 account-container">
    <form id="waiterForm" action="/speaker/waiter/save" method="post">
    <input type="hidden" id="id" name="id" value="${waiter.id }"/>
    <input type="hidden" id="logoId" name="logoId"/>
        <dl class="clearfix upc-box">
            <dt><span class="red-start">*</span>登录帐号：</dt>
            <dd>
                <input <c:if test="${not empty waiter.id }">readonly="readonly"</c:if> type="text" name="user.account" id="account" class="fl upc-name" minlength="6" maxlength="50" value="${waiter.user.account }"/>
            </dd>
        </dl>
        <dl class="clearfix upc-box">
            <dt><span class="red-start">*</span>密<span class="em2"></span>码：</dt>
            <dd>
                <input type="password" name="user.password" id="password" class="fl upc-name <c:if test="${empty waiter.id }">required</c:if>" minlength="6"/>
                <!-- 如果是修改客服，则显示下面的提示（没有class：error-tips） -->
                <c:if test="${not empty waiter.id }"><p class="fl tips acc-tips">若密码不变，请勿改动</p></c:if>
                </dd>
            </dl>
            <dl class="clearfix upc-box">
                <dt><span class="red-start">*</span>确认密码：</dt>
                <dd>
                    <input type="password" name="user.newPassword" id="newPassword" class="fl upc-name <c:if test="${empty waiter.id }">required</c:if>" equalTo="#password" minlength="6"/>
                    <p class="fl tips acc-tips"></p>
                </dd>
            </dl>
            <dl class="clearfix upc-box">
                <dt><span class="red-start">*</span>姓<span class="em2"></span>名：</dt>
                <dd>
                	<input type="text" name="name" id="name" class="fl upc-name" value="${waiter.name }" maxlength="50"/>
                    <p class="fl tips acc-tips"></p>
                </dd>
            </dl>
            <dl class="clearfix upc-box">
                <dt>头<span class="em2"></span>像：</dt>
                <dd>
                    <input type="file" name="file" class="fl button-a acc-btn" id="fileField" />
                    <c:choose>
                    	<c:when test="${empty waiter.id }">
		                	<img id="logoUri" /><!-- 添加 -->
                    	</c:when>
                    	<c:otherwise>
	                    	<c:choose>
	                    		<c:when test="${not empty waiter.logoId }"><!-- 修改 -->
			              			<img id="logoUri" src="${webSite }/static/upload/${sysFile.uri }"/>
	                    		</c:when>
	                    		<c:otherwise>
	                    			<img id="logoUri" /><!-- 添加 -->
	                    		</c:otherwise>
	                    	</c:choose>
                    	</c:otherwise>
                    </c:choose>                    
                </dd>
            </dl>
            <input type="submit" value="保 存" class="button-b upc-save" />
        </form>
    </div>
</div>
</body>
</html>