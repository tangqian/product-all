package com.ofweek.live.nio.handlers.user;


import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.entity.RoomBlacklist;
import com.ofweek.live.core.modules.room.service.RoomBlacklistService;
import com.ofweek.live.core.modules.sys.dao.UserDao;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.user.dto.BlackUserBroadcast;
import com.ofweek.live.nio.handlers.user.dto.BlackUserRequest;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BlackUserHandler extends AbstractBaseHandler<BlackUserRequest> {

    @Resource
    private RoomBlacklistService roomBlacklistService;

    @Resource
    private UserDao userDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.USER_BLACK.code();
    }

    @Override
    protected Class<BlackUserRequest> getReqestBodyClass() {
        return BlackUserRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, BlackUserRequest requestBody) {
        validate(requestBody);
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        RoomBlacklist blacklist = new RoomBlacklist();
        blacklist.setRoomId(roomId);
        blacklist.setReason(requestBody.getReason());
        blacklist.setUserId(requestBody.getUserId());
        roomBlacklistService.save(blacklist);
        return null;
    }

    /**
     * @param request
     * @throws NioIllegalArgumentException
     */
    private void validate(BlackUserRequest request) throws NioIllegalArgumentException {
        if (StringUtils.isBlank(request.getUserId()))
            throw new NioIllegalArgumentException("用户ID不能为空");

        User user = userDao.get(request.getUserId());
        if (user == null) {
            throw new NioIllegalArgumentException("用户不存在");
        }
        if (UserTypeEnum.isAdmin(user.getType())) {
            throw new NioIllegalArgumentException("不能拉黑管理员");
        }
    }

    @Override
    protected void sendNotifiaction(Channel channel, BlackUserRequest requestBody, Object responseBody) {
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_USER_BLACK.code());
        message.setBody(new BlackUserBroadcast(requestBody));
        doSend(LoginHandler.NioUserUtils.getRoomId(channel), requestBody.getUserId(), message);
    }

    /**
     * 发送拉黑消息
     *
     * @param id 拉黑记录id
     */
    public void sendNotice(String id) {
        RoomBlacklist roomBlacklist = roomBlacklistService.get(id);
        if (roomBlacklist != null) {
            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_USER_BLACK.code());
            message.setBody(new BlackUserBroadcast(roomBlacklist));
            doSend(roomBlacklist.getRoomId(), roomBlacklist.getUserId(), message);
        }
    }

    private void doSend(String roomId, String blackUserId, BaseMessage message) {
        List<Channel> channels = RoomChannelContainer.getChannelsInRoom(roomId);
        for (Channel nioChannel : channels) {
            String userId = LoginHandler.NioUserUtils.getUserId(nioChannel);
            if (userId != null && userId.equals(blackUserId)) {
                nioChannel.writeAndFlush(message);
            }
        }
    }
}
