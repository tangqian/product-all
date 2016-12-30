package com.ofweek.live.nio.handlers.room;

import com.ofweek.live.core.modules.room.dao.RoomDao;
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

/**
 * Created by tangqian on 2016/9/27.
 */
@Service
public class RoomStatusStartHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomDao roomDao;

    @Resource
    private RoomService roomService;

    @Resource
    private NoticeRoomStatusHandler noticeRoomStatusHandler;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_STATUS_START.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        Room room = roomDao.get(LoginHandler.NioUserUtils.getRoomId(channel));
        NioBaseResponse response = new NioBaseResponse();
        room.setStatus(RoomStatusEnum.HOLDING.getCode());
        roomService.save(room);
        return response;
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
}
