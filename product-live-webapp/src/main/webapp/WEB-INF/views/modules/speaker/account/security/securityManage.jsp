<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>账号安全</title>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_security_manage").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<span>账号安全</span>");
        })
    </script>
    <style type="text/css">
   		.error {
    		color: #ff0000;
    	}
    </style>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
	<h1 class="page-title">账号安全</h1>
	<!-- 上传视频 -->
	<div class="mt5 account-container">
		<c:if test="${result == 'success' }">
			<p class="acc-suc">
				<img src="${webSite}/static/web/modules/speaker/img/right.png" height="24" width="24" />
				<span>更新信息成功</span>
			</p>
		</c:if>
		<form>
			<dl class="clearfix upc-box">
				<dt>登录邮箱：</dt>
				<dd>
					<!-- 登录邮箱 -->
					<p class="fl line-h30">${user.email }&nbsp;&nbsp;&nbsp;</p>
					<!-- 邮箱验证状态 -->
					<!-- 通过|pass|绿色  待验证|wait-pass|灰色  未通过|not-pass|红色  -->
					<!-- <span class="fl wait-pass account-state">待验证</span>-->
					<a href="/speaker/account/emailForm?email=${user.email }" class="fl button-a">修改登录邮箱</a>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>密<span class="em2"></span>码：</dt>
				<dd>
					<a href="/speaker/account/passwordForm" class="fl button-a">修改密码</a>
				</dd>
			</dl>
		</form>
	</div>
</div>
</body>
</html>