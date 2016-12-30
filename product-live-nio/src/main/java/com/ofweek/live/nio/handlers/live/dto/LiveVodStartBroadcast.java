package com.ofweek.live.nio.handlers.live.dto;

public class LiveVodStartBroadcast {

    private String vodName;

    private String userId;

    private long seek;

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSeek() {
        return seek;
    }

    public void setSeek(long seek) {
        this.seek = seek;
    }
}
