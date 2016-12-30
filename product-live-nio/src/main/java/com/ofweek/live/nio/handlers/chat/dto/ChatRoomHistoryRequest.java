package com.ofweek.live.nio.handlers.chat.dto;

import com.ofweek.live.core.modules.room.entity.RoomChat;

/**
 * Created by tangqian on 2016/8/29.
 */
public class ChatRoomHistoryRequest {

    /**
     * 偏移位置 相当于offset,从0开始
     */
    private Integer start;

    /**
     * 取多少条记录
     */
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
