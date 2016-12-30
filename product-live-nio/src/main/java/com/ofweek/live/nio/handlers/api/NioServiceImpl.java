package com.ofweek.live.nio.handlers.api;

import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.handlers.live.utils.LiveUtils;
import com.ofweek.live.nio.handlers.live.utils.LiveVodUtils;
import com.ofweek.live.nio.handlers.sys.NoticeRoomStatusHandler;
import com.ofweek.live.nio.handlers.user.BlackUserHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by tangqian on 2016/9/5.
 */
@Service
public class NioServiceImpl implements NioService {

    @Resource
    private NoticeRoomStatusHandler noticeRoomStatusHandler;

    @Resource
    private BlackUserHandler blackUserHandler;

    @Override
    public Set<String> getLiveRoomIds() {
        Set<String> sets = LiveUtils.getLiveRoomIds();
        sets.addAll(LiveVodUtils.getLiveRoomIds());
        return sets;
    }

    @Override
    public Map<String, Integer> getOnlineUserCount() {
        Map<String, Integer> onlineMap = RoomChannelContainer.countOnline();
        return onlineMap;
    }

    @Override
    public int getOnlineUserCount(String roomId) {
        return RoomChannelContainer.countOnline(roomId);
    }

    @Override
    public void noticeRoomStatus(String roomId) {
        noticeRoomStatusHandler.sendNotice(roomId);
    }

    @Override
    public void noticeBlacklist(String id) {
        blackUserHandler.sendNotice(id);
    }
}
