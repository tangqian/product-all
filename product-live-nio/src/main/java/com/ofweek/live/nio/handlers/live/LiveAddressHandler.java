package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveAddressResponse;
import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.ofweek.live.nio.handlers.live.utils.LiveAddressUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baidubce.services.lss.model.CreateSessionResponse;
import com.baidubce.services.lss.model.GetSessionResponse;
import com.baidubce.services.lss.model.ListSessionsResponse;
import com.baidubce.services.lss.model.LivePlay;
import com.baidubce.services.lss.model.LiveSession;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse.DownstreamAddress;

@Service
public class LiveAddressHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private BaiduCloudService baiduCloudService;

    public static final String LIVE_NAME_PREFIX = "smartlifeLiveRoom-";

    @Override
    public int msgNo() {
        return MsgNoEnum.LIVE_ADDRESS.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        LiveAddressResponse response = new LiveAddressResponse();
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        LVBChannel liveInfo = LiveAddressUtils.get(roomId);
        if (liveInfo == null) {
            CreateSessionResponse sessionResponse = baiduCloudService.createPushSession(LIVE_NAME_PREFIX + roomId,
                    "live.rtmp_hls_forward_only", null, null, "test");
            liveInfo = newLVBChannel(sessionResponse);
            LiveAddressUtils.put(roomId, liveInfo);
        }
        response.setUpstreamAddress(liveInfo.getUpstreamAddress());
        response.setChannelId(liveInfo.getChannelId());
        return response;
    }

    private LVBChannel newLVBChannel(CreateSessionResponse sessionResponse) {
        LVBChannel channel = new LVBChannel();
        channel.setChannelId(sessionResponse.getSessionId());
        channel.setUpstreamAddress(sessionResponse.getPublish().getPushUrl());

        LivePlay playInfo = sessionResponse.getPlay();
        DownstreamAddress address = new DownstreamAddress();
        address.setFlvAddress(playInfo.getFlvUrl());
        address.setHlsAddress(playInfo.getHlsUrl());
        address.setRtmpAddress(playInfo.getRtmpUrl());

        channel.setDownstreamAddress(address);
        return channel;
    }

}
