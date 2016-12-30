package com.ofweek.live.nio.handlers.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerWaiterDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.chat.dto.ChatRoomHistoryRequest;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import com.ofweek.live.nio.handlers.user.dto.UserOnlineRequest;
import io.netty.channel.Channel;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service
public class UserOnlineHandler extends AbstractBaseHandler<UserOnlineRequest> {

    @Resource
    private RoomDao roomDao;

    @Resource
    private SpeakerWaiterDao speakerWaiterDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.USER_ONLINE.code();
    }

    @Override
    protected Class<UserOnlineRequest> getReqestBodyClass() {
        return UserOnlineRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, UserOnlineRequest requestBody) {
        validate(requestBody);
        Date timeLimit = LoginHandler.NioUserUtils.getCreateTime(channel);

        List<NioUserDto> response = new ArrayList<>();
        if (!RoomModeEnum.isPreview(LoginHandler.NioUserUtils.getMode(channel))) {
            List<GeneralUser> users = getUsersInRoom(LoginHandler.NioUserUtils.getRoomId(channel), timeLimit);
            int endIndex = Math.min(requestBody.getEndIndex(), users.size());
            for (int i = requestBody.getStart(); i < endIndex; i++) {
                response.add(new NioUserDto(users.get(i)));
            }
        }
        return response;
    }

    /**
     * @param request
     * @throws NioIllegalArgumentException
     */
    private void validate(UserOnlineRequest request) throws NioIllegalArgumentException {
        if (request.getStart() == null || request.getStart() < 0)
            throw new NioIllegalArgumentException("参数(偏移值)不正确");
        if (request.getSize() == null || request.getSize() < 10 || request.getSize() > 50) {
            throw new NioIllegalArgumentException("参数(页面大小)不正确，范围[10-50]");
        }
    }

    private final List<GeneralUser> getUsersInRoom(String roomId, Date timeLimit) {
        List<Channel> channels = RoomChannelContainer.getChannelsInRoom(roomId);
        List<GeneralUser> users = Lists.newArrayList();
        Map<String, Date> exists = Maps.newHashMap();

        Set<String> excludeUsersSet = getExcludeUsers(roomId);
        for (Channel channel : channels) {
            GeneralUser user = LoginHandler.NioUserUtils.getUser(channel);
            if (user == null)// 用户可能是游客
                continue;
            if (UserTypeEnum.isAdmin(user.getType()) || excludeUsersSet.contains(user.getId())) {
                continue;
            }

            Date enterTime = LoginHandler.NioUserUtils.getCreateTime(channel);
            if(enterTime.before(timeLimit)){
                if(!exists.containsKey(user.getId())){
                    exists.put(user.getId(), enterTime);
                    users.add(user);
                }else {
                    if(exists.get(user.getId()).after(enterTime)){
                        exists.put(user.getId(), enterTime);
                    }
                }
            }
        }

        users.sort((u1, u2)-> exists.get(u2.getId()).compareTo(exists.get(u1.getId())));
        return users;
    }

    private Set<String> getExcludeUsers(String roomId) {
        Set<String> excludeUsersSet = new HashSet<>(4);
        Room room = roomDao.get(roomId);
        excludeUsersSet.add(room.getSpeakerId());

        List<SpeakerWaiter> waiterList = speakerWaiterDao.findList(new SpeakerWaiter(room.getSpeakerId()));
        for (SpeakerWaiter waiter : waiterList) {
            excludeUsersSet.add(waiter.getId());
        }
        return excludeUsersSet;
    }

}
