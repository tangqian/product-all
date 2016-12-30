package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;

/**
 * 
 * @author tangqian
 */
public class RoomVideo extends DataEntity<RoomVideo> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private SpeakerVideo source;
	
	private String name;
	
	private String detail;
	
	private String fileId;
	
	private String coverId;
	
	public RoomVideo() {
		
	}
	
	public RoomVideo(String roomId, String sourceId) {
		this.roomId = roomId;
		setSourceId(sourceId);
	}

	public RoomVideo(String roomId) {
		this.roomId = roomId;
	}

	public void setRoomId(String value) {
		this.roomId = value;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setSourceId(String value) {
		if (source == null)
			source = new SpeakerVideo();
		source.setId(value);
	}
	
	public String getSourceId() {
		return source != null ? source.getId() : null;
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
	
	public void setFileId(String value) {
		this.fileId = value;
	}
	
	public String getFileId() {
		return this.fileId;
	}
	
	public void setCoverId(String value) {
		this.coverId = value;
	}
	
	public String getCoverId() {
		return this.coverId;
	}

	public SpeakerVideo getSource() {
		return source;
	}

	public void setSource(SpeakerVideo source) {
		this.source = source;
	}
}
