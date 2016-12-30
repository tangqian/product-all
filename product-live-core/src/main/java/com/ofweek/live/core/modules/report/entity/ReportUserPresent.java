package com.ofweek.live.core.modules.report.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class ReportUserPresent extends DataEntity<ReportUserPresent> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private String userId;

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
}
