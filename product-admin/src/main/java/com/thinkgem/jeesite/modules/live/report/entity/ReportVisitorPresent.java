package com.thinkgem.jeesite.modules.live.report.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * 
 * @author tangqian
 */
public class ReportVisitorPresent extends ExDataEntity<ReportVisitorPresent> {

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
