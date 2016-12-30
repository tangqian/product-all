package com.ofweek.live.nio.handlers.chat;

import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.room.enums.ChatTypeEnum;
import com.ofweek.live.core.modules.room.service.RoomChatService;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.common.utils.NioStringUtils;
import com.ofweek.live.nio.handlers.chat.dto.ChatBroadcast;
import com.ofweek.live.nio.handlers.chat.dto.ChatRoomRequest;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tangqian on 2016/8/29.
 */
@Service
public class ChatRoomHandler extends AbstractBaseHandler<ChatRoomRequest> {

    @Resource
    private RoomChatService roomChatService;

    @Resource
    private RoomDao roomDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.CHAT_ROOM.code();
    }

    @Override
    protected Class<ChatRoomRequest> getReqestBodyClass() {
        return ChatRoomRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, ChatRoomRequest requestBody) {
        requestBody.setChat(null);
        requestBody.setContent(NioStringUtils.filterEmoji(requestBody.getContent()));
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);

        if (isSave(channel)) {
            RoomChat chat = new RoomChat();
            chat.setRoomId(roomId);
            chat.setType(ChatTypeEnum.GROUP.getCode());
            chat.setContent(requestBody.getContent());
            chat.setSendBy(LoginHandler.NioUserUtils.getUserId(channel));
            roomChatService.save(chat);
            requestBody.setChat(chat);

            /*Room room = roomDao.get(roomId);
            if (RoomStatusEnum.isOver(room.getStatus())) {
                logger.error("Chat is not saved for live room is over!");
            } else {

            }*/
        }
        return null;
    }

    @Override
    protected void sendNotifiaction(Channel channel, ChatRoomRequest requestBody, Object responseBody) {
        RoomChat chat = requestBody.getChat();
        if (chat != null) {
            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.BROADCAST_CHAT_ROOM.code());

            ChatBroadcast body = new ChatBroadcast(chat);
            body.setAuthor(new NioUserDto(LoginHandler.NioUserUtils.getUser(channel)));
            message.setBody(body);
            sendToRoom(channel, message);
        }
    }
}
