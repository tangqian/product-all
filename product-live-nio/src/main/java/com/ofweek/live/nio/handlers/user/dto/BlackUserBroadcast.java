package com.ofweek.live.nio.handlers.user.dto;


import com.ofweek.live.core.modules.room.entity.RoomBlacklist;

public class BlackUserBroadcast {

    private String userId;
    private String reason;

    public BlackUserBroadcast(BlackUserRequest requestBody) {
        userId = requestBody.getUserId();
        reason = requestBody.getReason();
    }

    public BlackUserBroadcast(RoomBlacklist roomBlacklist) {
        userId = roomBlacklist.getUserId();
        reason = roomBlacklist.getReason();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
