package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.speaker.service.SpeakerWaiterService;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomWaiterHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private SpeakerWaiterService speakerWaiterService;

    @Resource
    private RoomDao roomDao;

    @Resource
    private UserService userService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_WAITER.code();
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

        SpeakerWaiter waiter = new SpeakerWaiter(room.getSpeakerId());
        List<SpeakerWaiter> datas = speakerWaiterService.findList(waiter);
        List<NioUserDto> responses = Lists.newArrayList();
        responses.add(new NioUserDto(speaker));
        datas.forEach(data -> {
            data.setSpeaker(speaker);
            responses.add(new NioUserDto(data));
        });
        return responses;
    }
}
