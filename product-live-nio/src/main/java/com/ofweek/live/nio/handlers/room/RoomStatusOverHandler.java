package com.ofweek.live.nio.handlers.room;

import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.dto.NioBaseResponse;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.sys.NoticeRoomStatusHandler;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/9/27.
 */
@Service
public class RoomStatusOverHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomService roomService;

    @Resource
    private NoticeRoomStatusHandler noticeRoomStatusHandler;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_STATUS_OVER.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        updateRoomStatus(LoginHandler.NioUserUtils.getRoomId(channel));
        return new NioBaseResponse();
    }

    @Override
    protected void sendNotifiaction(Channel channel, EmptyRequest requestBody, Object responseBody) {
        if (responseBody instanceof NioBaseResponse) {
            NioBaseResponse response = (NioBaseResponse) responseBody;
            if (response.getCode() == 0) {
                noticeRoomStatusHandler.sendNotice(LoginHandler.NioUserUtils.getRoomId(channel));
            }
        }
    }

    private void updateRoomStatus(String roomId) {
        Room room = new Room();
        room.setId(roomId);
        room.setStatus(RoomStatusEnum.OVER.getCode());
        roomService.save(room);
    }

    /**
     * 定时扫描过了结束时间但是未结束的直播间，自动结束直播，并关闭房间
     */
    public void autoCloseTheRoom() {
        List<Room> rooms = roomService.findToCloseList();
        try {
            for (Room room : rooms) {
                String roomId = room.getId();
                updateRoomStatus(roomId);
                noticeRoomStatusHandler.sendNotice(roomId);
            }
        } catch (Exception e) {
            logger.error("自动结束房间失败!", e);
        }
    }
}
