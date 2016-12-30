package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class RoomReviewData extends DataEntity<RoomReviewData> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private String name;
	
	private String fileId;

	/**
	 * 状态(0:禁用,1:启用)
	 */
	private Integer status;

	private String mediaId;

	private Integer sort;

	private Integer type;

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setFileId(String value) {
		this.fileId = value;
	}
	
	public String getFileId() {
		return this.fileId;
	}
	
	public void setSort(Integer value) {
		this.sort = value;
	}
	
	public Integer getSort() {
		return this.sort;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


}
