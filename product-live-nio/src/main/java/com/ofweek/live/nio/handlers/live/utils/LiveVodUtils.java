package com.ofweek.live.nio.handlers.live.utils;

import com.google.common.collect.Sets;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tangqian on 2016/8/30.
 */
public class LiveVodUtils {

    private static Map<String, RoomLiveVodSeek> roomLives = new ConcurrentHashMap<>();

    public static void put(RoomLiveVodSeek seek) {
        if (!roomLives.containsKey(seek.getRoomId())) {
            roomLives.put(seek.getRoomId(), seek);
        }
    }

    public static RoomLiveVodSeek get(String roomId) {
        return roomLives.get(roomId);
    }

    public static void remove(String roomId) {
        roomLives.remove(roomId);
    }

    /**
     * 获取正在直播中roomId列表
     *
     * @return
     */
    public static Set<String> getLiveRoomIds() {
        return Sets.newHashSet(roomLives.keySet());
    }


    /**
     * 展位视频播放时间
     */
    public static class RoomLiveVodSeek {
        /**
         * 展位id
         */
        private String socketId;// 通道唯一ID
        private String userId;
        private String roomId;
        private String vodName;
        /**
         * 播放时间
         */
        private Date playTime;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getVodName() {
            return vodName;
        }

        public void setVodName(String vodName) {
            this.vodName = vodName;
        }

        public Date getPlayTime() {
            return playTime;
        }

        public void setPlayTime(Date playTime) {
            this.playTime = playTime;
        }

        public String getSocketId() {
            return socketId;
        }

        public void setSocketId(String socketId) {
            this.socketId = socketId;
        }
    }
}
