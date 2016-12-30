package com.ofweek.live.nio.handlers.chat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.room.service.RoomChatService;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.chat.dto.ChatBroadcast;
import com.ofweek.live.nio.handlers.chat.dto.ChatRoomHistoryRequest;
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
public class ChatRoomHistoryHandler extends AbstractBaseHandler<ChatRoomHistoryRequest> {

    @Resource
    private RoomChatService roomChatService;

    @Resource
    private UserService userService;

    @Override
    public int msgNo() {
        return MsgNoEnum.CHAT_ROOM_HISTORY.code();
    }

    @Override
    protected Class<ChatRoomHistoryRequest> getReqestBodyClass() {
        return ChatRoomHistoryRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, ChatRoomHistoryRequest requestBody) {
        validate(requestBody);
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Date endTime = LoginHandler.NioUserUtils.getCreateTime(channel);

        List<RoomChat> records = roomChatService.findPage(roomId, endTime, requestBody.getStart(), requestBody.getSize());
        Map<String, NioUserDto> cache = Maps.newHashMap();

        List<ChatBroadcast> historys = Lists.newArrayList();
        records.forEach(chat -> {
            ChatBroadcast broadcast = new ChatBroadcast(chat);

            NioUserDto user = null;
            String author = chat.getSendBy();
            if (cache.containsKey(author))
                user = cache.get(author);
            else {
                user = new NioUserDto(userService.getGeneralUser(author));
                cache.put(author, user);
            }
            broadcast.setAuthor(user);
            historys.add(broadcast);
        });
        return historys;
    }

    /**
     * @param request
     * @throws NioIllegalArgumentException
     */
    private void validate(ChatRoomHistoryRequest request) throws NioIllegalArgumentException {
        if (request.getStart() == null || request.getStart() < 0)
            throw new NioIllegalArgumentException("参数(偏移值)不正确");
        if (request.getSize() == null || request.getSize() < 1 || request.getSize() > 50) {
            throw new NioIllegalArgumentException("参数(页面大小)不正确，范围[1-50]");
        }
    }
}
