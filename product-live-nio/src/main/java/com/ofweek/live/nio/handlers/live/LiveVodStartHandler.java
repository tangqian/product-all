package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.handler.HandlerListener;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.common.dto.NioBaseResponse;
import com.ofweek.live.nio.handlers.live.dto.LiveVodStartBroadcast;
import com.ofweek.live.nio.handlers.live.dto.LiveVodStartRequest;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveVodUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class LiveVodStartHandler extends AbstractBaseHandler<LiveVodStartRequest> implements HandlerListener, InitializingBean {

	@Resource
	private LoginHandler loginHandler;

	@Override
	public int msgNo() {
		return MsgNoEnum.LIVE_VOD_START.code();
	}

	@Override
	protected Class<LiveVodStartRequest> getReqestBodyClass() {
		return LiveVodStartRequest.class;
	}

	@Override
	protected Object doProcess(Channel channel, BaseMessage request, LiveVodStartRequest requestBody) {
		NioBaseResponse response = new NioBaseResponse();
		String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
		if (LiveUtils.isPlaying(roomId)) {
			response.setCode(1);
			response.setMessage("已经在直播中");
		} else {
			response.setCode(0);

			if(isLivePermitted(channel)){
				LiveVodUtils.RoomLiveVodSeek seek = new LiveVodUtils.RoomLiveVodSeek();
				seek.setSocketId(channel.id().asLongText());
				seek.setRoomId(roomId);
				seek.setVodName(requestBody.getVodName());
				seek.setPlayTime(new Date());
				seek.setUserId(LoginHandler.NioUserUtils.getUserId(channel));
				LiveVodUtils.put(seek);
			} else {
				response.setCode(2);
				response.setMessage("直播预览");
			}
		}
		return response;
	}

	@Override
	protected void sendNotifiaction(Channel channel, LiveVodStartRequest requestBody, Object responseBody) {
		if (responseBody instanceof NioBaseResponse) {
			NioBaseResponse response = (NioBaseResponse) responseBody;
			if (response.getCode() == 0 && isLivePermitted(channel)) {
				BaseMessage message = BaseMessage.getNotification();
				message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_VOD_START.code());

				LiveVodStartBroadcast broadcast = new LiveVodStartBroadcast();
				broadcast.setUserId(LoginHandler.NioUserUtils.getUserId(channel));
				broadcast.setVodName(requestBody.getVodName());
				broadcast.setSeek(0);
				message.setBody(broadcast);
				sendToRoomExcludeSelf(channel, message);
			}
		}
	}

	@Override
	public void notice(Channel channel) {
		LiveVodUtils.RoomLiveVodSeek videoSeek = LiveVodUtils.get(LoginHandler.NioUserUtils.getRoomId(channel));
		if (videoSeek != null) {
			LiveVodStartBroadcast broadcast = new LiveVodStartBroadcast();
			broadcast.setVodName(videoSeek.getVodName());
			broadcast.setUserId(videoSeek.getUserId());
			long current = System.currentTimeMillis();
			long playTime = videoSeek.getPlayTime().getTime();
			int seek = (int) (current - playTime) / 1000;
			broadcast.setSeek(seek);

			BaseMessage notice = BaseMessage.getNotification();
			notice.setMsgNo(MsgNoEnum.BROADCAST_LIVE_VOD_START.code());
			notice.setBody(broadcast);
			channel.writeAndFlush(notice);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.loginHandler != null) {
			this.loginHandler.addHanlderListener(this);
		}
	}

}
