package com.ofweek.live.nio.handlers.live.dto;

import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse.DownstreamAddress;

public class LiveStartRequest {

    /**
     * 请求参数
     */
    private String type;
    private String pptId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPptId() {
        return pptId;
    }

    public void setPptId(String pptId) {
        this.pptId = pptId;
    }

}
