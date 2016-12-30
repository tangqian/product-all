package com.thinkgem.jeesite.modules.live.room.entity;

import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class RoomBlacklist extends ExDataEntity<RoomBlacklist> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer userId;

	private String reason;

	private Room room;

	private LiveUser user;

	public RoomBlacklist(Integer userId, Integer roomId, String reason) {
		this.userId = userId;
		this.roomId = roomId;
		this.reason = reason;
	}

	public RoomBlacklist() {

	}

	public void setRoomId(Integer value) {
		this.roomId = value;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setReason(String value) {
		this.reason = value;
	}

	public String getReason() {
		return this.reason;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LiveUser getUser() {
		return user;
	}

	public void setUser(LiveUser user) {
		this.user = user;
	}

}
