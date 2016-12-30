package com.ofweek.live.nio.handlers.api;

import java.util.Map;
import java.util.Set;

/**
 * Created by tangqian on 2016/9/5.
 */
public interface NioService {

    Set<String> getLiveRoomIds();

    /**
     * 获取全部直播房间实时在线人数
     *
     * @return key->value 分别对应 roomId->count;
     */
    Map<String, Integer> getOnlineUserCount();

    /**
     * 获取该房间实时在线人数
     *
     * @param roomId
     * @return
     */
    int getOnlineUserCount(String roomId);

    void noticeRoomStatus(String rooomId);

    /**
     * 通知被拉黑用户
     * @param id 拉黑记录id
     */
    void noticeBlacklist(String id);


}
