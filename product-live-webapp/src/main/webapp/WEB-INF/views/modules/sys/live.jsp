<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ofweek.live.core.modules.sys.enums.UserTypeEnum" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="v" value="<%=System.currentTimeMillis()%>"/>
<c:set var="AUDIENCE_CODE" value="<%=UserTypeEnum.AUDIENCE.getCode()%>"/>
<c:set var="SPEAKER_CODE" value="<%=UserTypeEnum.SPEAKER.getCode()%>"/>
<c:set var="AUDIENCE_CODE" value="<%=UserTypeEnum.AUDIENCE.getCode()%>"/>
<c:set var="AUDIENCE_CODE" value="<%=UserTypeEnum.AUDIENCE.getCode()%>"/>
<!DOCTYPE html>
<html>
<head lang="zh-cmn-Hans">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">
    <title>${room.name}_${room.speaker.company} - 智慧生活网直播</title>
    <meta name="keywords" content="公司直播，产品直播，${room.name}，${room.speaker.company}">
    <meta name="description" content="${fns:unescapeHtml(room.summary)}">
    <link rel="shortcut icon" type="image/x-icon" href="http://www.smartlifein.com/favicon.ico" />
    <link rel="stylesheet" href="http://www.smartlifein.com/statics/css/live.css" />
    <script>
        function login(loginType, userId, roomId, nonce, mode, ak, sessionId) {
            window.loginKeys = {
                loginType: loginType,
                userId: userId,
                roomId: roomId,
                nonce: nonce,
                mode: mode,
                ak: ak,
                sessionId : sessionId
            };
        }
        login(${vo.loginType}, ${vo.userId}, ${room.id}, "${vo.nonce}", ${mode}, "${vo.ak}", "${pageContext.session.id}");
    </script>
    <link href="${webSite}/static/web/modules/live/css/index.${cssIndex}.css" rel="stylesheet">
</head>
<body>
<!-- header -->
<div class="header">
    <div class="header-wrap">
        <a href="javascript:" class="logo"><img src="http://www.smartlifein.com/statics/images/live/logo.png" height="50" width="225" /></a>
        <div class="nav-wrap">
            <ul class="clearfix nav">
                <li  class="current" >
                    <a href="${webSite}" class="nav-item" target="_blank" style="border:0;">首页</a>
                </li>
                <li>
                    <a href="/commingExh/" class="nav-item">正在直播</a>
                    <c:if test="${fn:length(headerLive) > 0}">
                        <div class="nav-sub">
                            <c:forEach items="${headerLive}" var="live" varStatus="vs">
                                <c:if test="${live.status == 2}">
                                    <a href="${webSite}/live/${live.id}" target="_blank">${live.name}</a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </li>
                <li>
                    <a href="/upcommingExh/" class="nav-item">直播预告</a>
                    <div class="nav-sub">
                        <c:forEach items="${headerLive}" var="live" varStatus="vs">
                            <c:if test="${live.status == 0}">
                                <a href="${webSite}/live/${live.id}" target="_blank">${live.name}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </li>
                <li>
                    <a href="/endedExh/" class="nav-item">直播回顾</a>
                    <div class="nav-sub">
                        <c:forEach items="${headerLive}" var="live" varStatus="vs">
                            <c:if test="${live.status == 4}">
                                <a href="${webSite}/live/${live.id}" target="_blank">${live.name}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </li>
                <li >
                    <a href="${webSite}/help/" class="nav-item" target="_blank">直播指南</a>
                </li>
                <li >
                    <a href="${webSite}/contact/" class="nav-item" target="_blank">联系我们</a>
                </li>
            </ul>
        </div>

        <c:if test="${empty user}">
        <div class="header-links" id="header-links">
            <a href="javascript:" class="hl-login">观众登录</a>
            <a href="http://www.smartlifein.com/register.html" class="hl-reg" target="_blank">观众注册</a>
            <a href="http://live.smartlifein.com/login?type=3" class="hl-company-entry" target="_blank">企业入口</a>
        </div>
        </c:if>

        <c:if test="${not empty user}">
            <!-- 已登录 -->
            <div class="logined" id="header-links">
                欢迎您：
                <c:choose>
                    <c:when test="${user.type == AUDIENCE_CODE}">
                        <a href="http://www.smartlifein.com/index.php?m=member&c=index" class="username" target='_blank'>${user.account}</a>
                        | <a href="http://live.smartlifein.com/viplogout">退出</a>
                    </c:when>
                    <c:when test="${user.type == SPEAKER_CODE}">
                        <a href="/room" class="username" target='_blank'>${user.account}</a>
                        | <a href="/logout">退出</a>
                    </c:when>
                    <c:otherwise>
                        ${user.account}
                        | <a href="/logout">退出</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</div>

