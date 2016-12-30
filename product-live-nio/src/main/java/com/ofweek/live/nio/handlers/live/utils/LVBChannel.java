package com.ofweek.live.nio.handlers.live.utils;

import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse.DownstreamAddress;

/**
 * @author tangqian
 *
 */
public class LVBChannel {

	private String channelId;

	private String upstreamAddress;

	private DownstreamAddress downstreamAddress;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getUpstreamAddress() {
		return upstreamAddress;
	}

	public void setUpstreamAddress(String upstreamAddress) {
		this.upstreamAddress = upstreamAddress;
	}

	public DownstreamAddress getDownstreamAddress() {
		return downstreamAddress;
	}

	public void setDownstreamAddress(DownstreamAddress downstreamAddress) {
		this.downstreamAddress = downstreamAddress;
	}

}
