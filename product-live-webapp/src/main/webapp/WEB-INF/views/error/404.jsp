<%
response.setStatus(404);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print("页面不存在.");
}

//输出异常信息页面
else {
%>
<%@page import="com.ofweek.live.core.common.web.Servlets"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>404 - 页面不存在</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>页面不存在.</h1></div>
		<div><a href="javascript:" onclick="goBack();" class="btn">返回上一页</a></div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
<script type="application/javascript">
	/**
	 * 返回前一页（或关闭本页面）
	 * <li>假设没有前一页历史。则直接关闭当前页面</li>
	 */
	function goBack(){
		if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
			if(history.length > 0){
				window.history.go( -1 );
			}else{
				window.opener=null;window.close();
			}
		}else{ //非IE浏览器
			if (navigator.userAgent.indexOf('Firefox') >= 0 ||
					navigator.userAgent.indexOf('Opera') >= 0 ||
					navigator.userAgent.indexOf('Safari') >= 0 ||
					navigator.userAgent.indexOf('Chrome') >= 0 ||
					navigator.userAgent.indexOf('WebKit') >= 0){

				if(window.history.length > 1){
					window.history.go( -1 );
				}else{
					window.opener=null;window.close();
				}
			}else{ //未知的浏览器
				window.history.go( -1 );
			}
		}
	}
</script>
</body>
</html>
<%
out.print("<!--"+request.getAttribute("javax.servlet.forward.request_uri")+"-->");
} out = pageContext.pushBody();
%>