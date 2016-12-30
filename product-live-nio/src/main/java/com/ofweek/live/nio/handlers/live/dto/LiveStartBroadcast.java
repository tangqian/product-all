package com.ofweek.live.nio.handlers.live.dto;

import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse.DownstreamAddress;

public class LiveStartBroadcast {

	private String type;
	private String pptId;
	private String userId;
	private int seek;
	private int page;
	private String channelId;
	private String hlsDownstream;
	private String flvDownstream;
	private String rtmpDownstream;

	public LiveStartBroadcast() {

	}

	public LiveStartBroadcast(LiveStartRequest requestBody) {
		this.type = requestBody.getType();
		this.pptId = requestBody.getPptId();
	}

	public void setDownstream(LVBChannel liveChannel) {
		channelId = liveChannel.getChannelId();
		DownstreamAddress address = liveChannel.getDownstreamAddress();
		flvDownstream = address.getFlvAddress();
		hlsDownstream = address.getHlsAddress();
		rtmpDownstream = address.getRtmpAddress();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSeek() {
		return seek;
	}

	public void setSeek(int seek) {
		this.seek = seek;
	}

	public String getPptId() {
		return pptId;
	}

	public void setPptId(String pptId) {
		this.pptId = pptId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getHlsDownstream() {
		return hlsDownstream;
	}

	public void setHlsDownstream(String hlsDownstream) {
		this.hlsDownstream = hlsDownstream;
	}

	public String getFlvDownstream() {
		return flvDownstream;
	}

	public void setFlvDownstream(String flvDownstream) {
		this.flvDownstream = flvDownstream;
	}

	public String getRtmpDownstream() {
		return rtmpDownstream;
	}

	public void setRtmpDownstream(String rtmpDownstream) {
		this.rtmpDownstream = rtmpDownstream;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
