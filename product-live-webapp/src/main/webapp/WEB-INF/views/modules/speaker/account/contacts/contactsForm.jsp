<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="speaker"/>
    <title>联系方式</title>
    <script type="text/javascript" src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#sm_speaker_contacts").addClass('menu-son-cur');
            $("#headCrumbs").append("<span class='em1'>></span>").append("<span>联系方式</span>");
        })
    </script>
   	<style type="text/css">
   		.error {
    		color: #ff0000;
    	}
    </style>
    <script type="text/javascript">
    	$(function(){
    		$("#contactsForm").validate({
                debug: false,
                errorClass: "error",
                focusInvalid: true,
                submitHandler: function(form) {
                    form.submit();
                }
            });
    	});
    </script>
</head>

<body>
<!-- 右 -->
<div class="fr w830">
	<h1 class="page-title">联系方式</h1>
	<!-- 联系方式 -->
	<div class="mt5 account-container">
		<c:if test="${result == 'success' }">
			<p class="acc-suc">
				<img src="${webSite}/static/web/modules/speaker/img/right.png" height="24" width="24" />
				<span>更新信息成功</span>
			</p>
		</c:if>
		<form id="contactsForm" method="post" action="/speaker/account/save">
		<input type="hidden" name="id" id="id" value="${speaker.id }">
			<dl class="clearfix upc-box">
				<dt>公司名称：</dt>
				<dd>
					<input type="hidden" class="fl upc-name" name="company" id="company" value="${speaker.company }"/>
					<p class="line-h30">${speaker.company }</p>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>联系人：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="name" id="account" value="${speaker.name }" maxlength="50"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>部<span class="em1"></span>门：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="department" id="department" value="${speaker.department }" maxlength="50"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>职<span class="em1"></span>位：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="job" id="job" value="${speaker.job }" maxlength="50"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>手<span class="em1"></span>机：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="mobilePhone" id="mobilePhone" value="${speaker.mobilePhone }" maxlength="50"/>
					<p class="fl tips acc-tips error-tips"></p>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>固定电话：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="telephone" id="telephone" value="${speaker.telephone }" maxlength="50"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>公司地址：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="address" id="address" value="${speaker.address }" maxlength="200"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>网址：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="url" id="url" value="${speaker.url }" maxlength="200"/>
				</dd>
			</dl>
			<dl class="clearfix upc-box">
				<dt>邮箱：</dt>
				<dd>
					<input type="text" class="fl upc-name" name="email" id="email" value="${speaker.email }" maxlength="200"/>
				</dd>
			</dl>
			<input type="submit" value="保 存" class="button-b upc-save">
		</form>
	</div>
</div>
</body>
</html>