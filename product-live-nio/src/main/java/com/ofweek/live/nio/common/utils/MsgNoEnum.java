package com.ofweek.live.nio.common.utils;

/**
 * Created by tangqian on 2016/8/24.
 */
public enum MsgNoEnum {
    SYS_KEEP_ALIVE(0, "心跳包"),
    SYS_BAD_REQUEST(404, "未知请求"),

    USER_LOGIN(100, "用户登录"),
    AUDIENCE_REGISTER(105, "观众登记"),
    AUDIENCE_UPDATE(106, "观众信息更新"),
    USER_BLACK(110, "拉黑用户消息"),
    USER_ONLINE(120, "用户在线列表"),

    CHAT_ROOM(200, "群聊消息"),
    CHAT_ROOM_HISTORY(201, "群聊历史消息"),
    CHAT_P2P(210, "私聊消息"),

    ROOM_INFO(300, "房间基本信息"),
    ROOM_WAITER(301, "客服列表消息"),
    ROOM_STATUS_START(302, "房间置开始状态"),
    ROOM_STATUS_OVER(303, "房间置结束状态"),
    ROOM_CONTACTER(304, "房间联系人信息"),
    ROOM_DATA(310, "下载资料消息"),
    ROOM_PPT(320, "PPT列表消息"),
    ROOM_PPT_DETAIL(321, "PPT详情消息"),
    ROOM_VIDEO(330, "视频消息"),
    ROOM_VIDEO_FOR_REVIEW(331, "回放视频消息"),
    ROOM_RICHTEXT(340, "获取图文消息"),
    ROOM_RICHTEXT_PUBLISH(341, "发布/修改图文消息"),
    ROOM_RICHTEXT_REMOVE(342, "删除图文消息"),


    LIVE_START(800, "主播开始直播"),
    LIVE_STOP(801, "主播结束直播"),
    LIVE_ADDRESS(802, "直播推流地址"),
    LIVE_STREAM_GET(803, "查询一个直播流信息"),
    LIVE_PPT(810, "主播PPT翻页消息"),
    LIVE_VOD_START(820, "主播开始VOD"),
    LIVE_VOD_STOP(821, "主播结束VOD"),

    BROADCAST_COUNT_PRESENT(1001, "广播房间人气"),
    BROADCAST_COUNT_ONLINE(1002, "广播房间实时在线人数"),
    BROADCAST_AUDIENCE_UPDATE(1106, "广播观众信息更新"),
    BROADCAST_USER_ENTER(1100, "广播用户进入房间"),
    BROADCAST_USER_LEAVE(1101, "广播用户离开房间"),
    BROADCAST_USER_BLACK(1110, "广播用户被拉黑"),

    BROADCAST_CHAT_ROOM(1200, "广播群聊消息"),
    BROADCAST_CHAT_P2P(1210, "广播私聊消息"),

    BROADCAST_ROOM_STATUS(1300, "广播房间状态"),
    BROADCAST_ROOM_RICHTEXT(1341, "广播图文消息"),
    BROADCAST_ROOM_RICHTEXT_REMOVE(1342, "广播删除图文消息"),

    BROADCAST_LIVE_START(1800, "广播直播开始消息"),
    BROADCAST_LIVE_STOP(1801, "广播直播结束消息"),
    BROADCAST_LIVE_PPT(1810, "广播PPT翻页消息"),
    BROADCAST_LIVE_VOD_START(1820, "广播VOD开始消息"),
    BROADCAST_LIVE_VOD_STOP(1821, "广播VOD结束消息"),
    ;

    private Integer code;

    private String meaning;

    MsgNoEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public int code() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }
}
