package com.ofweek.live.nio.common.handler;

import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.base.exception.BaseException;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.SysFileService;
import com.ofweek.live.nio.common.netty.RoomChannelMatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.NioFastJsonUtils;
import com.ofweek.live.nio.handlers.user.LoginHandler.NioUserUtils;

public abstract class AbstractBaseHandler<R> implements Handleable {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected SysFileService sysFileService;

    @PostConstruct
    public void postConstruct() {
        this.init();
    }

    @Override
    public void init() {
        HandlerMapperUtils.addHandler(this);
    }

    @Override
    public void process(ChannelHandlerContext ctx, BaseMessage request) {
        Object responseBody = null;
        R requestBody = null;
        Channel channel = ctx.channel();
        if (isNeedCheckAuth() && !hasLoggedOn(channel)) {
            channel.close();
            logger.error("非登录状态下不能访问此消息接口");
            return;
        }
        try {
            if (request.getBody() != null) {
                requestBody = NioFastJsonUtils.parseObject((String) request.getBody(), getReqestBodyClass());
                request.setBody(requestBody);
            }
            responseBody = doProcess(channel, request, requestBody);
            if (isNeedResponse()) {
                BaseMessage responseOk = BaseMessage.getResponseOk(request);
                responseOk.setBody(responseBody);
                channel.writeAndFlush(responseOk);
            }
        } catch (Exception e) {
            logger.error("处理消息失败!, request={}", request);
            logger.error("详细异常信息如下", e);
            if (isNeedResponse()) {
                BaseMessage responseException = BaseMessage.getResponseException(request);
                responseException.setMessage(BaseException.getSecurityMessage(e));
                if (e instanceof NioIllegalArgumentException) {
                    responseException.setStatusCode(BaseMessage.StatusCodeEnum.BAD_REQUEST);
                }
                channel.writeAndFlush(responseException);
            }
            return;
        }

        try {
            sendNotifiaction(channel, requestBody, responseBody);
        } catch (Exception e) {
            logger.error("通知发送失败", e);
        }

    }

    private boolean hasLoggedOn(Channel channel) {
        return NioUserUtils.getUser(channel) != null || NioUserUtils.getVisitor(channel) != null;
    }

    /**
     * 消息处理完成后，如果需要发送通知消息，请覆写该方法
     *
     * @param channel
     * @param responseBody
     */
    protected void sendNotifiaction(Channel channel, R requestBody, Object responseBody) {
    }

    /**
     * 是否需要保存操作到数据库，游客操作或预览模式下及管理员操作不进行保存
     *
     * @param channel
     * @return
     */
    protected final boolean isSave(Channel channel) {
        return NioUserUtils.getUserId(channel) != null && !RoomModeEnum.isPreview(NioUserUtils.getMode(channel))
                && !UserTypeEnum.isAdmin(NioUserUtils.getUser(channel).getType());
    }

    /**
     * 是否允许直播
     *
     * @param channel
     * @return
     */
    protected final boolean isLivePermitted(Channel channel) {
        if (isSave(channel)) {
            return true;
        } else {
            String userId = NioUserUtils.getUserId(channel);
            return userId != null && userId.equals("1");
        }
    }

    protected final void sendToRoom(Channel channel, BaseMessage message) {
        RoomChannelContainer.getGroupByRoom(channel).writeAndFlush(message);
    }

    protected final void sendToRoomExcludeSelf(Channel channel, BaseMessage message) {
        RoomChannelContainer.getGroupByRoom(channel).writeAndFlush(message, new RoomChannelMatcher(channel));
    }

    protected final void sendToUser(Channel channel, BaseMessage message, Integer receiverId) {
        String currentUserId = NioUserUtils.getUserId(channel);
        List<Channel> channels = RoomChannelContainer.getChannelsInRoom(channel);
        for (Channel nioChannel : channels) {
            if (nioChannel == channel)// 不给自己这个通道发送通知
                continue;
            String userId = NioUserUtils.getUserId(nioChannel);
            if (userId != null && (userId.equals(currentUserId) || userId.equals(receiverId))) {// 发送给接收者的多个通道，并且自己的其它通道也发送
                try {
                    nioChannel.writeAndFlush(message);
                } catch (Exception e) {
                }
            }
        }
    }

    protected boolean isNeedResponse() {
        return true;
    }

    /**
     * 是否需要检查用户认证状态
     *
     * @return 默认返回true
     */
    protected boolean isNeedCheckAuth() {
        return true;
    }

    protected abstract Class<R> getReqestBodyClass();

    protected abstract Object doProcess(Channel channel, BaseMessage request, R requestBody);
}
