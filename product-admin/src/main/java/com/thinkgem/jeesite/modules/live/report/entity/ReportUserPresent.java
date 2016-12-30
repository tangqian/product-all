package com.thinkgem.jeesite.modules.live.report.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * 
 * @author tangqian
 */
public class ReportUserPresent extends ExDataEntity<ReportUserPresent> {

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
