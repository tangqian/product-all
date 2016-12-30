package com.ofweek.live.nio.handlers.room.dto;

import com.ofweek.live.core.modules.room.entity.RoomRichText;

import java.util.Date;

/**
 * Created by tangqian on 2016/8/29.
 */
public class RoomRichTextResponse {

    private String id;

    private String content;

    private Date createDate;    // 创建日期

    private Date updateDate;    // 更新日期

    public RoomRichTextResponse(RoomRichText text) {
        id = text.getId();
        content = text.getContent();
        createDate = text.getCreateDate();
        updateDate = text.getUpdateDate();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
