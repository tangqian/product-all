package com.ofweek.live.nio.handlers.room.dto;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;

import java.util.Date;

/**
 * Created by tangqian on 2016/8/27.
 */
public class RoomDataResponse {

    private String id;

    private String name;

    private String url;

    public RoomDataResponse() {
    }

    public RoomDataResponse(RoomData data, int mode) {
        id = data.getSourceId();
        name = data.getName();
        url = LiveEnv.getWebSite() + "/download/room/data?id=" + id + "&roomId=" + data.getRoomId() + "&mode=" + mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
