package com.ofweek.live.core.modules.room.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomChat;

/**
 * @author tangqian
 */
@MyBatisDao
public interface RoomChatDao extends CrudDao<RoomChat> {

    /**
     * 获取房间公聊区历史消息
     * @param roomId
     * @param endTime
     * @param offset
     * @param limit
     * @return
     */
    List<RoomChat> findRoomRange(@Param("roomId") String roomId, @Param("endTime") Date endTime,
                             @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 统计房间聊天记录
     * @param roomId
     * @return
     */
    Integer countRoomChatAudienceForRoomId(@Param("roomId") String roomId);
    
    List<RoomChat> findRoomChatForRoomId(@Param("roomId") String roomId, @Param("userId") String userId);
}