<!-- banner -->
<div class="banner-wrap">
    <div class="banner">
        <c:set var="coverId" value="${room.coverId}"/>
        <ul class="banner-img">
            <li><img src="${fns:getSysFile(coverId).url}" width="1080" height="800" alt="" /></li>
        </ul>
    </div>
</div>

<div class="main-body" ms-controller="mainBody">
    <xmp ms-widget="[@config, {is:'mainBody', $id: 'mainBodyContent'}]"></xmp>
</div>

<!-- footer -->
<div class="footer">
    <div class="footer-hd">
        <a href="${webSite}" class="footer-logo" target="_blank"><img src="http://www.smartlifein.com/statics/images/live/logo2.png" height="59" width="151"></a>
        <div class="fr footer-txt">
            <div class="fl footer-links">
                <a target="_blank" href="http://www.smartlifein.com/service/aboutus.html">关于我们</a> |
                <a target="_blank" href="http://live.smartlifein.com/contact/">联系我们</a> |
                <a href="http://www.smartlifein.com/login.html" target="_blank">登录</a>|
                <a href="http://www.smartlifein.com/register.html" target="_blank">注册</a>|
                <a href="http://www.smartlifein.com/service/tougao.html" target="_blank">投稿</a>
            </div>
            <span class="ml40">客服电话：+86-755-83279360</span>
            <span class="ml40">邮箱：service@smartlifein.com</span>
        </div>
    </div>
    <div class="footer-bd">
        <a target="_blank" href="http://www.miitbeian.gov.cn" rel="nofollow" class="copyright">Copyright © 智慧生活网 (粤ICP备06087881号-17)</a>
        <img src="http://www.smartlifein.com/statics/images/live/jc.gif" height="34" width="76" style="vertical-align:middle;">
    </div>
</div>

<script src="${webSite}/static/web/modules/live/js/config.js?v=${v}"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/live/js/vendor.${jsVendor}.js"></script>
<script type="text/javascript" src="${webSite}/static/web/modules/live/js/index.${jsIndex}.js"></script>

<script type="text/javascript" src="http://www.smartlifein.com/statics/js/live.js"></script>
<script type="text/javascript" src="http://www.smartlifein.com/statics/js/jquery.cookie.js"></script>
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" >
    var jiathis_config = {
        summary:"",
        shortUrl:false,
        hideMore:false
    };

    $('body').on({
        mouseover: function() {
            jiathis_config.url = $(this).find('.theme-banner a').attr('href');
            jiathis_config.title = $(this).find('.theme-title a').text();
            jiathis_config.summary = $(this).find('.theme-intro').text();
            jiathis_config.pic = $(this).find('.theme-banner img').attr('src');
        }
    }, '.theme');

</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->

<script type="text/javascript" >
    var jiathis_config = {
        summary:"",
        shortUrl:false,
        hideMore:false
    };

    $('body').on({
        mouseover: function() {
            jiathis_config.url = $(this).find('.theme-banner a').attr('href');
            jiathis_config.title = $(this).find('.theme-title a').text();
            jiathis_config.summary = $(this).find('.theme-intro').text();
            jiathis_config.pic = $(this).find('.theme-banner img').attr('src');
        }
    }, '.theme');

</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->

<script type="text/javascript">
    // 加载用户登录弹窗
    function userLogin() {
        $.ajax({
            url:'http://live.smartlifein.com/getlogin/',
            type:'GET',
            dataType: 'html',
            success: function(data) {
                data = data.replace('&lt;', '<').replace('&gt;', '>')
                $('body').append(data);
                $('body').append('<div class="shade"></div>');
                $('.user-login').show();
            }
        });
    }

    $(function(){
        $('body').on('click','.hl-login',function(){
            userLogin();
        });
    });
</script>

</body>
</html>