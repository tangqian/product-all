package com.ofweek.live.nio.handlers.sys;

import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.sys.dto.RoomStatusBroadcast;
import com.ofweek.live.nio.handlers.user.SocketCloseHandler;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class NoticeRoomStatusHandler {

    @Resource
    private RoomDao roomDao;

    @Resource
    private SocketCloseHandler socketCloseHandler;

    public void sendNotice(String roomId) {
        Room room = roomDao.get(roomId);
        if (room != null) {
            if(RoomStatusEnum.isOver(room.getStatus())){
                socketCloseHandler.autoCloseLive(roomId);
            }

            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_ROOM_STATUS.code());
            message.setBody(new RoomStatusBroadcast(room.getStatus()));
            ChannelGroup group = RoomChannelContainer.getGroupByRoom(roomId);
            if (group != null)
                group.writeAndFlush(message);
        }
    }
}
