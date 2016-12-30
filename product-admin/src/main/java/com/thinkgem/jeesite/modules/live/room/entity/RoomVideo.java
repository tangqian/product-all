package com.thinkgem.jeesite.modules.live.room.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class RoomVideo extends ExDataEntity<RoomVideo> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer sourceId;

	public void setRoomId(Integer value) {
		this.roomId = value;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setSourceId(Integer value) {
		this.sourceId = value;
	}

	public Integer getSourceId() {
		return this.sourceId;
	}

}
