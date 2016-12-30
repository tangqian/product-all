package com.thinkgem.jeesite.modules.live.room.vo;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;
import com.thinkgem.jeesite.modules.live.room.entity.Room;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;

public class DataAuditVo extends ExDataEntity<DataAuditVo> {

	private static final long serialVersionUID = 1L;

	private Integer searchType;

	private String name;

	private Integer type;

	private Speaker speaker;

	private Room room;

	private String dataName;

	private Integer sourceId;

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

}
