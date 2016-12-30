package com.ofweek.live.nio.handlers.chat.dto;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.entity.RoomChat;

/**
 * Created by tangqian on 2016/8/29.
 */
public class ChatRoomRequest {

    private String content;

    /**
     * 客户端不需要传递该值，用来传递参数到sendNotifiaction用
     */
    private RoomChat chat;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RoomChat getChat() {
        return chat;
    }

    public void setChat(RoomChat chat) {
        this.chat = chat;
    }
}
