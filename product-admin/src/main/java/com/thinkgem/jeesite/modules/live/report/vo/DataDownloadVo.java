package com.thinkgem.jeesite.modules.live.report.vo;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;
import com.thinkgem.jeesite.modules.live.room.entity.Room;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;

public class DataDownloadVo extends ExDataEntity<DataDownloadVo> {

	private static final long serialVersionUID = 1L;

	private Integer searchType;

	private String name;

	private Integer type;

	private Speaker speaker;

	private Room room;

	private String dataName;

	private int attentionCount;

	private int dataDownloadCount;

	private String exportAttentionIds;

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

	public int getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(Integer attentionCount) {
		if (attentionCount != null)
			this.attentionCount = attentionCount;
	}

	public int getDataDownloadCount() {
		return dataDownloadCount;
	}

	public void setDataDownloadCount(Integer dataDownloadCount) {
		if (dataDownloadCount != null)
			this.dataDownloadCount = dataDownloadCount;
	}

	public String getExportAttentionIds() {
		return exportAttentionIds;
	}

	public void setExportAttentionIds(String exportAttentionIds) {
		this.exportAttentionIds = exportAttentionIds;
	}

}
