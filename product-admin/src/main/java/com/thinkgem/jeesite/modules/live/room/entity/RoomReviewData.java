package com.thinkgem.jeesite.modules.live.room.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class RoomReviewData extends ExDataEntity<RoomReviewData> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private String name;

	private Integer fileId;

	private String mediaId;

	private Integer sort;

	private Integer type;

	private Room room;

	private String operatorType;

	private String sourceUrl;// 获取媒资对应的可播放源文件的地址

	private String coverPage;// 获取媒资对应的封面图片的地址

	public void setRoomId(Integer value) {
		this.roomId = value;
	}

	public Integer getRoomId() {
		return this.roomId;
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

	public Room getRoom() {
		if (room == null) {
			room = new Room();
		}
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getCoverPage() {
		return coverPage;
	}

	public void setCoverPage(String coverPage) {
		this.coverPage = coverPage;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
}
