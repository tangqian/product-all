package com.ofweek.live.nio.handlers.room.dto;

import java.io.Serializable;

import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;

public class RoomPptResponse {

    private String id;
    private String name;

    public RoomPptResponse() {
    }

    public RoomPptResponse(RoomSpeech roomSpeech) {
        id = roomSpeech.getSourceId();
        name = roomSpeech.getName();
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
}
