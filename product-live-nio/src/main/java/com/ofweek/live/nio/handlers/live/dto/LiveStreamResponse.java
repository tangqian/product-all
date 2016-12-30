package com.ofweek.live.nio.handlers.live.dto;

import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse.DownstreamAddress;

public class LiveStreamResponse {

    private String channelId;
    private String hlsDownstream;
    private String flvDownstream;
    private String rtmpDownstream;
    private String streamingStatus;

    public LiveStreamResponse() {
    }

    public void setDownstream(LVBChannel liveChannel) {
        channelId = liveChannel.getChannelId();
        DownstreamAddress address = liveChannel.getDownstreamAddress();
        flvDownstream = address.getFlvAddress();
        hlsDownstream = address.getHlsAddress();
        rtmpDownstream = address.getRtmpAddress();
    }

    public String getStreamingStatus() {
        return streamingStatus;
    }

    public void setStreamingStatus(String streamingStatus) {
        this.streamingStatus = streamingStatus;
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
