package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.dao.RoomDataDao;
import com.ofweek.live.core.modules.room.dao.RoomDataPublicDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomDataResponse;
import com.ofweek.live.nio.handlers.room.dto.RoomInfoResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomDataHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomDataDao roomDataDao;

    @Resource
    private RoomDataPublicDao roomDataPublicDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_DATA.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Integer mode = LoginHandler.NioUserUtils.getMode(channel);

        RoomData entity = new RoomData(roomId);
        List<RoomData> dataList = RoomModeEnum.isPreview(mode) ? roomDataDao.findList(entity) :
                roomDataPublicDao.findList(entity);
        List<RoomDataResponse> responses = Lists.newArrayList();
        dataList.forEach(data -> responses.add(new RoomDataResponse(data, mode)));
        return responses;
    }
}
