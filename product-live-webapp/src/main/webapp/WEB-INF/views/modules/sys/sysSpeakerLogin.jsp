<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${fns:getConfig('productName')}_登录</title>
    <link rel="stylesheet" href="${webSite}/static/web/css/live.css">
    <script src="${webSite}/static/web/plugins/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css"
          rel="stylesheet"/>
    <script src="${webSite}/static/web/plugins/jquery-validation/1.11.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}';</script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#loginForm").validate({
                rules: {
                    validateCode: {remote: "${ctx}/servlet/validateCodeServlet"}
                },
                messages: {
                    username: {required: "请填写用户名"},
                    password: {required: "请填写密码"},
                    validateCode: {remote: "验证码不正确", required: "请填写验证码"}
                },
                errorElement: "p",
                errorClass: "errorp",
                errorPlacement: function (error, element) {
                    if (element.attr("name") == 'validateCode') {
                        error.insertAfter(element.parent());
                    } else {
                        error.appendTo(element.parent());
                    }
                }
            });
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>
</head>
<body>

<div id="g-topbar" class="g-topbar">
    <div class="login_header">
        <div class="wrapper">
            <div class="logo_wrap">
            <a class="fl" target="_blank" href="${webSite}"><img src="${webSite}/static/web/img/logo_live.png" alt=""></a>
            <img class="login_img" src="${webSite}/static/web/img/login-logo.png" alt="">
            </div>
        </div>
    </div>
</div>
<div class="login_fuild">
    <div class="wrapper">
        <div class="login_content">
            <h2>主播 / 客服登录</h2>
            <c:if test="${not empty message}">
                <p class="error_t" style="display: block;">您输入的账号密码不匹配，请重新输入！</p>
            </c:if>
            <form id="loginForm" class="form-signin" action="${ctx}/login?type=3" method="post">
                <input type="hidden" value="3" name="type"/>

                <div class="input_group">
                    <input type="text" id="username" class="required" name="username" value="${username}"
                           placeholder="邮箱/用户名">
                </div>
                <div class="input_group">
                    <input type="password" id="password" class="required" name="password" placeholder="密 码">
                </div>
                <c:if test="${isValidateCodeLogin}">
                    <div class="pass_content clearfix">
                        <input type="text" class="required" maxlength="5" name="validateCode" placeholder="验证码">
                        <img id="validateCodeImg" src="${ctx}/servlet/validateCodeServlet"
                             onclick="$('.validateCodeRefresh').click();" alt=""/>
                        <a href="javascript:;" class="validateCodeRefresh"
                           onclick="$('#validateCodeImg').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">换张图</a>
                    </div>
                </c:if>
                <input class="loginBtn" type="submit" value="登 录">
            </form>
            <div class="login_footer">
                <p>直播客服热线</p>
                <p>联系邮箱：service@smartlifein.com 联系电话：86-755-83279360</p>
            </div>
        </div>
    </div>
</div>

<div class="footer_main">
    <div class="wrapper">
        <a href="http://www.smartlifein.com/" class="footer_logo"><img src="${webSite}/static/web/modules/speaker/img/smartlifein_logo.png" height="55" width="135"></a>
        <div class="about_us">
            <a href="http://live.smartlifein.com/help/" target="_black">常见问题</a>&#x3000;|&#x3000;<a href="http://live.smartlifein.com/contact/" target="_black">联系我们</a>
        </div>
        <span>客服电话：+86-755-83279360&#x3000;&#x3000;邮箱：service@smartlifein.com</span>
    </div>
</div>
</body>
</html>