package com.ofweek.live.nio.handlers.sys;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.sys.dto.CountPresentBroadcast;
import com.ofweek.live.nio.handlers.user.LoginHandler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;


@Service
public class CountPresentHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 房间人气统计，包括游客
     */
    private Map<String, Integer> roomCounts = Maps.newHashMap();

    /**
     * 前一次发送的人气统计值，比较值是否有变化
     */
    private Map<String, Integer> previousCounts = Maps.newHashMap();

    @Resource
    private RoomDao roomDao;

    @PostConstruct
    public void postConstruct() {
    	List<Room> rooms = roomDao.findAllList(new Room());
    	rooms.forEach(p -> initCount(p.getId(), p.getPv()));
    }

    public void add(Channel channel) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Room room = roomDao.get(roomId);
        roomCounts.put(roomId, room.getPv());
    }

    public int getCount(String roomId) {
        Integer count = roomCounts.get(roomId);
        return count == null ? 0 : count;
    }

    public void autoRun() {
        roomCounts.keySet().forEach(roomId -> {
            Integer prevCount = previousCounts.get(roomId);
            int nowCount = roomCounts.get(roomId);
            if (prevCount == null || prevCount != nowCount) {
                previousCounts.put(roomId, nowCount);

                CountPresentBroadcast broadcast = new CountPresentBroadcast();
                broadcast.setCount(nowCount);

                BaseMessage message = BaseMessage.getNotification();
                message.setMsgNo(MsgNoEnum.BROADCAST_COUNT_PRESENT.code());
                message.setBody(broadcast);
                ChannelGroup group = RoomChannelContainer.getGroupByRoom(roomId);
                if (group != null)
                    group.writeAndFlush(message);
            }
        });
    }
    
    private void initCount(String roomId, Integer pv) {
    	roomCounts.put(roomId, pv);
    }
}
