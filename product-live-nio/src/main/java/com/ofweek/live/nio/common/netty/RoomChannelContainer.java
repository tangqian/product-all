package com.ofweek.live.nio.common.netty;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.nio.handlers.sys.CountPresentHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ofweek.live.nio.handlers.user.LoginHandler.NioUserUtils;

public class RoomChannelContainer {

    private static final Logger logger = LoggerFactory.getLogger(RoomChannelContainer.class);

    private static final Map<String, ChannelGroup> roomGroups = new ConcurrentHashMap<>();

    private static final ChannelGroup userGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup visitorGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static CountPresentHandler userMonitorHandler = SpringContextHolder.getBean(CountPresentHandler.class);

    public static void addChannel(Channel channel) {
        String roomId = NioUserUtils.getRoomId(channel);
        if (roomId != null) {
            ChannelGroup group = getGroup(roomId);
            group.add(channel);
            userMonitorHandler.add(channel);
        }

        if (isLogined(channel)) {
            userGroup.add(channel);
        } else {
            visitorGroup.add(channel);
        }
    }

    private static ChannelGroup getGroup(String key) {
        ChannelGroup group = roomGroups.get(key);
        if (group == null) {
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            roomGroups.put(key, group);
        }
        return group;
    }

    private static boolean isLogined(Channel channel) {
        return NioUserUtils.getUser(channel) != null;
    }

    public static void removeChannel(Channel channel) {
        String roomId = NioUserUtils.getRoomId(channel);
        if (roomId != null) {
            ChannelGroup group = roomGroups.get(roomId);
            if (group != null) {
                group.remove(channel);
            }
        }

        if (isLogined(channel)) {
            userGroup.remove(channel);
        } else {
            visitorGroup.remove(channel);
        }
    }

    public static boolean isOtherChannelInRoom(Channel channel) {
        return isOtherChannelOnline(channel, getChannelsInRoom(channel));
    }

    private static boolean isOtherChannelOnline(Channel channel, List<Channel> channels) {
        boolean result = false;
        try {
            String currentUserId = NioUserUtils.getUserId(channel);
            if (currentUserId != null) {// 游客不进行任何记录
                for (Channel nioChannel : channels) {
                    if (nioChannel != channel && currentUserId.equals(NioUserUtils.getUserId(nioChannel))) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("判断当前用户是否有多端在线失败", e);
        }
        return result;
    }

    /**
     * @param isLogined 是否已登录
     * @return
     */
    public static List<Channel> getAllChannels(boolean isLogined) {
        ChannelGroup group = isLogined ? userGroup : visitorGroup;
        return newList(group);
    }

    public static List<Channel> getAllChannels(Channel channel) {
        ChannelGroup group = isLogined(channel) ? userGroup : visitorGroup;
        return newList(group);
    }

    public static ChannelGroup getGroupByRoom(Channel channel) {
        return getGroupByRoom(NioUserUtils.getRoomId(channel));
    }

    public static ChannelGroup getGroupByRoom(String roomId) {
        return roomGroups.get(roomId);
    }

    public static List<Channel> getChannelsInRoom(String roomId) {
        return newList(roomGroups.get(roomId));
    }

    public static List<Channel> getChannelsInRoom(Channel channel) {
        return getChannelsInRoom(NioUserUtils.getRoomId(channel));
    }

    private static List<Channel> newList(ChannelGroup group) {
        List<Channel> channels = new ArrayList<>();
        if (group != null) {
            for (Iterator<Channel> iterator = group.iterator(); iterator.hasNext(); ) {
                Channel channel = iterator.next();
                channels.add(channel);
            }
        }
        return channels;
    }

    public static int countOnline(String roomId) {
        ChannelGroup group = roomGroups.get(roomId);
        if (group != null) {
            Set<String> userSet = new HashSet<>(128);
            Set<String> visitorSet = new HashSet<>(128);
            group.forEach(channel -> {
                if (isLogined(channel))
                    userSet.add(NioUserUtils.getUserId(channel));
                else
                    visitorSet.add(NioUserUtils.getVisitor(channel).getId());
            });
            return userSet.size() + visitorSet.size();
        } else {
            return 0;
        }
    }

    public static Map<String, Integer> countOnline() {
        Map<String, Integer> roomIdCounts = Maps.newHashMap();
        roomGroups.keySet().forEach(roomId -> roomIdCounts.put(roomId, countOnline(roomId)));
        return roomIdCounts;
    }

    private static Set<String> getCountSet(Map<String, Set<String>> roomIds, String roomId) {
        Set<String> set = roomIds.get(roomId);
        if (set == null) {
            set = new HashSet<>(128);
            roomIds.put(roomId, set);
        }
        return set;
    }
}