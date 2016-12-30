package com.ofweek.live.nio.handlers.room;

import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomRichText;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.room.service.RoomRichTextService;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.dto.NioBaseResponse;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextRemoveRequest;
import com.ofweek.live.nio.handlers.sys.NoticeRoomStatusHandler;
import com.ofweek.live.nio.handlers.sys.dto.RoomStatusBroadcast;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tangqian on 2016/9/27.
 */
@Service
public class RoomRichTextRemoveHandler extends AbstractBaseHandler<RoomRichTextRemoveRequest> {

    @Resource
    private RoomRichTextService roomRichTextService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_RICHTEXT_REMOVE.code();
    }

    @Override
    protected Class<RoomRichTextRemoveRequest> getReqestBodyClass() {
        return RoomRichTextRemoveRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, RoomRichTextRemoveRequest requestBody) {
        roomRichTextService.delete(new RoomRichText(requestBody.getId()));
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, RoomRichTextRemoveRequest requestBody, Object responseBody) {
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_ROOM_RICHTEXT_REMOVE.code());
        message.setBody(requestBody);
        ChannelGroup group = RoomChannelContainer.getGroupByRoom(LoginHandler.NioUserUtils.getRoomId(channel));
        if (group != null)
            group.writeAndFlush(message);
    }
}
