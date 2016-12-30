package com.ofweek.live.nio.handlers.room;

import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomInfoResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomInfoHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomDao roomDao;

    @Resource
    private SpeakerDao speakerDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_INFO.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        Room room = roomDao.get(LoginHandler.NioUserUtils.getRoomId(channel));
        RoomInfoResponse response = new RoomInfoResponse(room);
        Speaker speaker = speakerDao.get(room.getSpeakerId());
        response.setCompany(speaker.getCompany());
        response.setCoverUrl(sysFileService.get(room.getCoverId()).getUrl());
        if(room.getReviewId() != null){
            response.setReviewUrl(sysFileService.get(room.getReviewId()).getUrl());
        }
        return response;
    }
}
