package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.dao.RoomDataDao;
import com.ofweek.live.core.modules.room.dao.RoomDataPublicDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomContacterResponse;
import com.ofweek.live.nio.handlers.room.dto.RoomDataResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomContacterHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomDao roomDao;

    @Resource
    private UserService userService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_CONTACTER.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Room room = roomDao.get(roomId);
        Speaker speaker = (Speaker) userService.getGeneralUser(room.getSpeakerId());
        return new RoomContacterResponse(speaker);
    }
}
