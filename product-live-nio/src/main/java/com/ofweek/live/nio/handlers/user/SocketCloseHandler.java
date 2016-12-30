package com.ofweek.live.nio.handlers.user;

import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.Handleable;
import com.ofweek.live.nio.common.handler.HandlerMapperUtils;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveStopBroadcast;
import com.ofweek.live.nio.handlers.live.dto.LiveVodStopBroadcast;
import com.ofweek.live.nio.handlers.live.utils.LVBChannel;
import com.ofweek.live.nio.handlers.live.utils.LivePptUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveVodUtils;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class SocketCloseHandler implements Handleable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int CLOSE_MSG = -100;

    @PostConstruct
    public void postConstruct() {
        this.init();
    }

    @Override
    public void init() {
        HandlerMapperUtils.addHandler(this);
    }

    @Override
    public int msgNo() {
        return CLOSE_MSG;
    }

    @Override
    public void process(ChannelHandlerContext ctx, BaseMessage request) {
        close(ctx.channel());
    }

    private void close(Channel channel) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        if (roomId == null) {
            return;
        }
        RoomChannelContainer.removeChannel(channel);
        sendRoomNotification(channel);

        closeLiveStream(roomId, channel);
        closeVodLiveStream(roomId, channel);
        LivePptUtils.RoomPptStream pptStream = LivePptUtils.get(roomId);
        if (pptStream != null) {
            if (channel.id().asLongText().equals(pptStream.getSocketId())) {
                LivePptUtils.remove(roomId);
            }
        }
    }

    /**
     * 系统自动关闭直播接口（当房间状态变结束后调用）
     *
     * @param roomId
     */
    public void autoCloseLive(String roomId) {
        LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
        if (stream != null)
            doCloseLiveStream(roomId, stream);

        LiveVodUtils.RoomLiveVodSeek vodSeek = LiveVodUtils.get(roomId);
        if (vodSeek != null)
            doCloseVodLiveStream(roomId, vodSeek);

        LivePptUtils.RoomPptStream pptStream = LivePptUtils.get(roomId);
        if (pptStream != null) {
            LivePptUtils.remove(roomId);
        }
    }

    /**
     * 关闭直播流，及广播该关闭通知给房间内其它用户
     *
     * @param roomId
     * @param channel
     */
    private void closeLiveStream(String roomId, Channel channel) {
        LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
        if (stream != null && !"camera_live".equals(stream.getType()) && channel.id().asLongText().equals(stream.getSocketId())) {
            doCloseLiveStream(roomId, stream);
        }
    }

    private void doCloseLiveStream(String roomId, LiveUtils.RoomLiveStream stream) {
        LiveUtils.remove(roomId);
        LVBChannel qcloudResponse = stream.getLiveInfo();
        LiveStopBroadcast broadcast = new LiveStopBroadcast();
        broadcast.setChannelId(qcloudResponse.getChannelId());
        broadcast.setType(stream.getType());
        broadcast.setUserId(stream.getUserId());

        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_STOP.code());
        message.setBody(broadcast);
        ChannelGroup group = RoomChannelContainer.getGroupByRoom(roomId);
        if (group != null)
            group.writeAndFlush(message);
    }

    /**
     * 关闭录播文件直播流，及广播该关闭通知给房间内其它用户
     *
     * @param roomId
     * @param channel
     */
    private void closeVodLiveStream(String roomId, Channel channel) {
        LiveVodUtils.RoomLiveVodSeek vodSeek = LiveVodUtils.get(roomId);
        if (vodSeek != null && channel.id().asLongText().equals(vodSeek.getSocketId())) {
            doCloseVodLiveStream(roomId, vodSeek);
        }
    }

    private void doCloseVodLiveStream(String roomId, LiveVodUtils.RoomLiveVodSeek vodSeek) {
        LiveVodUtils.remove(roomId);

        LiveVodStopBroadcast broadcast = new LiveVodStopBroadcast();
        broadcast.setVodName(vodSeek.getVodName());
        broadcast.setUserId(vodSeek.getUserId());
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_LIVE_VOD_STOP.code());
        message.setBody(broadcast);

        ChannelGroup group = RoomChannelContainer.getGroupByRoom(roomId);
        if (group != null)
            group.writeAndFlush(message);
    }

    private void sendRoomNotification(Channel channel) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        GeneralUser generalUser = LoginHandler.NioUserUtils.getUser(channel);
        if (generalUser != null) {
            if (!UserTypeEnum.isAdmin(generalUser.getType())) {
                if (RoomChannelContainer.isOtherChannelInRoom(channel))// 如果该通道所属用户有其它端在线，则不发离场通知
                    return;
                NioUserDto broadcast = new NioUserDto(generalUser);

                BaseMessage message = BaseMessage.getNotification();
                message.setMsgNo(MsgNoEnum.BROADCAST_USER_LEAVE.code());
                message.setBody(broadcast);
                try {
                    RoomChannelContainer.getGroupByRoom(channel).writeAndFlush(message);
                } catch (Exception e) {
                    logger.error("发送房间内广播离场消息失败", e);
                }
            }
        }
    }

}
