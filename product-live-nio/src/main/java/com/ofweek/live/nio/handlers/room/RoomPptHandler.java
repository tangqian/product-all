package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.modules.room.dao.RoomSpeechDao;
import com.ofweek.live.core.modules.room.dao.RoomSpeechPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomPptResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomPptHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomSpeechDao roomSpeechDao;

    @Resource
    private RoomSpeechPublicDao roomSpeechPublicDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_PPT.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Integer mode = LoginHandler.NioUserUtils.getMode(channel);

        RoomSpeech entity = new RoomSpeech(roomId);
        List<RoomSpeech> dataList = RoomModeEnum.isPreview(mode) ? roomSpeechDao.findList(entity) :
                roomSpeechPublicDao.findList(entity);
        List<RoomPptResponse> responses = Lists.newArrayList();
        dataList.forEach(data -> responses.add(new RoomPptResponse(data)));
        return responses;
    }
}
