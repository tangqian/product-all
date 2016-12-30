package com.ofweek.live.core.modules.speaker.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class SpeakerVideo extends DataEntity<SpeakerVideo> {

	private static final long serialVersionUID = 1L;
	
	private String speakerId;
	
	private String name;
	
	private String detail;
	
	private String fileId;
	
	private String coverId;
	
	private Integer sort;
	
	private Integer status;
	
	private String auditBy;
	
	private java.util.Date auditTime;
	
	private String auditReason;
	
	public SpeakerVideo() {
		
	}
	
	public SpeakerVideo(String speakerId) {
		this.speakerId = speakerId;
	}

	public void setSpeakerId(String value) {
		this.speakerId = value;
	}
	
	public String getSpeakerId() {
		return this.speakerId;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDetail(String value) {
		this.detail = value;
	}
	
	public String getDetail() {
		return this.detail;
	}
	
	public void setFileId(String value) {
		this.fileId = value;
	}
	
	public String getFileId() {
		return this.fileId;
	}
	
	public void setCoverId(String value) {
		this.coverId = value;
	}
	
	public String getCoverId() {
		return this.coverId;
	}
	
	public void setSort(Integer value) {
		this.sort = value;
	}
	
	public Integer getSort() {
		return this.sort;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setAuditBy(String value) {
		this.auditBy = value;
	}
	
	public String getAuditBy() {
		return this.auditBy;
	}
	
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditReason(String value) {
		this.auditReason = value;
	}
	
	public String getAuditReason() {
		return this.auditReason;
	}
	
	@Override
	public void preInsert() {
		speakerId = getCurrentUser() == null ? "0" : getCurrentUser().getId();
		sort = 1;
		status = 0;
		super.preInsert();
	}
	
}
