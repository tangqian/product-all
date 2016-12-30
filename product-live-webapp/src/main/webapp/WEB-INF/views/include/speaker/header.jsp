<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!-- 页眉 -->
<div class="w1000 header">
    <!-- logo -->
    <a target="_blank" href="${webSite}" class="fl admin-logo"><img src="${webSite}/static/web/modules/speaker/img/logo.png" height="60" width="200"></a>
    <div class="admin-name"><img src="${webSite}/static/web/modules/speaker/img/admin.png" height="30" width="113"></div>
    <div class="fr topbar-right">
        <span class="welcome">欢迎您：<a href="${ctx}/room">${fns:getUser().account}</a></span>
        <a href="${ctx}/speaker/account/security" class="lock"><img src="${webSite}/static/web/modules/speaker/img/lock.png" height="20" width="17">帐号管理</a>
        <a href="${ctx}/logout" class="exit">退出</a>
    </div>
</div>