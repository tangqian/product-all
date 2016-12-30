package com.thinkgem.jeesite.modules.live.room.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class RoomSpeechPublic extends ExDataEntity<RoomSpeechPublic> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer sourceId;

	private String name;

	private Integer fileId;

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

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setFileId(Integer value) {
		this.fileId = value;
	}

	public Integer getFileId() {
		return this.fileId;
	}

}
