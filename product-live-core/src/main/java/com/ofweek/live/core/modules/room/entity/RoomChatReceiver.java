package com.ofweek.live.core.modules.room.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class RoomChatReceiver extends DataEntity<RoomChatReceiver> {

	private static final long serialVersionUID = 1L;
	
	private String chatId;
	
	private String receiveBy;
	
	private java.util.Date readDate;

	public void setChatId(String value) {
		this.chatId = value;
	}
	
	public String getChatId() {
		return this.chatId;
	}
	
	public void setReceiveBy(String value) {
		this.receiveBy = value;
	}
	
	public String getReceiveBy() {
		return this.receiveBy;
	}
	
	public void setReadDate(java.util.Date value) {
		this.readDate = value;
	}
	
	public java.util.Date getReadDate() {
		return this.readDate;
	}
	
}
