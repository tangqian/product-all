package com.thinkgem.jeesite.modules.live.room.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomData;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface RoomDataDao extends CrudDao<RoomData> {

	/**
	 * @param roomId
	 * @param id
	 */
	RoomData getByRoomSourceId(@Param("roomId") String roomId, @Param("sourceId") String sourceId);
	
}