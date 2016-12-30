package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveVodStopBroadcast;
import com.ofweek.live.nio.handlers.live.utils.LiveVodUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;

import org.springframework.stereotype.Service;

@Service
public class LiveVodStopHandler extends AbstractBaseHandler<EmptyRequest> {

	@Override
	public int msgNo() {
		return MsgNoEnum.LIVE_VOD_STOP.code();
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
		LiveVodUtils.RoomLiveVodSeek videoSeek = LiveVodUtils.get(roomId);
		if (videoSeek != null && isLivePermitted(channel)) {
			LiveVodUtils.remove(roomId);
			BaseMessage message = BaseMessage.getNotification();
			message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_VOD_STOP.code());

			LiveVodStopBroadcast broadcast = new LiveVodStopBroadcast();
			broadcast.setVodName(videoSeek.getVodName());
			broadcast.setUserId(videoSeek.getUserId());
			message.setBody(broadcast);
			sendToRoom(channel, message);
		}
	}
}
