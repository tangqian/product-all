<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="v" value="201612071546"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${room.name}_${room.speaker.company} - 智慧生活网直播</title>
		<meta content="telephone=no" name="format-detection">
		<meta name="keywords" content="公司直播，产品直播，${room.name}，${room.speaker.company}">
		<meta name="description" content="${room.summary}">
		<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<link href="${webSite}/static/web/wap/live/static/css/app.css?v=${v}" rel=stylesheet>
		<script>
            window.userLogin;
            function login(loginType, userId, roomId, nonce, mode, ak){
                var msg = {};
                msg.loginType = loginType;
                msg.userId = userId;
                msg.roomId = roomId;
                msg.nonce = nonce;
                msg.mode = mode;
                msg.ak = ak;
                userLogin = msg;
            }
            login(${vo.loginType}, ${vo.userId}, ${room.id}, "${vo.nonce}", ${mode}, "${vo.ak}");
        </script>
	</head>
	<body>
        <div id=app></div>
		<script type="text/javascript" src="${webSite}/static/web/wap/live/static/js/manifest.js?v=${v}"></script>
		<script type="text/javascript" src="${webSite}/static/web/wap/live/static/js/vendor.js?v=${v}"></script>
		<script type="text/javascript" src="${webSite}/static/web/wap/live/static/js/app.js?v=${v}"></script>
		<script>
			var _hmt = _hmt || [];
			(function() {
				var hm = document.createElement("script");
				hm.src = "https://hm.baidu.com/hm.js?678cb18b67c0b9c9cf5a8a1e6944e558";
				var s = document.getElementsByTagName("script")[0];
				s.parentNode.insertBefore(hm, s);
			})();
		</script>
		<script type="text/javascript">
			var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
			document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F28a416fcfc17063eb9c4f9bb1a1f5cda' type='text/javascript'%3E%3C/script%3E"));
		</script>
	</body>
</html>