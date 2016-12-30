package com.ofweek.live.nio.handlers.room.dto;

import com.ofweek.live.core.modules.room.entity.Room;

import java.util.Date;

/**
 * Created by tangqian on 2016/8/27.
 */
public class RoomInfoResponse {

    private String name;

    private java.util.Date startTime;

    private java.util.Date endTime;

    private java.util.Date bgEndTime;

    private String coverUrl;

    private String reviewUrl;

    private String summary;

    private String detail;

    private String company;

    private Integer watchMode;

    private Integer contactShow;

    /**
     * 0:待举办,1:保留,2:进行中,3:保留,4:结束
     */
    private Integer status;

    public RoomInfoResponse(Room room) {
        name = room.getName();
        startTime = room.getStartTime();
        endTime = room.getEndTime();
        bgEndTime = room.getBgEndTime();
        summary = room.getSummary();
        detail = room.getDetail();
        status = room.getStatus();
        watchMode = room.getWatchMode();
        contactShow = room.getIsContactShow();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWatchMode() {
        return watchMode;
    }

    public void setWatchMode(Integer watchMode) {
        this.watchMode = watchMode;
    }

    public Integer getContactShow() {
        return contactShow;
    }

    public void setContactShow(Integer contactShow) {
        this.contactShow = contactShow;
    }

    public Date getBgEndTime() {
        return bgEndTime;
    }

    public void setBgEndTime(Date bgEndTime) {
        this.bgEndTime = bgEndTime;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
