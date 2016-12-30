package com.ofweek.live.nio.handlers.live;

import com.baidubce.services.lss.model.CreateSessionResponse;
import com.baidubce.services.lss.model.GetSessionResponse;
import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveAddressResponse;
import com.ofweek.live.nio.handlers.live.dto.LiveStopBroadcast;
import com.ofweek.live.nio.handlers.live.dto.LiveStreamResponse;
import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.ofweek.live.nio.handlers.live.utils.LiveAddressUtils;
import com.ofweek.live.nio.handlers.live.utils.LivePptUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LiveGetStreamHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private BaiduCloudService baiduCloudService;

    @Resource
    private LiveAddressHandler liveAddressHandler;

    @Override
    public int msgNo() {
        return MsgNoEnum.LIVE_STREAM_GET.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        LiveStreamResponse response = new LiveStreamResponse();
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        LVBChannel liveInfo = LiveAddressUtils.get(roomId);
        if(liveInfo != null){
            GetSessionResponse sessionResponse = baiduCloudService.getSession(liveInfo.getChannelId());
            if(sessionResponse != null){
                response.setStreamingStatus(sessionResponse.getStreamingStatus());
            }
            response.setDownstream(liveInfo);
            response.setChannelId(liveInfo.getChannelId());
        }
        return response;
    }

}
