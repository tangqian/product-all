package com.ofweek.live.core.modules.room.entity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.entity.User;

public class Room extends DataEntity<Room> {

	private static final long serialVersionUID = 1L;

	private String name;

	private String keyword;

	private Speaker speaker;

	private Integer publishMode;

	private Integer watchMode;

	private Integer isShow;

	private Integer isContactShow;

	private Integer isTop;
	
	private Integer pv;

	private String industry;

	private List<String> industriesIdList;

	private java.util.Date startTime;

	private java.util.Date endTime;

	private java.util.Date bgEndTime;

	private SysFile cover;

	private SysFile review;

	private String summary;

	private String detail;

	private Integer status;

	private Integer searchType;

	private String company;

	protected User createBy;

	protected User updateBy;

	private Integer audienceCount;

	private Integer downloadAudienceCount;

	private Integer roomChatAudienceCount;

	private Integer visitorCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSpeakerId() {
		return speaker != null ? speaker.getId() : null;
	}

	public void setSpeakerId(String speakerId) {
		if (speaker == null)
			speaker = new Speaker();
		speaker.setId(speakerId);
	}

	public Integer getPublishMode() {
		return publishMode;
	}

	public void setPublishMode(Integer publishMode) {
		this.publishMode = publishMode;
	}

	public Integer getWatchMode() {
		return watchMode;
	}

	public void setWatchMode(Integer watchMode) {
		this.watchMode = watchMode;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsContactShow() {
		return isContactShow;
	}

	public void setIsContactShow(Integer isContactShow) {
		this.isContactShow = isContactShow;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public List<String> getIndustriesIdList() {
		return industriesIdList;
	}

	public void setIndustriesIdList(List<String> industriesIdList) {
		this.industriesIdList = industriesIdList;
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getCoverId() {
		return cover != null ? cover.getId() : null;
	}

	public void setCoverId(String coverId) {
		if(cover == null)
			cover = new SysFile();
		cover.setId(coverId);
	}

	public SysFile getCover() {
		return cover;
	}

	public void setCover(SysFile cover) {
		this.cover = cover;
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

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Integer getAudienceCount() {
		return audienceCount;
	}

	public void setAudienceCount(Integer audienceCount) {
		this.audienceCount = audienceCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDownloadAudienceCount() {
		return downloadAudienceCount;
	}

	public void setDownloadAudienceCount(Integer downloadAudienceCount) {
		this.downloadAudienceCount = downloadAudienceCount;
	}

	public Integer getRoomChatAudienceCount() {
		return roomChatAudienceCount;
	}

	public void setRoomChatAudienceCount(Integer roomChatAudienceCount) {
		this.roomChatAudienceCount = roomChatAudienceCount;
	}

	public Date getBgEndTime() {
		return bgEndTime;
	}

	public void setBgEndTime(Date bgEndTime) {
		this.bgEndTime = bgEndTime;
	}

	public Integer getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(Integer visitorCount) {
		this.visitorCount = visitorCount;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public SysFile getReview() {
		return review;
	}

	public void setReview(SysFile review) {
		this.review = review;
	}

	public String getReviewId() {
		return review != null ? review.getId() : null;
	}

	public void setReviewId(String reviewId) {
		if(review == null)
			review = new SysFile();
		review.setId(reviewId);
	}

}
