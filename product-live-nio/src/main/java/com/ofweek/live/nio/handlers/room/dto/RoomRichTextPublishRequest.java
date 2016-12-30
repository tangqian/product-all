package com.ofweek.live.nio.handlers.room.dto;

/**
 * Created by tangqian on 2016/8/29.
 */
public class RoomRichTextPublishRequest {

    private String id;

    private String content;

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
}
