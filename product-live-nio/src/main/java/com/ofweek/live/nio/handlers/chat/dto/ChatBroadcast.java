package com.ofweek.live.nio.handlers.chat.dto;

import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;

import java.util.Date;

/**
 * Created by tangqian on 2016/8/29.
 */
public class ChatBroadcast {

    private String id;

    private Integer chatType;

    private String content;

    private Date chatTime;

    private String roomId;

    private NioUserDto author;

    public ChatBroadcast(RoomChat chat) {
        id = chat.getId();
        chatType = chat.getType();
        content = chat.getContent();
        chatTime = chat.getCreateDate();
        roomId = chat.getRoomId();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public NioUserDto getAuthor() {
        return author;
    }

    public void setAuthor(NioUserDto author) {
        this.author = author;
    }
}
