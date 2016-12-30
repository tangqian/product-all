package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.room.entity.RoomRichText;
import com.ofweek.live.core.modules.room.service.RoomRichTextService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.chat.dto.ChatBroadcast;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextRequest;
import com.ofweek.live.nio.handlers.room.dto.RoomRichTextResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tangqian on 2016/8/29.
 */
@Service
public class RoomRichTextHandler extends AbstractBaseHandler<RoomRichTextRequest> {

    @Resource
    private RoomRichTextService roomRichTextService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_RICHTEXT.code();
    }

    @Override
    protected Class<RoomRichTextRequest> getReqestBodyClass() {
        return RoomRichTextRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, RoomRichTextRequest requestBody) {
        validate(requestBody);
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Date endTime = LoginHandler.NioUserUtils.getCreateTime(channel);

        List<RoomRichText> records = roomRichTextService.findPage(roomId, endTime, requestBody.getStart(), requestBody.getSize());
        List<RoomRichTextResponse> historys = Lists.newArrayList();
        records.forEach(text -> historys.add(new RoomRichTextResponse(text)));
        return historys;
    }

    /**
     * @param request
     * @throws NioIllegalArgumentException
     */
    private void validate(RoomRichTextRequest request) throws NioIllegalArgumentException {
        if (request.getStart() == null || request.getStart() < 0)
            throw new NioIllegalArgumentException("参数(偏移值)不正确");
        if (request.getSize() == null || request.getSize() < 10 || request.getSize() > 50) {
            throw new NioIllegalArgumentException("参数(页面大小)不正确，范围[10-50]");
        }
    }
}
