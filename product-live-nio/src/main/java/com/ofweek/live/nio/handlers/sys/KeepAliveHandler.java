package com.ofweek.live.nio.handlers.sys;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import io.netty.channel.Channel;

import org.springframework.stereotype.Service;


/**
 * 心跳包处理器
 *
 * @author tangqian
 */
@Service
public class KeepAliveHandler extends AbstractBaseHandler<EmptyRequest> {

    @Override
    public int msgNo() {
        return MsgNoEnum.SYS_KEEP_ALIVE.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected boolean isNeedCheckAuth() {
        return false;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        return null;
    }


}
