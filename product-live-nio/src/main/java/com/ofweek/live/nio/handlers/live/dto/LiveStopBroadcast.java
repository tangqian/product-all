package com.ofweek.live.nio.handlers.live.dto;

public class LiveStopBroadcast {

	private String channelId;
	/**
	 * live/ppt_live
	 */
	private String type;

	private String userId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
