package com.ofweek.live.nio.handlers.sys.dto;


public class RoomStatusBroadcast {

    private Integer status;

    public RoomStatusBroadcast(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
