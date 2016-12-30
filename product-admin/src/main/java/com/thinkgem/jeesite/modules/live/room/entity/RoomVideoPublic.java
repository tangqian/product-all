package com.thinkgem.jeesite.modules.live.room.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class RoomVideoPublic extends ExDataEntity<RoomVideoPublic> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer sourceId;

	private String name;

	private String detail;

	private Integer fileId;

	private Integer coverId;

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

	public void setDetail(String value) {
		this.detail = value;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setFileId(Integer value) {
		this.fileId = value;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setCoverId(Integer value) {
		this.coverId = value;
	}

	public Integer getCoverId() {
		return this.coverId;
	}

}
