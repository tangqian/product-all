package com.ofweek.live.nio.handlers.live;

import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.handler.HandlerListener;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveStartBroadcast;
import com.ofweek.live.nio.handlers.live.dto.LiveStartRequest;
import com.ofweek.live.nio.handlers.live.dto.LiveStartResponse;
import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.ofweek.live.nio.handlers.live.utils.LiveAddressUtils;
import com.ofweek.live.nio.handlers.live.utils.LivePptUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class LiveStartHandler extends AbstractBaseHandler<LiveStartRequest> implements HandlerListener, InitializingBean {

    @Resource
    private LoginHandler loginHandler;

    @Override
    public int msgNo() {
        return MsgNoEnum.LIVE_START.code();
    }

    @Override
    protected Class<LiveStartRequest> getReqestBodyClass() {
        return LiveStartRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LiveStartRequest requestBody) {
        LiveStartResponse response = new LiveStartResponse();
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        if (LiveUtils.isPlaying(roomId)) {
            response.setCode(1);
            response.setMessage("已经在直播中");
        } else {
            if (!RoomModeEnum.isPreview(LoginHandler.NioUserUtils.getMode(channel))) {
                LVBChannel liveInfo = LiveAddressUtils.get(roomId);
                if (liveInfo == null) {
                    response.setCode(3);
                    response.setMessage("请先获取直播地址");
                } else {
                    response.setCode(0);
                    LiveUtils.RoomLiveStream stream = new LiveUtils.RoomLiveStream();
                    stream.setPlaying(true);
                    stream.setSocketId(channel.id().asLongText());
                    stream.setUserId(LoginHandler.NioUserUtils.getUserId(channel));
                    stream.setRoomId(roomId);
                    stream.setLiveInfo(liveInfo);
                    stream.setType(requestBody.getType());
                    stream.setPptId(requestBody.getPptId());
                    stream.setPlayTime(new Date());
                    LiveUtils.put(stream);
                }
            } else {
                response.setCode(2);
                response.setMessage("发布直播预览成功");
            }
        }

        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.loginHandler != null) {
            this.loginHandler.addHanlderListener(this);
        }
    }

    @Override
    protected void sendNotifiaction(Channel channel, LiveStartRequest requestBody, Object responseBody) {
        if (responseBody instanceof LiveStartResponse) {
            LiveStartResponse response = (LiveStartResponse) responseBody;
            if (response.getCode() == 0 && isLivePermitted(channel)) {
                LiveUtils.RoomLiveStream stream = LiveUtils.get(LoginHandler.NioUserUtils.getRoomId(channel));
                BaseMessage message = BaseMessage.getNotification();
                message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_START.code());

                LiveStartBroadcast broadcast = new LiveStartBroadcast(requestBody);
                broadcast.setPage(1);
                broadcast.setSeek(0);
                broadcast.setDownstream(stream.getLiveInfo());
                message.setBody(broadcast);
                sendToRoomExcludeSelf(channel, message);
            }
        }
    }

    @Override
    public void notice(Channel channel) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
        if (stream != null) {
            LiveStartBroadcast broadcast = new LiveStartBroadcast();
            broadcast.setDownstream(stream.getLiveInfo());
            broadcast.setUserId(stream.getUserId());
            broadcast.setType(stream.getType());
            broadcast.setPptId(stream.getPptId());
            long current = System.currentTimeMillis();
            long playTime = stream.getPlayTime().getTime();
            int seek = (int) (current - playTime) / 1000;
            broadcast.setSeek(seek);

            LivePptUtils.RoomPptStream pptStream = LivePptUtils.get(roomId);
            if (pptStream != null) {// 发送翻页请求时，会设置页码
                broadcast.setPage(pptStream.getDto().getPage());
            } else {
                broadcast.setPage(1);
            }

            BaseMessage notice = BaseMessage.getNotification();
            notice.setMsgNo(MsgNoEnum.BROADCAST_LIVE_START.code());
            notice.setBody(broadcast);
            channel.writeAndFlush(notice);
        }
    }
}
