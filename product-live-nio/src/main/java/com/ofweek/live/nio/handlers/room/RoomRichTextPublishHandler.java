package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.entity.RoomRichText;
import com.ofweek.live.core.modules.room.service.RoomRichTextService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.live.dto.LiveStartBroadcast;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextPublishRequest;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextRequest;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by tangqian on 2016/8/29.
 */
@Service
public class RoomRichTextPublishHandler extends AbstractBaseHandler<RoomRichTextPublishRequest> {

    @Resource
    private RoomRichTextService roomRichTextService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_RICHTEXT_PUBLISH.code();
    }

    @Override
    protected Class<RoomRichTextPublishRequest> getReqestBodyClass() {
        return RoomRichTextPublishRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, RoomRichTextPublishRequest requestBody) {
        validate(requestBody);
        String userId = LoginHandler.NioUserUtils.getUserId(channel);
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);

        RoomRichText richText = new RoomRichText();
        richText.setId(requestBody.getId());
        richText.setContent(requestBody.getContent());
        richText.setRoomId(roomId);
        richText.setCreateBy(userId);
        richText.setUpdateBy(userId);

        roomRichTextService.save(richText);
        requestBody.setId(richText.getId());
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, RoomRichTextPublishRequest requestBody, Object responseBody) {
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_ROOM_RICHTEXT.code());

        RoomRichText text = roomRichTextService.get(requestBody.getId());
        RoomRichTextResponse broadcast = new RoomRichTextResponse(text);
        message.setBody(broadcast);
        sendToRoom(channel, message);
    }

    /**
     * @param request
     * @throws NioIllegalArgumentException
     */
    private void validate(RoomRichTextPublishRequest request) throws NioIllegalArgumentException {
        if (StringUtils.isBlank(request.getContent())) {
            throw new NioIllegalArgumentException("内容不能为空");
        }
    }
}
