package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LivePptBroadcast;
import com.ofweek.live.nio.handlers.live.dto.LivePptRequest;
import com.ofweek.live.nio.handlers.live.utils.LivePptUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;

import org.springframework.stereotype.Service;


@Service
public class LivePptHandler extends AbstractBaseHandler<LivePptRequest> {

    @Override
    public int msgNo() {
        return MsgNoEnum.LIVE_PPT.code();
    }

    @Override
    protected Class<LivePptRequest> getReqestBodyClass() {
        return LivePptRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LivePptRequest requestBody) {
        if (isLivePermitted(channel)) {
            LivePptUtils.RoomPptStream stream = new LivePptUtils.RoomPptStream(LoginHandler.NioUserUtils.getUserId(channel),
                    LoginHandler.NioUserUtils.getRoomId(channel), requestBody);
            stream.setSocketId(channel.id().asLongText());
            LivePptUtils.put(stream);
        }
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, LivePptRequest requestBody, Object responseBody) {
        if (isLivePermitted(channel)) {
            String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_PPT.code());

            LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
            if (stream == null || stream.getPptId() == null) {
                logger.error("请先调用直播开启接口并传递pptId");
            }
            LivePptBroadcast broadcast = new LivePptBroadcast();
            broadcast.setPptId(stream.getPptId());
            broadcast.setPage(requestBody.getPage());
            message.setBody(broadcast);
            sendToRoomExcludeSelf(channel, message);
        }
    }


}
