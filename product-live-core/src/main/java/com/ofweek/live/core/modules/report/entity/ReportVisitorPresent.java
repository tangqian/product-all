package com.ofweek.live.core.modules.report.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class ReportVisitorPresent extends DataEntity<ReportVisitorPresent> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private String visitorId;

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setVisitorId(String value) {
		this.visitorId = value;
	}
	
	public String getVisitorId() {
		return this.visitorId;
	}
	
}
