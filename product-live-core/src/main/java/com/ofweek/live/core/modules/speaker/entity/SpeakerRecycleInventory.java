package com.ofweek.live.core.modules.speaker.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class SpeakerRecycleInventory extends DataEntity<SpeakerRecycleInventory> {

	private static final long serialVersionUID = 1L;
	
	private String speakerId;
	
	private String subjectName;
	
	private String subjectId;
	
	private Integer subjectType;

	public void setSpeakerId(String value) {
		this.speakerId = value;
	}
	
	public String getSpeakerId() {
		return this.speakerId;
	}
	
	public void setSubjectName(String value) {
		this.subjectName = value;
	}
	
	public String getSubjectName() {
		return this.subjectName;
	}
	
	public void setSubjectId(String value) {
		this.subjectId = value;
	}
	
	public String getSubjectId() {
		return this.subjectId;
	}
	
	public void setSubjectType(Integer value) {
		this.subjectType = value;
	}
	
	public Integer getSubjectType() {
		return this.subjectType;
	}
	
	@Override
	public void preInsert() {
		speakerId = getCurrentUser() == null ? "0" : getCurrentUser().getId();
		super.preInsert();
	}
	
}
