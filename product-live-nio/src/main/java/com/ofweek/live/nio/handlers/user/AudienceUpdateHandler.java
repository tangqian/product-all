package com.ofweek.live.nio.handlers.user;

import com.ofweek.live.core.common.utils.WrapperBeanUtils;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import com.ofweek.live.nio.handlers.user.dto.UpdateUserRequest;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/29.
 */
@Service
public class AudienceUpdateHandler extends AbstractBaseHandler<UpdateUserRequest> {

    @Resource
    private AudienceService audienceService;

    @Resource
    private UserService userService;

    @Override
    public int msgNo() {
        return MsgNoEnum.AUDIENCE_UPDATE.code();
    }

    @Override
    protected Class<UpdateUserRequest> getReqestBodyClass() {
        return UpdateUserRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, UpdateUserRequest requestBody) {
        GeneralUser user = LoginHandler.NioUserUtils.getUser(channel);
        if (user != null && UserTypeEnum.isAudience(user.getType())) {
            Audience audience = new Audience();
            WrapperBeanUtils.copyProperties(audience, requestBody);
            audience.setId(user.getId());
            audienceService.save(audience);
        }
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, UpdateUserRequest requestBody, Object responseBody) {
        GeneralUser user = LoginHandler.NioUserUtils.getUser(channel);
        if (user != null && UserTypeEnum.isAudience(user.getType())) {
            final GeneralUser saveUser = userService.getGeneralUser(user.getId());
            List<Channel> channels = RoomChannelContainer.getAllChannels(true);
            channels.forEach(c -> {
                String userId = LoginHandler.NioUserUtils.getUserId(c);
                if (saveUser.getId().equals(userId)) {
                    LoginHandler.NioUserUtils.setUser(c, saveUser);
                }
            });

            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_AUDIENCE_UPDATE.code());
            message.setBody(new NioUserDto(LoginHandler.NioUserUtils.getUser(channel)));
            sendToRoom(channel, message);
        }
    }
}
