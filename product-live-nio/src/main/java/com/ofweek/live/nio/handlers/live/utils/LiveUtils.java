package com.ofweek.live.nio.handlers.live.utils;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tangqian on 2016/8/30.
 */
public class LiveUtils {

    private static Map<String, RoomLiveStream> roomLives = new ConcurrentHashMap<>();

    public static void put(RoomLiveStream stream) {
        if (!roomLives.containsKey(stream.getRoomId())) {
            roomLives.put(stream.getRoomId(), stream);
        }
    }

    public static RoomLiveStream get(String roomId) {
        return roomLives.get(roomId);
    }

    public static boolean isPlaying(String roomId) {
        return get(roomId) != null || LiveVodUtils.get(roomId) != null;
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

    public static class RoomLiveStream {

        private String socketId;// 通道唯一ID
        private String userId;
        private String roomId;
        private LVBChannel liveInfo;
        private String type;
        private String pptId;
        private boolean playing;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPptId() {
            return pptId;
        }

        public void setPptId(String pptId) {
            this.pptId = pptId;
        }

        public LVBChannel getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(LVBChannel liveInfo) {
            this.liveInfo = liveInfo;
        }

        public boolean isPlaying() {
            return playing;
        }

        public void setPlaying(boolean playing) {
            this.playing = playing;
        }
    }

}
