package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveStopBroadcast;
import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.live.utils.LivePptUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;

import org.springframework.stereotype.Service;

@Service
public class LiveStopHandler extends AbstractBaseHandler<EmptyRequest> {

    @Override
    public int msgNo() {
        return MsgNoEnum.LIVE_STOP.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, EmptyRequest requestBody, Object responseBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
        if (stream != null && isLivePermitted(channel)) {
            LiveUtils.remove(roomId);
            LivePptUtils.remove(roomId);

            LVBChannel liveInfo = stream.getLiveInfo();
            LiveStopBroadcast broadcast = new LiveStopBroadcast();
            broadcast.setChannelId(liveInfo.getChannelId());
            broadcast.setType(stream.getType());
            broadcast.setUserId(stream.getUserId());

            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_STOP.code());
            message.setBody(broadcast);
            sendToRoom(channel, message);
        }
    }
}
