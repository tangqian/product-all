package com.ofweek.live.nio.handlers.sys;

import com.google.common.collect.Maps;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.sys.dto.CountOnlineBroadcast;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;


@Service
public class CountOnlineHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 前一次发送的在线人数(包括游客)统计值，比较值是否有变化
     */
    private Map<String, Integer> previousCounts = Maps.newHashMap();

    public int getCount(String roomId) {
        return RoomChannelContainer.countOnline(roomId);
    }

    private void autoRun() {
        Map<String, Integer> onlineMap = RoomChannelContainer.countOnline();

        onlineMap.keySet().forEach(roomId -> {
            Integer prevCount = previousCounts.get(roomId);
            int nowCount = onlineMap.get(roomId);
            if (prevCount == null || prevCount != nowCount) {
                previousCounts.put(roomId, nowCount);

                CountOnlineBroadcast broadcast = new CountOnlineBroadcast();
                broadcast.setCount(nowCount);
                BaseMessage message = BaseMessage.getNotification();
                message.setMsgNo(MsgNoEnum.BROADCAST_COUNT_ONLINE.code());
                message.setBody(broadcast);
                ChannelGroup group = RoomChannelContainer.getGroupByRoom(roomId);
                if (group != null)
                    group.writeAndFlush(message);
            }
        });
    }

}
