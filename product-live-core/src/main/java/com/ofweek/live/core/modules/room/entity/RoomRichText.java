package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class RoomRichText extends DataEntity<RoomRichText> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private String content;
	
	private Integer status;
	
	private String createBy;
	
	private String updateBy;

	public RoomRichText() {
	}

	public RoomRichText(String id) {
		this.id = id;
	}

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setCreateBy(String value) {
		this.createBy = value;
	}
	
	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setUpdateBy(String value) {
		this.updateBy = value;
	}
	
	public String getUpdateBy() {
		return this.updateBy;
	}

	@Override
	public void preInsert() {
		status = 0;
		super.preInsert();
	}
}
