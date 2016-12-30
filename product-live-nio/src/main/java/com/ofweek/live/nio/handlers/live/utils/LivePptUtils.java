package com.ofweek.live.nio.handlers.live.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ofweek.live.nio.handlers.live.dto.LivePptRequest;
import org.springframework.stereotype.Service;


@Service
public class LivePptUtils {

    private static Map<String, RoomPptStream> pptStreams = new ConcurrentHashMap<>();

    public static RoomPptStream get(String roomId) {
        return pptStreams.get(roomId);
    }

    public static void remove(String roomId) {
        pptStreams.remove(roomId);
    }

    public static void put(RoomPptStream stream) {
        if (stream.getRoomId() != null) {
            pptStreams.put(stream.getRoomId(), stream);
        }
    }

    /**
     * ppt演讲视频流
     */
    public static class RoomPptStream {
        /**
         * 主讲人ID
         */
        private String userId;

        private String socketId;

        /**
         * 展台ID
         */
        private String roomId;

        private LivePptRequest dto;

        public RoomPptStream() {
        }

        public RoomPptStream(String userId, String roomId, LivePptRequest dto) {
            this.userId = userId;
            this.roomId = roomId;
            this.dto = dto;
        }

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

        public LivePptRequest getDto() {
            return dto;
        }

        public void setDto(LivePptRequest dto) {
            this.dto = dto;
        }

        public String getSocketId() {
            return socketId;
        }

        public void setSocketId(String socketId) {
            this.socketId = socketId;
        }

    }
}
