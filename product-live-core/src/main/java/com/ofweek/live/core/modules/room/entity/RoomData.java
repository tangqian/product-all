package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;

/**
 * @author tangqian
 */
public class RoomData extends DataEntity<RoomData> {

    private static final long serialVersionUID = 1L;

    private String roomId;

    private SpeakerData source;

    private String name;

    private String fileId;

    private Integer sort;

    public RoomData() {
    	
    }
    
	public RoomData(String roomId, String sourceId) {
		this.roomId = roomId;
		setSourceId(sourceId);
	}

    public RoomData(String roomId) {
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
            source = new SpeakerData();
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

    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }

    public SpeakerData getSource() {
        return source;
    }

    public void setSource(SpeakerData source) {
        this.source = source;
    }
}
