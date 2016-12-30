package com.thinkgem.jeesite.modules.live.room.entity;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class Room extends ExDataEntity<Room> {

	private static final long serialVersionUID = 1L;

	private String name;

	private String keyword;

	private Integer speakerId;

	private Integer publishMode;

	private Integer watchMode;

	private Integer isShow;

	private Integer isContactShow;

	private Integer isTop;

	private Integer topSort;

	private String industry;

	private List<String> industriesIdList;

	private java.util.Date startTime;

	private java.util.Date endTime;

	private java.util.Date bgEndTime;

	private String reviewId;

	private String coverId;

	private String summary;

	private String detail;

	private Integer searchType;

	private String company;

	private int onLineAudienceCount;

	private Integer registerAudienceCount;

	private Integer presentAudienceCount;

	private Integer visitorCount;

	private Integer pv;

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

	public Integer getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(Integer speakerId) {
		this.speakerId = speakerId;
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
		return coverId;
	}

	public void setCoverId(String coverId) {
		this.coverId = coverId;
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

	public int getOnLineAudienceCount() {
		return onLineAudienceCount;
	}

	public void setOnLineAudienceCount(Integer onLineAudienceCount) {
		if(onLineAudienceCount != null)
			this.onLineAudienceCount = onLineAudienceCount;
	}

	public Integer getRegisterAudienceCount() {
		return registerAudienceCount;
	}

	public void setRegisterAudienceCount(Integer registerAudienceCount) {
		this.registerAudienceCount = registerAudienceCount;
	}

	public Integer getPresentAudienceCount() {
		return presentAudienceCount;
	}

	public void setPresentAudienceCount(Integer presentAudienceCount) {
		this.presentAudienceCount = presentAudienceCount;
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

	public Integer getTopSort() {
		return topSort;
	}

	public void setTopSort(Integer topSort) {
		this.topSort = topSort;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

}
