package com.ofweek.live.nio.handlers.user;

import com.ofweek.live.core.modules.audience.service.AudienceRegisterService;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tangqian on 2016/8/29.
 */
@Service
public class AudienceRegisterHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private AudienceRegisterService audienceRegisterService;

    @Override
    public int msgNo() {
        return MsgNoEnum.AUDIENCE_REGISTER.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        if (isSave(channel)) {
            GeneralUser user = LoginHandler.NioUserUtils.getUser(channel);
            if (UserTypeEnum.isAudience(user.getType())) {
                audienceRegisterService.save(user.getId(), roomId);
            }
        }
        return null;
    }

}
