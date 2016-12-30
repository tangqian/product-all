package com.ofweek.live.core.modules.rpc.common.dto;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.rpc.dto.ExhibitionDto;
import com.ofweek.live.core.modules.rpc.dto.WebinarMeetingsDto;

import java.util.Date;

/**
 * Created by tangqian on 2016/9/19.
 */
public class LiveVo {

    private LiveTypeEnum type;

    private Integer id;

    private String name;

    /**
     * -10 进行中，0 待举办，10 已结束
     */
    private Integer status;

    private String smallUrl;

    private String bigUrl;

    private String brief;

    private Date startTime;

    private Date endTime;

    /**
     * 主办公司
     */
    private String company;

    /**
     * 主讲人，针对研讨会
     */
    private String speaker;

    /**
     * 是否置顶，默认否
     */
    private boolean top;

    /**
     * 缩写显示前多少字符
     */
    private static final int ABBR_COUNT = 164;

    public LiveVo(Room room) {
        type = LiveTypeEnum.LIVE;
        id = Integer.valueOf(room.getId());
        name = room.getName();
        bigUrl = room.getCover().getUrl();
        smallUrl = room.getCover().getUrl();
        startTime = room.getStartTime();
        endTime = room.getEndTime();
        brief = getAbbr(room.getSummary());
        top = room.getIsTop() == 1;
        switch (room.getStatus()) {
            case 0:
                status = 0;
                break;
            case 2:
                status = -10;
                break;
            default:
                status = 10;
        }
    }

    public LiveVo(ExhibitionDto dto) {
        type = LiveTypeEnum.EXPO;
        id = dto.getId();
        name = dto.getName();
        bigUrl = dto.getBigUrl();
        smallUrl = dto.getSmallUrl();
        startTime = dto.getStartTime();
        endTime = dto.getEndTime();
        brief = getAbbr(dto.getBrief());
        top = false;
        switch (dto.getStatus()) {
            case 1:
                status = 0;
                break;
            case 2:
                status = -10;
                break;
            default:
                status = 10;
        }
    }

    public LiveVo(WebinarMeetingsDto.WebinarActivity dto) {
        type = LiveTypeEnum.WEBINAR;
        id = dto.getId();
        name = dto.getName();
        bigUrl = dto.getCoverImgUrl();
        smallUrl = dto.getCoverImgUrl();
        startTime = dto.getStartTime();
        endTime = dto.getEndTime();
        brief = getAbbr(dto.getSummary());
        company = dto.getCompany();
        speaker = dto.getLecturer();
        top = false;
        switch (dto.getStatus()) {
            case 0:
                status = 0;
                break;
            case 1:
                status = -10;
                break;
            default:
                status = 10;
        }
    }

    private String getAbbr(String src){
        return StringUtils.abbr(src, ABBR_COUNT);
    }

    public LiveTypeEnum getType() {
        return type;
    }

    public void setType(LiveTypeEnum type) {
        this.type = type;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public boolean isLiving(){
        return status == -10;
    }

    public boolean isOver(){
        return status == 10;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

}
