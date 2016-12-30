package com.ofweek.live.web.api.action.dto;

/**
 * Created by tangqian on 2016/9/5.
 */
public class RoomOnlineCountDto {

    private String roomId;

    private int count;

    public RoomOnlineCountDto(String roomId, int onlineUserCount) {
        this.roomId = roomId;
        this.count = onlineUserCount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
