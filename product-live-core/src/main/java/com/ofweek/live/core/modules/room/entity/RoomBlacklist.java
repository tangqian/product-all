package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class RoomBlacklist extends DataEntity<RoomBlacklist> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private String userId;
	
	private String reason;

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
	
	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
	}
	
}
