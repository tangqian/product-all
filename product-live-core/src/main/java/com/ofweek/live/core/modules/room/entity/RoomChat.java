package com.ofweek.live.core.modules.room.entity;

import java.util.List;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.sys.entity.User;

/**
 * 
 * @author tangqian
 */
public class RoomChat extends DataEntity<RoomChat> {

	private static final long serialVersionUID = 1L;

	private String roomId;

	private String sendBy;

	private String content;

	private Integer type;

	private Room room;

	private User user;

	private List<RoomChat> chats;

	private String name;

	public void setSpeakerId(String speakerId) {
		if (room == null)
			room = new Room();
		room.setSpeakerId(speakerId);
	}

	public void setRoomId(String value) {
		this.roomId = value;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setSendBy(String value) {
		this.sendBy = value;
	}

	public String getSendBy() {
		return this.sendBy;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	public void setType(Integer value) {
		this.type = value;
	}

	public Integer getType() {
		return this.type;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RoomChat> getChats() {
		return chats;
	}

	public void setChats(List<RoomChat> chats) {
		this.chats = chats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
