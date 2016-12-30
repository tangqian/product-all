package com.ofweek.live.core.modules.report.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class ReportDataDownload extends DataEntity<ReportDataDownload> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;

	private String name;
	
	private Integer type;
	
	private String dataId;
	
	private String userId;

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setDataId(String value) {
		this.dataId = value;
	}
	
	public String getDataId() {
		return this.dataId;
	}
	
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
