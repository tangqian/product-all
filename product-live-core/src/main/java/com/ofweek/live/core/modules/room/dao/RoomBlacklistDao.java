package com.ofweek.live.core.modules.room.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomBlacklist;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomBlacklistDao extends CrudDao<RoomBlacklist> {

    RoomBlacklist getByRoomUserId(@Param("roomId") String roomId, @Param("userId") String userId);
}