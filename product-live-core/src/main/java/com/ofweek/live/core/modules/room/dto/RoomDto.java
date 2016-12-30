package com.ofweek.live.core.modules.room.dto;

import com.ofweek.live.core.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/9.
 */
public class RoomDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String company;

    private Integer status;

    private String detail;

    private Integer coverId;

    private String coverImgUrl;

    private Date startTime;

    private Date endTime;

    private Integer isTop;

    public RoomDto()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getStartTime() {
        return DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm");
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm");
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
