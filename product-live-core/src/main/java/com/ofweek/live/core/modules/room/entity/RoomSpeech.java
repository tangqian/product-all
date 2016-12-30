package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;

/**
 * 
 * @author tangqian
 */
public class RoomSpeech extends DataEntity<RoomSpeech> {

	private static final long serialVersionUID = 1L;
	
	private String roomId;
	
	private SpeakerSpeech source;
	
	private String name;
	
	private String fileId;
	
	public RoomSpeech() {
		
	}

	public RoomSpeech(String roomId, String sourceId) {
		this.roomId = roomId;
		setSourceId(sourceId);
	}

	public RoomSpeech(String roomId) {
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
			source = new SpeakerSpeech();
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
	
	public void setFileId(String value) {
		this.fileId = value;
	}
	
	public String getFileId() {
		return this.fileId;
	}

	public SpeakerSpeech getSource() {
		return source;
	}

	public void setSource(SpeakerSpeech source) {
		this.source = source;
	}
}
