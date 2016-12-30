<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 左 -->
<div class="fl">
    <!-- 左菜单栏 -->
    <div class="menu" id="menu">
        <ul class="menu-parent">
            <!-- 样式：menu-cur 是控制显示二级 -->
            <li class="one-level menu-cur">
                <p class="ol-title">
                    <img src="${webSite}/static/web/modules/speaker/img/menu_1.png" />
                    <strong>直播管理</strong>
                </p>
                <!-- 二级 -->
                <ul class="menu-son">
                    <li class="two-level" id="sm_room_live"><a href="${ctx}/room">管理直播</a></li>
                </ul>
            </li>
            <li class="one-level menu-cur">
                <p class="ol-title">
                   <img src="${webSite}/static/web/modules/speaker/img/menu_2.png" />
                    <strong>资料管理</strong> 
                </p>
                <!-- 二级 -->
                <ul class="menu-son">
                    <!-- 选中的二级菜单项添加样式：menu-son-cur -->
                    <li class="two-level" id="sm_speaker_data"><a href="${ctx}/speaker/data">下载管理</a></li>
                    <li class="two-level" id="sm_speaker_video"><a href="${ctx}/speaker/video">视频管理</a></li>
                    <li class="two-level" id="sm_speaker_speech"><a href="${ctx}/speaker/speech">PPT管理</a></li>
                    <li class="two-level" id="sm_speaker_data_recycle"><a href="${ctx}/speaker/recycle">资料回收站</a></li>
                </ul>
            </li>
            <li class="one-level menu-cur">
                <p class="ol-title">
                    <img src="${webSite}/static/web/modules/speaker/img/menu_3.png" />
                    <strong>数据报告</strong>
                </p>
                <!-- 二级 -->
                <ul class="menu-son">
                    <li class="two-level" id="sm_audience_data"><a href="${ctx}/speaker/audience/data">观众数据</a></li>
                </ul>
            </li>
            <!-- 没有子级需要添加样式：no-clild 不显示箭头图标 -->
            <li class="one-level no-child" id="sm_room_chat">
                <p class="ol-title">
                    <img src="${webSite}/static/web/modules/speaker/img/menu_4.png" />
               		<a href="/room/chat/list" style="text-decoration: none;"><strong>聊天记录</strong></a>
                </p>
            </li>
            <li class="one-level menu-cur">
                <p class="ol-title">
                    <img src="${webSite}/static/web/modules/speaker/img/menu_5.png" />
                    <strong>帐号管理</strong>
                </p>
                <!-- 二级 -->
                <ul class="menu-son">
                    <li class="two-level" id="sm_waiter_manage"><a href="${ctx}/speaker/waiter">设置客服</a></li>
                    <li class="two-level" id="sm_security_manage"><a href="${ctx}/speaker/account/security">账号安全</a></li>
                    <li class="two-level" id="sm_speaker_contacts"><a href="${ctx}/speaker/account/contacts">联系方式</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>