package com.ofweek.live.nio.handlers.room.dto;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.entity.RoomReviewData;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;

/**
 * Created by tangqian on 2016/8/27.
 */
public class RoomVideoResponse {

    private String id;

    private String name;

    private String url;

    private String fileId;

    private String playUrl;

    private String mp4Url;

    private Integer type;

    private String bPlayUrl;

    private String bCoverPage;

    public RoomVideoResponse() {
    }

    public RoomVideoResponse(RoomVideo roomVideo) {
        id = roomVideo.getSourceId();
        name = roomVideo.getName();
    }

    public RoomVideoResponse(RoomReviewData roomReviewData) {
        id = roomReviewData.getId();
        name = roomReviewData.getName();
        type = roomReviewData.getType();
    }

    public void setVodPlayInfo(QcloudVodPlayInfo playInfo) {
        if (playInfo != null) {
            fileId = playInfo.getFileId();
            mp4Url = playInfo.getMp4PlayUrl();
            playUrl = playInfo.getOriginalPlayUrl();
        }
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getbPlayUrl() {
        return bPlayUrl;
    }

    public void setbPlayUrl(String bPlayUrl) {
        this.bPlayUrl = bPlayUrl;
    }

    public String getbCoverPage() {
        return bCoverPage;
    }

    public void setbCoverPage(String bCoverPage) {
        this.bCoverPage = bCoverPage;
    }

}
